package com.flybook.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.flybook.FlybookConstant.FlyBookConstant;
import com.flybook.Repository.FlightRepository;
import com.flybook.Repository.TravelRouteRepository;
import com.flybook.collection.City;
import com.flybook.collection.Flights;
import com.flybook.collection.TravelRoute;
import com.flybook.customCollection.CustomResponse;
import com.flybook.service.UsefulServices;
import com.google.gson.Gson;

@Component
public class UsefulServicesImpl implements UsefulServices {

	@Autowired
	private TravelRouteRepository travelRouteRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Override
	public List<HashMap<String, String>> FetchError(BindingResult bindingResult) {
		ArrayList<HashMap<String, String>> error = new ArrayList<HashMap<String, String>>();
		List<FieldError> errorList = bindingResult.getFieldErrors();
		errorList.forEach(err -> {
			HashMap<String, String> map = new HashMap<>();
			map.put(err.getField(), err.getDefaultMessage());
			error.add(map);
		});
		return error;
	}

	@Override
	public CustomResponse addFlightByAirline(Flights flights) {

		Calendar calendar = Calendar.getInstance();
		Flights flight = null;

		TravelRoute travelRoute = null;
		flight = Flights.builder().airlines(flights.getAirlines()).arrivalDate(flights.getArrivalDate())
				.departureDate(flights.getDepartureDate()).baseFare(flights.getBaseFare())
				.flightId(flights.getFlightId())
				.sourceCity(City.builder().cityCode(flights.getSourceCity().getCityCode())
						.cityName(flights.getSourceCity().getCityName()).build())
				.teriminatingCity(City.builder().cityCode(flights.getTeriminatingCity().getCityCode())
						.cityName(flights.getTeriminatingCity().getCityName()).build())
				.connectingCities(flights.getConnectingCities()).days(flights.getDays())
				.connectingFlight(flights.isConnectingFlight()).build();

		List<TravelRoute> flightSearch = null;

		flightSearch = this.travelRouteRepository.findBySourceAndDestination(flights.getSourceCity().getCityCode(),
				flights.getTeriminatingCity().getCityCode());
		if (flightSearch.isEmpty()) {
			travelRoute = new TravelRoute();
			travelRoute.setId(FlyBookConstant.RT + calendar.getTimeInMillis());
		} else {
			travelRoute = this.travelRouteRepository.findOne(flightSearch.get(0).getId());
		}

		List<String> list = travelRoute.getFlightId();
		if (list == null)
			list = new ArrayList<String>();

		list.add(flights.getFlightId());

		travelRoute.setDestinationCity(flights.getTeriminatingCity());
		travelRoute.setSourceCity(flights.getSourceCity());
		travelRoute.setFlightId(list);

		this.travelRouteRepository.save(travelRoute);
		this.flightRepository.save(flight);

		return CustomResponse.builder().status(true).message(FlyBookConstant.FLIGHT_ADDED_SUCCESSFULLY)
				.data(flights.getFlightId()).build();

	}

	@Override
	public boolean cityValidation(String source, String destination) {

		if (source.equalsIgnoreCase(destination))
			return true;

		return false;
	}

	@Override
	public boolean dateValidation(Date departDate, Date returnDate) {

		if (returnDate.before(departDate) || returnDate.equals(departDate))
			return true;

		return false;
	}

	@Override
	public Date setDateFormatYYYY_MM_DD(Date date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date YYYY_MM_DD = sdf.parse(date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate());
			return YYYY_MM_DD;
		} catch (Exception e) {
			return null;
		}
	}

}
