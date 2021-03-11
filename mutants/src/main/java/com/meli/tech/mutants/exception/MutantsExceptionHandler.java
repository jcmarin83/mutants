package com.meli.tech.mutants.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.meli.tech.mutants.controller.response.ErrorResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class MutantsExceptionHandler {

	@ExceptionHandler(value = { MutantAlreadyEvaluatedException.class })
	public ResponseEntity<ErrorResponse> handleMutantAlreadyEvaluatedException(MutantAlreadyEvaluatedException e) {
		return ResponseEntity.badRequest().body(ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
				.message(e.getMessage()).build());
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String message = "Invalid Argument";
		Optional<ObjectError> objectError = e.getBindingResult().getAllErrors().stream().findFirst();
		if (objectError.isPresent()) {
			message = objectError.get().getDefaultMessage();
		}

		log.error(message, e);
		return ResponseEntity.badRequest()
				.body(ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(message).build());
	}

}
