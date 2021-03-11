package com.meli.tech.mutants.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {

	@JsonProperty(value = "count_mutant_dna")
	private Integer countMutants;

	@JsonProperty(value = "count_human_dna")
	private Integer countHumans;

	@JsonProperty(value = "ratio")
	private Double ratio;
}
