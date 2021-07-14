package com.shane.iredeem.controller;

import com.shane.iredeem.entity.Itinerary;
import com.shane.iredeem.entity.tracking.TrackingJoinResult;
import com.shane.iredeem.entity.tracking.TrackingRequest;
import com.shane.iredeem.service.AuthService;
import com.shane.iredeem.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static com.shane.iredeem.service.AuthService.USER_ID;

@RestController
@RequestMapping(value = "/tracking", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrackingController {
    @Autowired
    private TrackingService trackingService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public List<TrackingJoinResult> getTracking(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader) {
        String accessToken = authHeader.replace("Bearer ", "").replace("bearer ", "");
        Map<String, Object> claims = authService.parseToken(accessToken);
        long userId = Long.valueOf((int)claims.get(USER_ID));
        return trackingService.getTracking(userId);
    }

    @DeleteMapping("/{trackingId}")
    public void deleteTracking(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("trackingId") Long trackingId) {
        String accessToken = authHeader.replace("Bearer ", "").replace("bearer ", "");
        Map<String, Object> claims = authService.parseToken(accessToken);
        long userId = Long.valueOf((int)claims.get(USER_ID));
        trackingService.deleteTracking(trackingId, userId);
    }

    @PostMapping
    public void addTracking(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody TrackingRequest request) {
        String accessToken = authHeader.replace("Bearer ", "").replace("bearer ", "");
        Map<String, Object> claims = authService.parseToken(accessToken);
        long userId = Long.valueOf((int)claims.get(USER_ID));
        if(request.getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The logged in user doesn't match the one queried");
        }
        trackingService.addTracking(request.getUserId(), request.getItineraryId(), request.getCabin());
    }
}
