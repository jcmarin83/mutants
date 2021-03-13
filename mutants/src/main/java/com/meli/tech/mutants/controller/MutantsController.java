package com.meli.tech.mutants.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.tech.mutants.controller.request.MutantRequest;
import com.meli.tech.mutants.controller.response.StatsResponse;
import com.meli.tech.mutants.service.MutantsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Mutants Controller")
@RestController
public class MutantsController {

	@Autowired
	private MutantsService mutantsService;

	@PostMapping(path = "/mutant")
	@ApiOperation(value = "POST\" Check if a person is a mutant \"", notes = "Evaluates if a person is a mutant or not, to do this, "
			+ "it's necessary the DNA sequence to be evaluated (e.g. \"GTGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"). \n"
			+ "A person is a mutant if there is 4 consecutives equal characters in a nitrogene base in any direction "
			+ "(by row, by column or by main or secondary diagonals")
	public ResponseEntity<Void> validateMutant(@Valid @NotNull @RequestBody MutantRequest request) {
		boolean response = mutantsService.isMutant(request.getDna());
		return response ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(path = "/stats")
	@ApiOperation(value = "GET\" Get mutant statistics \"", notes = "Provide statistics about the persons evaluated in the application: "
			+ "Cuantity of mutants, humans and mutants ratio", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatsResponse> getStats() {
		return ResponseEntity.ok(mutantsService.getMutantsStats());
	}
}
