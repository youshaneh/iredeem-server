package com.shane.iredeem.service;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.shane.iredeem.entity.Flight;
import com.shane.iredeem.entity.Itinerary;
import com.shane.iredeem.repository.ItineraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraryService {
  @Autowired
  ItineraryRepository itineraryRepository;

  public List<Itinerary> getItinerary(String departure, String arrival, Date since, Date till) {
    return Stream
        .concat(itineraryRepository.findNonStopItineraryByInterval(departure, arrival, since, till).stream(),
            itineraryRepository.findOneStopItineraryByInterval(departure, arrival, since, till).stream())
        .collect(Collectors.toList());
  }

  public Map<String, Map<String, String>> getAvailability(String departure, String arrival, Date since, Date till) {
    Map<String, Map<String, String>> availabilities = new TreeMap<>();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<Itinerary> itineraries = getItinerary(departure, arrival, since, till);
    itineraries.forEach(itinerary -> {
      String dateString = dateFormat.format(itinerary.getFlight1().getDepartureTime());
      if (availabilities.get(dateString) == null)
        availabilities.put(dateString, new HashMap<>(4));
      Arrays.asList(new String[] { "F", "B", "N", "R" }).forEach(cabin -> {
        String currentStatus = availabilities.get(dateString).get(cabin);
        if ("Y".equals(currentStatus))
          return;
        String status = getStatus(itinerary.getFlight1(), Optional.ofNullable(itinerary.getFlight2()), cabin);
        if ("Y".equals(status) || ("L".equals(status) && "N".equals(currentStatus)))
          availabilities.get(dateString).put(cabin, status);
      });
    });
    return availabilities;
  }

  boolean isCx(String airline) {
    return "CX".equals(airline) || "KA".equals(airline);
  }

  boolean isOnWaitingList(String airline, String status) {
    return isCx(airline) && "L".equals(status);
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
  // X: (no such a cabin??)
  // [1-9]+: available
  // treat KA as CX
  // CX/KA[L] = L
  // CX/KA[L] + CX/KA[L] = L
  // CX/KA[L] + other[1-9] = N
  // other[L] = N
  // other[L] + other[L] = N
  String getStatus(Flight flight1, Optional<Flight> flight2, String cabin) {
    String flight1Status;
    Optional<String> flight2Status;
    try {
      Method getStatus = Flight.class.getMethod("getStatus" + cabin);
      flight1Status = (String) getStatus.invoke(flight1);
      flight2Status = Optional.ofNullable(flight2.isPresent() ? (String) getStatus.invoke(flight2.get()) : null);
    } catch (Exception e) {
      throw new RuntimeException("Fail to get the status of cabin " + cabin, e);
    }
    if (isOnWaitingList(flight1.getAirline(), flight1Status)
        && (flight2.isEmpty() || isOnWaitingList(flight2.get().getAirline(), flight2Status.get())
            || (isCx(flight2.get().getAirline()) && getCapacity(flight2Status.get()) > 0))) {
      return "L";
    } else {
      int capacity = Math.min(getCapacity(flight1Status),
          flight2.isPresent() ? getCapacity(flight2Status.get()) : 1);
      return (capacity > 0) ? "Y" : "N";
    }
  }
}