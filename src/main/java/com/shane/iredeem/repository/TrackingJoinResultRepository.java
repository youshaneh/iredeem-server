package com.shane.iredeem.repository;

import com.shane.iredeem.entity.Itinerary;
import com.shane.iredeem.entity.tracking.Tracking;
import com.shane.iredeem.entity.tracking.TrackingJoinResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TrackingJoinResultRepository extends JpaRepository<TrackingJoinResult, Long> {
    @Query(value = "select * from tracking join itinerary on tracking.itinerary_id = itinerary.id " +
            "join flight as flight1 on itinerary.flight1 = flight1.id left join flight as flight2 on itinerary.flight2 = flight2.id " +
            "where user_id = ?1 order by flight1.departure_time",
            nativeQuery = true)
    List<TrackingJoinResult> findItineraryByUserId(Long userId);
}