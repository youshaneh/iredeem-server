package com.shane.iredeem.repository;

import java.util.Date;
import java.util.List;

import com.shane.iredeem.entity.Itinerary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
  @Query("select distinct f1.departureAirport as f, COALESCE(f2.arrivalAirport, f1.arrivalAirport) as t from Itinerary as i join Flight as f1 on i.flight1 = f1.id left join Flight as f2 on i.flight2 = f2.id where f1.departureTime > current_date")
  List<List<String>> findRoutes();
  
  @Query("select iti from Itinerary as iti where iti.flight1.departureAirport = :departure and iti.flight2 is null and iti.flight1.arrivalAirport = :arrival and iti.flight1.departureTime >= :since and iti.flight1.departureTime < :till")
  List<Itinerary> findNonStopItineraryByInterval(@Param("departure") String departure, @Param("arrival") String arrival, @Param("since") Date since, @Param("till") Date till);
  
  @Query("select iti from Itinerary as iti where iti.flight1.departureAirport = :departure and iti.flight2 is not null and iti.flight2.arrivalAirport = :arrival and iti.flight1.departureTime >= :since and iti.flight1.departureTime < :till")
  List<Itinerary> findOneStopItineraryByInterval(@Param("departure") String departure, @Param("arrival") String arrival, @Param("since") Date since, @Param("till") Date till);
}