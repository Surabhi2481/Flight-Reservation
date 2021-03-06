package com.surabhi.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${com.surabhi.flightreservation.itinerary.dirpath}")
	private String ITINERARY_DIR;

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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	@Override
	public Reservation bookFlight(ReservationRequest request) {
		
		LOGGER.info("Inside bookFlight()");
		Long flightId = request.getFlightId();
		Flight flight = flightRepository.findById(flightId).get();
		
		LOGGER.info("Fetching the flight of flight Id : "+flightId);
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setPhone(request.getPassengerPhone());
		passenger.setEmail(request.getPassengerEmail());
		LOGGER.info("Saving the passenger "+passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		LOGGER.info("Saving the reservation "+reservation);
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String filePath = ITINERARY_DIR+savedReservation.getId()+".pdf";
		LOGGER.info("Generating the Itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath);
		emailUtil.sendItinenary(passenger.getEmail(), filePath);
		LOGGER.info("Emailing the Itinerary");
		return savedReservation;
	}

}
