package com.meli.tech.mutants.controller.request;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meli.tech.mutants.validator.ValidDNACharacter;
import com.meli.tech.mutants.validator.ValidDNASequence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MutantRequest {

	@Valid
	@JsonProperty(value = "dna")
	@ValidDNASequence(message = "Invalid DNA Sequence")
	@ValidDNACharacter(message = "There are invalid characters in the DNA Sequence")
	private String[] dna;

}
