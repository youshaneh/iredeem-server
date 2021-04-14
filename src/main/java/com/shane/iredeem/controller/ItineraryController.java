package com.shane.iredeem.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shane.iredeem.constant.Availability;
import com.shane.iredeem.constant.Cabin;
import com.shane.iredeem.entity.Itinerary;
import com.shane.iredeem.repository.ItineraryRepository;
import com.shane.iredeem.service.ItineraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItineraryController {
    @Autowired
    ItineraryService itineraryService;

    @GetMapping("/routes")
    public List<List<String>> getRoutes() {
        return itineraryService.getRoutes();
    }

    @GetMapping("/itineraries/{id}")
    public Itinerary getItinerary(@PathVariable("id") Integer id) {
        return itineraryService.getItinerary(id).orElse(null);
    }

    @GetMapping("/itineraries")
    public List<Itinerary> getItineraries(@RequestParam String departure, @RequestParam String arrival,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date since,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date till) {
        return itineraryService.getItineraries(departure, arrival, since, till);
    }

    @GetMapping("/availability")
    public Map<String, Map<Cabin, Availability>> getAvailability(@RequestParam String departure, @RequestParam String arrival,
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date since,
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date till,
                                                                 @RequestParam boolean includeWaitingList) {
        return itineraryService.getAvailability(departure, arrival, since, till, includeWaitingList);
    }
}