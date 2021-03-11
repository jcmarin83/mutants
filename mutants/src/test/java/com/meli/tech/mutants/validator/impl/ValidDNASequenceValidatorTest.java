package com.meli.tech.mutants.validator.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ValidDNASequenceValidatorTest {
	
	private static final String[] VALID_DNA = { "GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] INVALID_DNA = { "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] INVALID_DNA_MISSING_LETTER_FIRST_SEQ = { "GTGCA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] INVALID_DNA_MISSING_LETTER_LAST_SEQ = { "GTGCCA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCATG" };
	private static final String[] INVALID_DNA_MISSING_LETTER_MIDDLE_SEQ = { "GTGCCA", "CAGTGC", "TTATT", "AGAAGG", "CCCCTA", "TCATCG" };

	private ValidDNASequenceValidator validator = new ValidDNASequenceValidator();
	
	@Test
	void testValidSequence() {
		assertTrue(validator.isValid(VALID_DNA, null));
	}
	
	@Test
	void testValidSequenceInvalid() {
		assertFalse(validator.isValid(INVALID_DNA, null));
	}
	
	@Test
	void testValidSequenceInvalidMissingLetterFirstSeq() {
		assertFalse(validator.isValid(INVALID_DNA_MISSING_LETTER_FIRST_SEQ, null));
	}
	
	@Test
	void testValidSequenceInvalidMissingLetterLastSeq() {
		assertFalse(validator.isValid(INVALID_DNA_MISSING_LETTER_LAST_SEQ, null));
	}
	
	@Test
	void testValidSequenceInvalidMissingLetterMiddleSeq() {
		assertFalse(validator.isValid(INVALID_DNA_MISSING_LETTER_MIDDLE_SEQ, null));
	}
}
