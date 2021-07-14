package com.shane.iredeem.entity.tracking;

import com.shane.iredeem.constant.Cabin;
import lombok.Data;

@Data
public class TrackingRequest {
    private Long userId;
    private Long itineraryId;
    private Cabin cabin;
}
