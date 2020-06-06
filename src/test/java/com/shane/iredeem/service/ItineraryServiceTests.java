package com.shane.iredeem.service;

import java.util.Optional;

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
		Assertions.assertTrue(itineraryService.isCx("KA"));
		Assertions.assertFalse(itineraryService.isCx("BA"));
		Assertions.assertFalse(itineraryService.isCx("JA"));
	}

	@Test
	void testIsOnWaitingList() {
		Assertions.assertTrue(itineraryService.isOnWaitingList("CX", "L"));
		Assertions.assertTrue(itineraryService.isOnWaitingList("KA", "L"));
		Assertions.assertFalse(itineraryService.isOnWaitingList("KA", "1"));
		Assertions.assertFalse(itineraryService.isOnWaitingList("KA", "20"));
		Assertions.assertFalse(itineraryService.isOnWaitingList("KA", "X"));
		Assertions.assertFalse(itineraryService.isOnWaitingList("BA", "L"));
		Assertions.assertFalse(itineraryService.isOnWaitingList("BA", "9"));
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
		Assertions.assertEquals("L", itineraryService.getStatus(flight1, Optional.empty(), "F"));
		Assertions.assertEquals("N", itineraryService.getStatus(flight1, Optional.empty(), "B"));
		Assertions.assertEquals("Y", itineraryService.getStatus(flight1, Optional.empty(), "N"));
		Flight flight2 = new Flight();
		flight2.setAirline("BA");
		flight2.setStatusF("1");
		flight2.setStatusB("2");
		flight2.setStatusN("L");
		Assertions.assertEquals("Y", itineraryService.getStatus(flight2, Optional.empty(), "F"));
		Assertions.assertEquals("Y", itineraryService.getStatus(flight2, Optional.empty(), "B"));
		Assertions.assertEquals("N", itineraryService.getStatus(flight2, Optional.empty(), "N"));
		Assertions.assertEquals("N", itineraryService.getStatus(flight1, Optional.of(flight2), "F"));
		Assertions.assertEquals("N", itineraryService.getStatus(flight1, Optional.of(flight2), "B"));
		Assertions.assertEquals("N", itineraryService.getStatus(flight1, Optional.of(flight2), "N"));
  }
}
