package com.shane.iredeem.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the employees database table.
 * 
 */
@Entity
@Table(name = "itinerary")
public class Itinerary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int itineraryId;

	@ManyToOne
	@JoinColumn(name="flight1")
	private Flight flight1;
	
	@ManyToOne
	@JoinColumn(name="flight2")
	private Flight flight2;

	public int getItineraryId() {
		return itineraryId;
	}

	public void setItineraryId(int itineraryId) {
		this.itineraryId = itineraryId;
	}

	public Flight getFlight1() {
		return flight1;
	}

	public void setFlight1(Flight flight1) {
		this.flight1 = flight1;
	}

	public Flight getFlight2() {
		return flight2;
	}

	public void setFlight2(Flight flight2) {
		this.flight2 = flight2;
	}
}