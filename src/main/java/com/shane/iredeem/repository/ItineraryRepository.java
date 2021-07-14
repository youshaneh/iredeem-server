package com.shane.iredeem.repository;

import java.util.Date;
import java.util.List;

import com.shane.iredeem.entity.Itinerary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
  @Query("select distinct f1.departureAirport as departure, COALESCE(f2.arrivalAirport, f1.arrivalAirport) as arrival from Itinerary as i join Flight as f1 on i.flight1 = f1.id left join Flight as f2 on i.flight2 = f2.id where f1.departureTime > current_date")
  List<List<String>> findRoutes();

  @Query(value = "select * from itinerary join flight as flight1 on itinerary.flight1 = flight1.id left join flight as flight2 on itinerary.flight2 = flight2.id" +
          " where flight1.departure_airport = ?1 and COALESCE(flight2.arrival_airport, flight1.arrival_airport) = ?2" +
          " and flight1.departure_time >= ?3 and flight1.departure_time < ?4", nativeQuery = true)
  List<Itinerary> findItineraryByInterval(String departure, String arrival, Date since, Date till);

  List<Itinerary> findByItineraryIdIn(List<Long> ids);
}