package com.meli.tech.mutants.validator.impl;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.meli.tech.mutants.validator.ValidDNACharacter;

public class ValidDNACharacterValidator implements ConstraintValidator<ValidDNACharacter, String[]>{

	private static final String CHAR_REGEX = "^[ATCG]+";
	
	private Pattern pattern = Pattern.compile(CHAR_REGEX);
	
	@Override
	public boolean isValid(String[] value, ConstraintValidatorContext context) {
		boolean isValid = true;
		for (int i = 0; i < value.length; i++) {
			String row = value[i];
			isValid = pattern.matcher(row).matches();
			
			if (!isValid) {
				return isValid;
			}
		}
		return isValid;
	}
}
