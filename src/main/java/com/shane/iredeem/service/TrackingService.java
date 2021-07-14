package com.shane.iredeem.service;

import com.shane.iredeem.constant.Cabin;
import com.shane.iredeem.entity.tracking.Tracking;
import com.shane.iredeem.entity.tracking.TrackingJoinResult;
import com.shane.iredeem.repository.TrackingJoinResultRepository;
import com.shane.iredeem.repository.TrackingRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TrackingService {
    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private TrackingJoinResultRepository trackingJoinResultRepository;

    @Autowired
    private ItineraryService itineraryService;

    public List<TrackingJoinResult> getTracking(Long userId) {
        return trackingJoinResultRepository.findItineraryByUserId(userId);
    }

    public void deleteTracking(Long trackingId, Long userId) {
        Tracking tracking = trackingRepository.findById(trackingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The route doesn't exist"));
        if(tracking.getUserId() != userId) throw new ResponseStatusException(HttpStatus.CONFLICT, "The route exists already");
        trackingRepository.deleteById(trackingId);
    }

    public void addTracking(Long userId, Long itineraryId, Cabin cabin) {
        Tracking tracking = new Tracking(userId, itineraryId, cabin.getCode());
        try {
            trackingRepository.save(tracking);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException exception = (ConstraintViolationException) e.getCause();
                if ("tracking.user_itinerary_cabin".equals(exception.getConstraintName())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "The route exists already");
                }
            }
        }
    }
}
