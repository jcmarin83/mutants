package com.meli.tech.mutants.validator.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ValidDNACharacterValidatorTest {

	private static final String[] VALID_DNA = { "GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] INVALID_DNA = { "GTFCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] INVALID_DNA_FIRST_LETTER = { "UTCCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] INVALID_DNA_FIRST_LETTER_LAST_SEQUENCE = { "CTCCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "KCACTG" };
	private static final String[] INVALID_DNA_LETTER_MIDDLE_SEQUENCE = { "CTCCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "KCACTG" };
	private static final String[] INVALID_DNA_SPECIAL_CHAR = { "@TCCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "KCACTG" };
	private static final String[] INVALID_DNA_SPACE_CHAR = { " TCCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "KCACTG" };

	private ValidDNACharacterValidator validator = new ValidDNACharacterValidator();

	@Test
	void testValidDNACharacter() {
		assertTrue(validator.isValid(VALID_DNA, null));
	}

	@Test
	void testValidDNACharacterInvalid() {
		assertFalse(validator.isValid(INVALID_DNA, null));
	}

	@Test
	void testValidDNACharacterInvalidFirstLetter() {
		assertFalse(validator.isValid(INVALID_DNA_FIRST_LETTER, null));
	}

	@Test
	void testValidDNACharacterInvalidFirstLetterLastSequence() {
		assertFalse(validator.isValid(INVALID_DNA_FIRST_LETTER_LAST_SEQUENCE, null));
	}

	@Test
	void testValidDNACharacterInvalidLetterMiddleSequence() {
		assertFalse(validator.isValid(INVALID_DNA_LETTER_MIDDLE_SEQUENCE, null));
	}

	@Test
	void testValidDNACharacterInvalidSpecialChar() {
		assertFalse(validator.isValid(INVALID_DNA_SPECIAL_CHAR, null));
	}

	@Test
	void testValidDNACharacterInvalidSpaceChar() {
		assertFalse(validator.isValid(INVALID_DNA_SPACE_CHAR, null));
	}
}
