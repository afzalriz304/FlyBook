package com.flybook.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.flybook.FlybookConstant.FlyBookConstant;
import com.flybook.collection.Flights;
import com.flybook.customCollection.CustomResponse;
import com.flybook.customCollection.FlightSearch;
import com.flybook.service.FlightBookingService;
import com.flybook.service.UsefulServices;

@RestController
@RequestMapping("flybook")
public class MainController {

	@Autowired
	private UsefulServices usefulService;
	@Autowired
	private FlightBookingService flightBookingService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/***
	 * Handle the exceptions
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<CustomResponse> handleAllExceptions(Exception ex, WebRequest request) {
		CustomResponse errorDetails = CustomResponse.builder().status(false).message(ex.getMessage()).data(request.getDescription(false))
				.build();
		
	  return new ResponseEntity<CustomResponse>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	/***
	 * testing connection
	 * 
	 * @return
	 */
	@GetMapping("/test")
	public boolean checkConnection() {
		return true;
	}

	/***
	 * 
	 * @param flights
	 * @param errors
	 * @return
	 */
	@PostMapping("/addFlight")
	public CustomResponse addFlightData(@Valid @RequestBody Flights flights, BindingResult errors) {

		List<HashMap<String, String>> error = null;

		try {
			if (errors.hasErrors()) {
				logger.info("validation error occurs in addFlightData");
				error = usefulService.FetchError(errors);

				return CustomResponse.builder().status(false).message(FlyBookConstant.VALIDATION_FAILED).data(error)
						.build();
			} 
			return flightBookingService.addFlight(flights);
			//return CustomResponse.builder().status(false).message("Validation failed").data(null).build();
		} catch (Exception e) {
			logger.info("exception");
			return CustomResponse.builder().status(false).message(FlyBookConstant.SOMETHING_WENT_WRONG).data(e.toString()).build();
		}
	}
	
	@PostMapping("/searchFlight")
	public CustomResponse searchFlight(@Valid @RequestBody FlightSearch flightSearch,BindingResult errors){
		
		List<HashMap<String, String>> error = null;
		try {
			
			if (errors.hasErrors()) {
				logger.info("validation error occurs in addFlightData");
				error = usefulService.FetchError(errors);

				return CustomResponse.builder().status(false).message(FlyBookConstant.VALIDATION_FAILED).data(error)
						.build();
			}
			return flightBookingService.searchFlight(flightSearch);
		} catch (Exception e) {
			logger.info("exception occurs in searchFlight");
			return CustomResponse.builder().status(false).message(FlyBookConstant.SOMETHING_WENT_WRONG).data(e.toString()).build();
		}
	}

}
