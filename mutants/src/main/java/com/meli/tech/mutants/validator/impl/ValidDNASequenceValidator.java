package com.meli.tech.mutants.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.meli.tech.mutants.validator.ValidDNASequence;

public class ValidDNASequenceValidator implements ConstraintValidator<ValidDNASequence, String[]>{

	@Override
	public boolean isValid(String[] value, ConstraintValidatorContext context) {
 		for (String row : value) {
 			if (row.length() != value.length) {
				return false;
			}
		}
 		
		return true;
	}

	
}
