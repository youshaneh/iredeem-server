package com.shane.iredeem.controller;

import java.util.Date;
import java.util.List;

import com.shane.iredeem.entity.Itinerary;
import com.shane.iredeem.repository.ItineraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItineraryController {
  @Autowired
  ItineraryRepository itineraryRepository;

  @GetMapping("/routes")
  public List<List<String>> getRoutes() {
    return itineraryRepository.findRoutes();
  }

  @GetMapping("/itinerary/{id}")
  public Itinerary getItinerary(@PathVariable("id") Integer id) {
    return itineraryRepository.findById(id).orElse(new Itinerary());
  }

  @GetMapping("/itinerary")
  public List<Itinerary> getItinerary(@RequestParam String departure, @RequestParam String arrival,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date since,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date till) {
    return itineraryRepository.findItineraryByInterval(departure, arrival, since, till);
  }
}