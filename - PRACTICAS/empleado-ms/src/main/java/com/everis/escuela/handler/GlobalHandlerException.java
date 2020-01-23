package com.everis.escuela.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;

@ControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(ResourceNotFoundException.class)//
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex,WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(),ex.getMessage(),request.getDescription(false));
		
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationException.class)//
	public ResponseEntity<?> validationDNIException(ValidationException ex,WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.OK);
	}
	
}
