package com.flybook.collection;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.flybook.collection.City;

import lombok.Data;

@Datapublic class TravelRoute {

	@Id
	private String id;
	private City sourceCity;
	private City destinationCity;
	private List<String> flightId;
}
