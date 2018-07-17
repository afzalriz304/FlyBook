package com.flybook.customCollection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomResponse {

	private boolean status;
	private String message;
	private Object data;
}
