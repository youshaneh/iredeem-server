package com.shane.iredeem.entity.tracking;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tracking")
public class Tracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "itinerary_id", nullable = false)
    private long itineraryId;

    @Column(name = "cabin", nullable = false)
    private String cabin;

    public Tracking(Long userId, Long itineraryId, String cabin){
        setUserId(userId);
        setItineraryId(itineraryId);
        setCabin(cabin);
    }
}
