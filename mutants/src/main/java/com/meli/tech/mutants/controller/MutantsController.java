package com.meli.tech.mutants.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	@ApiOperation(value = "POST\" Check if a person is a mutant \"")
	public ResponseEntity<Void> validateMutant(@Valid @NotNull @RequestBody MutantRequest request) {
		boolean response = mutantsService.isMutant(request.getDna());
		return response ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(path = "/stats")
	@ApiOperation(value = "GET\" Get mutant statistics \"")
	public ResponseEntity<StatsResponse> getStats() {
		return ResponseEntity.ok(mutantsService.getMutantsStats());
	}
}
