package com.shane.iredeem.repository;

import com.shane.iredeem.entity.Itinerary;
import com.shane.iredeem.entity.tracking.Tracking;
import com.shane.iredeem.entity.tracking.TrackingJoinResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByUserId(Long userId);

    @Override
    Optional<Tracking> findById(Long id);
}