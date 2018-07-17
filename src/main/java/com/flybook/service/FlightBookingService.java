package com.flybook.service;

import org.springframework.stereotype.Component;

import com.flybook.collection.Flights;
import com.flybook.customCollection.CustomResponse;
import com.flybook.customCollection.FlightSearch;

@Component
public interface FlightBookingService {

	public CustomResponse addFlight(Flights flights);

	public CustomResponse searchFlight(FlightSearch flightSearch);
}
