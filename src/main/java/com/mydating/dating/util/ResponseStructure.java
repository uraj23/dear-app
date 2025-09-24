package com.mydating.dating.util;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ResponseStructure<T>{
	private Integer status;
	private String message;
	private T body;

}
