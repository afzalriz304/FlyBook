package com.flybook.customCollection;

import java.util.List;

import com.flybook.collection.Flights;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoundTrip {

	private List<Flights> oneWay;
	private List<Flights> roundWay;
}
