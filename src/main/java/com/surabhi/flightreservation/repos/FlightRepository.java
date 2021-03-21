package com.surabhi.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surabhi.flightreservation.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

}
