package com.shane.iredeem.service;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.shane.iredeem.constant.Availability;
import com.shane.iredeem.constant.Cabin;
import com.shane.iredeem.entity.Flight;
import com.shane.iredeem.entity.Itinerary;
import com.shane.iredeem.repository.ItineraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraryService {
    @Autowired
    ItineraryRepository itineraryRepository;

    public List<List<String>> getRoutes() {
        return itineraryRepository.findRoutes();
    }

    public Optional<Itinerary> getItinerary(Long id) {
        return itineraryRepository.findById(id);
    }

    public List<Itinerary> getItineraries(List<Long> ids) {
        return itineraryRepository.findByItineraryIdIn(ids);
    }

    public List<Itinerary> getItineraries(String departure, String arrival, Date since, Date till) {
        return itineraryRepository.findItineraryByInterval(departure, arrival, since, till);
    }

    public Map<String, Map<Cabin, Availability>> getAvailability(
            String departure, String arrival, Date since, Date till, boolean includeWaitingList) {
        Map<String, Map<Cabin, Availability>> availabilities = new TreeMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        List<Itinerary> itineraries = getItineraries(departure, arrival, since, till);
        itineraries.forEach(itinerary -> {
            String dateString = dateFormat.format(itinerary.getFlight1().getDepartureTime());
            availabilities.computeIfAbsent(dateString, k -> new HashMap<>(4, 1));
            Arrays.asList(Cabin.values()).forEach(cabin -> {
                Availability currentStatus = availabilities.get(dateString).get(cabin);
                if (Availability.NON_STOP == currentStatus) return;
                Availability status = getStatus(itinerary.getFlight1(), itinerary.getFlight2(), cabin, includeWaitingList);
                if (isStatusPreferred(status, currentStatus)) availabilities.get(dateString).put(cabin, status);
            });
        });
        return availabilities;
    }

    boolean isStatusPreferred(Availability newStatus, Availability oldStatus) {
        return oldStatus == null || newStatus.getOrder() < oldStatus.getOrder();
    }

    boolean isCx(String airline) {
        return "CX".equals(airline);
    }

    boolean isFlightOnWaitingList(String airline, String status) {
        return isCx(airline) && "L".equals(status);
    }

    // CX/KA[L] = L
    // CX/KA[L] + CX/KA[L] = L
    // CX/KA[L] + other[1-9] = N
    // other[L] = N
    // other[L] + other[L] = N
    boolean isItineraryOnWaitingList(String airline1, String status1) {
        return isFlightOnWaitingList(airline1, status1);
    }

    boolean isItineraryOnWaitingList(String airline1, String status1, String airline2, String status2) {
        if (airline2 == null || status2 == null) return isItineraryOnWaitingList(airline1, status1);
        return isFlightOnWaitingList(airline1, status1) &&
                (isFlightOnWaitingList(airline2, status2) || (isCx(airline2) && getCapacity(status2) > 0));
    }

    int getCapacity(String status) {
        try {
            return Integer.parseInt(status);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // status
    // N: not available
    // L: waiting list
    // X: no such a cabin?
    // [1-9]+: available
    Availability getStatus(Flight flight1, Flight flight2, Cabin cabin, boolean includeWaitingList) {
        String flight1Status;
        String flight2Status;
        try {
            Method getStatus = Flight.class.getMethod("getStatus" + cabin.getCode());
            flight1Status = (String) getStatus.invoke(flight1);
            flight2Status = (flight2 != null) ? (String) getStatus.invoke(flight2) : null;
        } catch (Exception e) {
            throw new RuntimeException("Fail to get the status of cabin " + cabin.getCode(), e);
        }
        if (flight2 == null) {
            boolean isOnWaitingList = isItineraryOnWaitingList(flight1.getAirline(), flight1Status);
            return (getCapacity(flight1Status) > 0 || (includeWaitingList && isOnWaitingList)) ?
                    Availability.NON_STOP : Availability.UNAVAILABLE;
        } else {
            boolean isOnWaitingList = isItineraryOnWaitingList(flight1.getAirline(), flight1Status, flight2.getAirline(), flight2Status);
            return (Math.min(getCapacity(flight1Status), getCapacity(flight2Status)) > 0 || (includeWaitingList && isOnWaitingList)) ?
                    Availability.AVAILABLE : Availability.UNAVAILABLE;
        }
    }
}