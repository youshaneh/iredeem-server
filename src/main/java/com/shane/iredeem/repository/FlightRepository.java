package com.shane.iredeem.repository;

import java.util.List;

import com.shane.iredeem.entity.Flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
}