package com.surabhi.flightreservation.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.surabhi.flightreservation.entities.Flight;
import com.surabhi.flightreservation.repos.FlightRepository;



@Controller
public class FlightController {
	
	@Autowired
	FlightRepository flightRepository;

	
//	@RequestMapping("/findFlights")
//	public String findFlights(@RequestParam("from") String from, @RequestParam("to") String to,
//			@RequestParam("departureDate") String departureDate, ModelMap modelMap) throws ParseException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//		Date date = dateFormat.parse(departureDate);
//		List<Flight> flights = flightRepository.findFlightsByDepartureCityAndArrivalCityAndDateOfDeparture(from, to, date);
//		if(!CollectionUtils.isEmpty(flights)) {
//		for(Flight flight : flights) {
//			System.out.println(flight);
//		} }
//		else {
//			System.out.println("Records not avail");
//		}
//		modelMap.addAttribute("flights", flights);
//		
//		return "displayFlights";
//
//	}
	

	@RequestMapping("/findFlights")
	public String findFlights(@RequestParam("from") String from, @RequestParam("to") String to,
			@RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy") Date departureDate, ModelMap modelMap) {
		List<Flight> flights = flightRepository.findFlights(from, to, departureDate);
		modelMap.addAttribute("flights", flights);
		return "displayFlights";

	}

}



