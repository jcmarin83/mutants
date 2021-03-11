package com.meli.tech.mutants.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.meli.tech.mutants.validator.ValidDNASequence;

public class ValidDNASequenceValidator implements ConstraintValidator<ValidDNASequence, String[]>{

	@Override
	public boolean isValid(String[] value, ConstraintValidatorContext context) {
 		for (int i = 0; i < value.length; i++) {
 			if (value[i].length() != value.length) {
				return false;
			}
		}
		return true;
	}

	
}
