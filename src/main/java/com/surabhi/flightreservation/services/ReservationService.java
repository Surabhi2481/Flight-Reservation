package com.surabhi.flightreservation.services;

import com.surabhi.flightreservation.dto.ReservationRequest;
import com.surabhi.flightreservation.entities.Reservation;

public interface ReservationService {
	
	public Reservation bookFlight(ReservationRequest request);

}
