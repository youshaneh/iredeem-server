package com.shane.iredeem.entity.tracking;

import com.shane.iredeem.constant.Cabin;
import com.shane.iredeem.entity.Itinerary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingResponse {
    private Cabin cabin;
    private Itinerary itinerary;
}
