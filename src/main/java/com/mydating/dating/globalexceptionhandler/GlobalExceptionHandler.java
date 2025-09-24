package com.mydating.dating.globalexceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mydating.dating.exceptionclasses.InvalidUserIdException;
import com.mydating.dating.exceptionclasses.NoSuchRecordFoundException;
import com.mydating.dating.util.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoSuchRecordFoundException.class)
	public ResponseEntity<?> noSuchRecordFoundExceptionHandler(NoSuchRecordFoundException e) {
		return ResponseEntity.ok(ResponseStructure.<String>builder().status(HttpStatus.NOT_FOUND.value())
				.message(e.getMessage()).body("No Recodrs Found").build());
	}
	
	@ExceptionHandler(InvalidUserIdException.class)
	public ResponseEntity<?> invalidUserIdExceptionHandler(InvalidUserIdException e){
		return ResponseEntity.ok(
				ResponseStructure.<String>builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message(e.getMessage())
				.body("No User Found")	
				.build()
				);
	}
}
