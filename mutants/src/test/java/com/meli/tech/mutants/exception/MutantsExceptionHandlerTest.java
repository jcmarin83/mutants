package com.meli.tech.mutants.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.meli.tech.mutants.controller.response.ErrorResponse;

class MutantsExceptionHandlerTest {

	private MutantsExceptionHandler handler = new MutantsExceptionHandler();

	@Test
	void testHandleMutantAlreadyEvaluatedException() {
		ResponseEntity<ErrorResponse> response = handler.handleMutantAlreadyEvaluatedException(
				new MutantAlreadyEvaluatedException());

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
		assertEquals("Mutant already evaluated", response.getBody().getMessage());
	}
}
