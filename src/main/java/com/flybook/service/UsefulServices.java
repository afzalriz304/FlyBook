package com.flybook.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.flybook.collection.Flights;
import com.flybook.customCollection.CustomResponse;

@Component
public interface UsefulServices {

	public List<HashMap<String, String>> FetchError(BindingResult bindingResult);
	
	public CustomResponse addFlightByAirline(Flights flights);

	public boolean cityValidation(String source, String destination);

	public boolean dateValidation(Date departDate, Date returnDate);
	
	// format date as
	public Date setDateFormatYYYY_MM_DD(Date date);
}
