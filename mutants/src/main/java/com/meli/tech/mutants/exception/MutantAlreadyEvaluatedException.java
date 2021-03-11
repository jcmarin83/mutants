package com.meli.tech.mutants.exception;

public class MutantAlreadyEvaluatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MutantAlreadyEvaluatedException() {
		super("Mutant already evaluated");
	}
	
}
