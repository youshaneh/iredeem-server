package com.shane.iredeem.service;

import java.util.Optional;

import com.shane.iredeem.constant.Availability;
import com.shane.iredeem.constant.Cabin;
import com.shane.iredeem.entity.Flight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItineraryServiceTests {
	@Autowired
	ItineraryService itineraryService;

	@Test
	void testIsCx() {
		Assertions.assertTrue(itineraryService.isCx("CX"));
		Assertions.assertFalse(itineraryService.isCx("BA"));
		Assertions.assertFalse(itineraryService.isCx("JA"));
	}

	@Test
	void testIsOnWaitingList() {
		Assertions.assertTrue(itineraryService.isFlightOnWaitingList("CX", "L"));
		Assertions.assertFalse(itineraryService.isFlightOnWaitingList("CX", "1"));
		Assertions.assertFalse(itineraryService.isFlightOnWaitingList("CX", "20"));
		Assertions.assertFalse(itineraryService.isFlightOnWaitingList("CX", "X"));
		Assertions.assertFalse(itineraryService.isFlightOnWaitingList("BA", "L"));
		Assertions.assertFalse(itineraryService.isFlightOnWaitingList("BA", "9"));
	}

	@Test
	void testGetCapacity() {
		Assertions.assertEquals(0, itineraryService.getCapacity("X"));
		Assertions.assertEquals(0, itineraryService.getCapacity("N"));
		Assertions.assertEquals(0, itineraryService.getCapacity("L"));
		Assertions.assertEquals(1, itineraryService.getCapacity("1"));
		Assertions.assertEquals(20, itineraryService.getCapacity("20"));
	}

	@Test
	void testGetStatus() {
		Flight flight1 = new Flight();
		flight1.setAirline("CX");
		flight1.setStatusF("L");
		flight1.setStatusB("N");
		flight1.setStatusN("5");
		Assertions.assertEquals(Availability.NON_STOP, itineraryService.getStatus(flight1, null, Cabin.FIRST, true));
		Assertions.assertEquals(Availability.UNAVAILABLE, itineraryService.getStatus(flight1, null, Cabin.BUSINESS, false));
		Assertions.assertEquals(Availability.NON_STOP, itineraryService.getStatus(flight1, null, Cabin.PREMIUM_ECONOMY, false));
		Flight flight2 = new Flight();
		flight2.setAirline("BA");
		flight2.setStatusF("1");
		flight2.setStatusB("2");
		flight2.setStatusN("L");
		Assertions.assertEquals(Availability.NON_STOP, itineraryService.getStatus(flight2, null, Cabin.FIRST, false));
		Assertions.assertEquals(Availability.NON_STOP, itineraryService.getStatus(flight2, null, Cabin.BUSINESS, false));
		Assertions.assertEquals(Availability.UNAVAILABLE, itineraryService.getStatus(flight2, null, Cabin.PREMIUM_ECONOMY, false));
		Assertions.assertEquals(Availability.UNAVAILABLE, itineraryService.getStatus(flight1, flight2, Cabin.FIRST, false));
		Assertions.assertEquals(Availability.UNAVAILABLE, itineraryService.getStatus(flight1, flight2, Cabin.BUSINESS, false));
		Assertions.assertEquals(Availability.UNAVAILABLE, itineraryService.getStatus(flight1, flight2, Cabin.PREMIUM_ECONOMY, false));
  }
}
