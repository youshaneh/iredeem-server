package com.shane.iredeem.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the employees database table.
 * 
 */
@Entity
@Table(name = "flight")
public class Flight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int flightId;

	private String airline;

	@Column(name = "flight_number")
	private String flightNumber;

	private String aircraft;

	@Column(name = "status_f")
	private String statusF;

	@Column(name = "status_b")
	private String statusB;
	
	@Column(name = "status_n")
	private String statusN;
	
	@Column(name = "status_r")
	private String statusR;
	
	@Column(name = "departure_airport")
	private String departureAirport;
	
	@Column(name = "departure_terminal")
	private String departureTerminal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "departure_time")
	private Date departureTime;

	@Column(name = "arrival_airport")
	private String arrivalAirport;
	
	@Column(name = "arrival_terminal")
	private String arrivalTerminal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "arrival_time")
	private Date arrivalTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getAircraft() {
		return aircraft;
	}

	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	public String getStatusF() {
		return statusF;
	}

	public void setStatusF(String statusF) {
		this.statusF = statusF;
	}

	public String getStatusB() {
		return statusB;
	}

	public void setStatusB(String statusB) {
		this.statusB = statusB;
	}

	public String getStatusN() {
		return statusN;
	}

	public void setStatusN(String statusN) {
		this.statusN = statusN;
	}

	public String getStatusR() {
		return statusR;
	}

	public void setStatusR(String statusR) {
		this.statusR = statusR;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getDepartureTerminal() {
		return departureTerminal;
	}

	public void setDepartureTerminal(String departureTerminal) {
		this.departureTerminal = departureTerminal;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public String getArrivalTerminal() {
		return arrivalTerminal;
	}

	public void setArrivalTerminal(String arrivalTerminal) {
		this.arrivalTerminal = arrivalTerminal;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}