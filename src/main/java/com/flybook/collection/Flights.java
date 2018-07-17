package com.flybook.collection;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Flights {

	@Id
	@NotNull(message="flight id is compuslory")
	private String flightId;
	
	@NotNull(message="source city is compuslory")
	private City sourceCity;
	
	@NotNull(message="source city is compuslory")
	private City teriminatingCity;
	
	@NotNull(message="departure date is compuslory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
	private Date departureDate;
	
	@NotNull(message="arrival date is compuslory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
	private Date arrivalDate;
	
	@NotNull(message="base fare is compuslory")
	private double baseFare;
	
	@NotNull(message="airlines is compuslory")
	private String airlines;
	
	private boolean connectingFlight;
	
	private List<City> connectingCities;
	
	//days are as Sun,Mon,Tues,Wed,Thu,Fri,Sat for each byte of 7 digit
	@NotNull(message="days are mondatory")
	private long days;
}
