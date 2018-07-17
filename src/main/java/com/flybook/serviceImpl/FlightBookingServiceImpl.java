package com.flybook.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flybook.FlybookConstant.FlyBookConstant;
import com.flybook.Repository.FlightRepository;
import com.flybook.Repository.TravelRouteRepository;
import com.flybook.collection.Flights;
import com.flybook.collection.TravelRoute;
import com.flybook.customCollection.CustomResponse;
import com.flybook.customCollection.FlightSearch;
import com.flybook.customCollection.RoundTrip;
import com.flybook.service.FlightBookingService;
import com.flybook.service.UsefulServices;

@Component
public class FlightBookingServiceImpl implements FlightBookingService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private UsefulServices usefulService;
	@Autowired
	private TravelRouteRepository travelRouteRepository;

	@Override
	public CustomResponse addFlight(Flights flights) {

		boolean dateValidation = false;
		boolean cityValidation = false;
		Date now = new Date();

		try {
			logger.info(flights.toString());
			Flights flightCheck = this.flightRepository.findOne(flights.getFlightId());
			if (flightCheck != null)
				return CustomResponse.builder().status(false).message(FlyBookConstant.ALREADY_ON_RUN)
						.data(flights.getFlightId()).build();

			if (flights.getDepartureDate().before(now))
				return CustomResponse.builder().status(false).message(FlyBookConstant.Old_Date)
						.data(flights.getDepartureDate()).build();

			dateValidation = this.usefulService.dateValidation(flights.getDepartureDate(), flights.getArrivalDate());

			if (dateValidation)
				return CustomResponse.builder().status(false).message(FlyBookConstant.INVALID_ARRIVAL_DATE)
						.data(flights.getArrivalDate()).build();

			cityValidation = this.usefulService.cityValidation(flights.getSourceCity().getCityCode(),
					flights.getTeriminatingCity().getCityCode());

			if (cityValidation) {
				return CustomResponse.builder().status(false).message(FlyBookConstant.SAME_CITY)
						.data(flights.getSourceCity().getCityName()).build();
			}

			return this.usefulService.addFlightByAirline(flights);
			
		} catch (Exception e) {
			return CustomResponse.builder().status(false).message(FlyBookConstant.SOMETHING_WENT_WRONG)
					.data(e.toString()).build();
		}

	}

	@Override
	public CustomResponse searchFlight(FlightSearch flightSearch) {

		boolean dateValidation = false;
		boolean cityValidation = false;
		List<TravelRoute> returnList = null;
		Date now = new Date();
		List<Flights> oneWayflightList = new ArrayList<>();
		List<Flights> roundWayflightList = new ArrayList<>();

		try {
			if (flightSearch.getDepartDate().before(now))
				return CustomResponse.builder().status(false).message(FlyBookConstant.Old_Date)
						.data(flightSearch.getDepartDate()).build();

			if (flightSearch.isRoundtrip())
				dateValidation = this.usefulService.dateValidation(flightSearch.getDepartDate(),
						flightSearch.getReturnDate());

			if (dateValidation)
				return CustomResponse.builder().status(false).message(FlyBookConstant.INVALID_RETURN_DATE)
						.data(flightSearch.getReturnDate()).build();

			cityValidation = this.usefulService.cityValidation(flightSearch.getSource(), flightSearch.getDestination());
			if (cityValidation) {
				return CustomResponse.builder().status(false).message(FlyBookConstant.SAME_CITY)
						.data(flightSearch.getSource()).build();
			}

			List<TravelRoute> listTravelRoute = this.travelRouteRepository
					.findBySourceAndDestination(flightSearch.getSource(), flightSearch.getDestination());

			if (flightSearch.isRoundtrip())
				returnList = this.travelRouteRepository.findBySourceAndDestination(flightSearch.getDestination(),
						flightSearch.getSource());

			if (listTravelRoute!=null && listTravelRoute.size()>0) {
				logger.info("searching for one way fight");
				for (String flightId : listTravelRoute.get(0).getFlightId()) {
					Flights flight = this.flightRepository.findOne(flightId);
					Date requiredDate = this.usefulService.setDateFormatYYYY_MM_DD(flight.getDepartureDate());
					Date flightDate = this.usefulService.setDateFormatYYYY_MM_DD(flightSearch.getDepartDate());
					if (requiredDate.equals(flightDate))
						oneWayflightList.add(flight);
				}

				if (returnList!=null) {
					if (flightSearch.isRoundtrip() && flightSearch.getReturnDate() != null) {
						logger.info("searching for returning fight");
						for (String flightId : returnList.get(0).getFlightId()) {
							Flights flight = this.flightRepository.findOne(flightId);
							Date requiredDate = this.usefulService.setDateFormatYYYY_MM_DD(flight.getDepartureDate());
							Date flightDate = this.usefulService.setDateFormatYYYY_MM_DD(flightSearch.getReturnDate());
							if (requiredDate.equals(flightDate))
								roundWayflightList.add(flight);
						}
					}

					return CustomResponse.builder().status(true).message(FlyBookConstant.FLIGHT_FOUNDS)
							.data(RoundTrip.builder().oneWay(oneWayflightList).roundWay(roundWayflightList).build())
							.build();
				}

				return CustomResponse.builder().status(true).message(FlyBookConstant.FLIGHT_FOUNDS).data(oneWayflightList)
						.build();
			} else {
				return CustomResponse.builder().status(true).message(FlyBookConstant.NO_FLIGHT_FOUNDS)
						.data(oneWayflightList).build();
			}
		} catch (Exception e) {
			return CustomResponse.builder().status(false).message(FlyBookConstant.SOMETHING_WENT_WRONG)
					.data(e.toString()).build();
		}

	}

}
