package com.flybook.customCollection;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FlightSearch {

	@NotNull(message="source is mandatory")
	private String source;
	
	@NotNull(message="source is mandatory")
	private String destination;
	
	@NotNull(message="source is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date departDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date returnDate;
	
	@NotNull(message="roundtrip is mandatory")
	private boolean roundtrip;
	
	@NotNull(message="source is mandatory")
	@Min(value=1,message="passager is atleast 1")
	@Max(value=30,message="passagers are not more than 30")
	private int passenger;
	
}
