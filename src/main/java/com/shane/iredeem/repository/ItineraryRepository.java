package com.shane.iredeem.repository;

import java.util.Date;
import java.util.List;

import com.shane.iredeem.entity.Itinerary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
  @Query("select distinct f1.departureAirport as f, COALESCE(f2.arrivalAirport, f1.arrivalAirport) as t from Itinerary as i join Flight as f1 on i.flight1 = f1.id left join Flight as f2 on i.flight2 = f2.id where f1.departureTime > current_date")
  List<List<String>> findRoutes();
  
  @Query("select iti from Itinerary as iti where iti.flight1.departureAirport = ?1 and COALESCE(iti.flight2.arrivalAirport, iti.flight1.arrivalAirport) = ?2 " + 
    "and iti.flight1.departureTime >= ?3 and iti.flight1.departureTime < ?4")
  List<Itinerary> findItineraryByInterval(String departure, String arrival, Date since, Date till);
}