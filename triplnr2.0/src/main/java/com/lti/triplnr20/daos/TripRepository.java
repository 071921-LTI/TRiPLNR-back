package com.lti.triplnr20.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.triplnr20.models.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer> {
	
	Trip findTripByTrip_Name(String tripName);

}
