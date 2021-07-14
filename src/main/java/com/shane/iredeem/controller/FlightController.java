package com.shane.iredeem.controller;

import com.shane.iredeem.entity.Flight;
import com.shane.iredeem.repository.FlightRepository;

import com.shane.iredeem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/flights", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightController {
  @Autowired
  FlightService flightService;

  @GetMapping("/{id}")
  public Flight getFlight(@PathVariable("id") Integer id){
    return flightService.getFlight(id).orElse(new Flight());
  }
}