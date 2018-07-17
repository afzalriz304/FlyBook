package com.flybook.scheduledTask;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.flybook.Repository.FlightRepository;
import com.flybook.Repository.TravelRouteRepository;
import com.flybook.collection.Flights;
import com.flybook.collection.TravelRoute;
import com.flybook.service.UsefulServices;

@Component
public class Scheduler {

public final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private UsefulServices usefulServices;
	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private TravelRouteRepository travelRouteRepository;
	
	@Scheduled(cron = "0 0 12 * * ?")
	public void clearFlightData(){
		Date now	=	new Date();
		now	=	this.usefulServices.setDateFormatYYYY_MM_DD(now);
		List<Flights> list	=	this.flightRepository.findAll();
		Date flightArrival	=	null;
		for(Flights flight: list){
			flightArrival	=	this.usefulServices.setDateFormatYYYY_MM_DD(flight.getArrivalDate());
			if(flightArrival.before(now)){
				List<TravelRoute> flights	=	this.travelRouteRepository.findByFlightId(flight.getFlightId());
				TravelRoute route	=	this.travelRouteRepository.findOne(flights.get(0).getId());
				route.getFlightId().remove(flight.getFlightId());
				this.flightRepository.delete(flight);
				this.travelRouteRepository.save(route);
			}
				
		}
		
		
	}
}
