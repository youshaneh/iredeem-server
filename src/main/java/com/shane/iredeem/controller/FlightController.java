package com.shane.iredeem.controller;

import com.shane.iredeem.entity.Flight;
import com.shane.iredeem.repository.FlightRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {
  @Autowired
  FlightRepository flightRepository;

  @GetMapping("/flight/{id}")
  public Flight getFlight(@PathVariable("id") Integer id){
    return flightRepository.findById(id).orElse(new Flight());
  }
}