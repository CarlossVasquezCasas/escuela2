package com.everis.escuela.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;

}
