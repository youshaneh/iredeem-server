package com.shane.iredeem.service;

import com.shane.iredeem.entity.Flight;
import com.shane.iredeem.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    FlightRepository flightRepository;

    public Optional<Flight> getFlight(Integer id){
        return flightRepository.findById(id);
    }
}
