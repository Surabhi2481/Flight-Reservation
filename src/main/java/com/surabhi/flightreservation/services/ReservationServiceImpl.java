package com.surabhi.flightreservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surabhi.flightreservation.dto.ReservationRequest;
import com.surabhi.flightreservation.entities.Flight;
import com.surabhi.flightreservation.entities.Passenger;
import com.surabhi.flightreservation.entities.Reservation;
import com.surabhi.flightreservation.repos.FlightRepository;
import com.surabhi.flightreservation.repos.PassengerRepository;
import com.surabhi.flightreservation.repos.ReservationRepository;
import com.surabhi.flightreservation.util.EmailUtil;
import com.surabhi.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	PDFGenerator pdfGenerator;
	
	@Autowired
	EmailUtil emailUtil;
	
	@Override
	public Reservation bookFlight(ReservationRequest request) {
		Long flightId = request.getFlightId();
		Flight flight = flightRepository.findById(flightId).get();
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setPhone(request.getPassengerPhone());
		passenger.setEmail(request.getPassengerEmail());
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String filePath = "Z:\\Docs/Reservation/reservation"+savedReservation.getId()+".pdf";
		pdfGenerator.generateItinerary(savedReservation, filePath);
		emailUtil.sendItinenary(passenger.getEmail(), filePath);
		
		return savedReservation;
	}

}
