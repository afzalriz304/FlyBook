package com.flybook.collection;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {

	@NotNull(message="City code must not be null")
	private String cityCode;
	
	@NotNull(message="City name must not be null")
	private String cityName;
	
}
