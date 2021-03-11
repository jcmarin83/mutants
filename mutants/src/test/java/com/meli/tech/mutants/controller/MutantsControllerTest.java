package com.meli.tech.mutants.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.tech.mutants.controller.request.MutantRequest;
import com.meli.tech.mutants.controller.response.StatsResponse;
import com.meli.tech.mutants.service.MutantsService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MutantsControllerTest {

	private static final String MUTANT_URL = "/mutant";
	private static final String STATS_URL = "/stats";

	private static final String[] MUTANT_DNA = { "GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] HUMAN_DNA = { "ATGCAC", "CCGTAC", "TTAAGT", "AGAAGG", "CCCGTA", "TCACTG" };
	private static final String[] BAD_DNA_SEQUENCE_MISSING_LETTER = { "ATGCA", "CCGTAC", "TTAAGT", "AGAAGG", "CCCGTA", "TCACTG" };
	private static final String[] BAD_DNA_SEQUENCE_WRONG_LETTER = { "ATGHCA", "CCGTAC", "TTAAGT", "AGAAGG", "CCCGTA", "TCACTG" };

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MutantsService mutantsService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void testValidateMutant() throws Exception {
		when(mutantsService.isMutant(any(String[].class))).thenReturn(true);
		String request = objectMapper.writeValueAsString(MutantRequest.builder().dna(MUTANT_DNA).build());
		this.mockMvc.perform(post(MUTANT_URL).contentType(MediaType.APPLICATION_JSON).content(request)).andDo(print())
				.andExpect(status().isOk());
		verify(mutantsService, times(1)).isMutant(MUTANT_DNA);
	}

	@Test
	void testValidateMutantHuman() throws Exception {
		when(mutantsService.isMutant(any(String[].class))).thenReturn(false);
		String request = objectMapper.writeValueAsString(MutantRequest.builder().dna(HUMAN_DNA).build());
		this.mockMvc.perform(post(MUTANT_URL).contentType(MediaType.APPLICATION_JSON).content(request)).andDo(print())
				.andExpect(status().isForbidden());
		verify(mutantsService, times(1)).isMutant(HUMAN_DNA);
	}

	@Test
	void testValidateMutantBadRequestForIncompleteDNASequence() throws Exception {
		String request = objectMapper
				.writeValueAsString(MutantRequest.builder().dna(BAD_DNA_SEQUENCE_MISSING_LETTER).build());
		this.mockMvc.perform(post(MUTANT_URL).contentType(MediaType.APPLICATION_JSON).content(request)).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("Invalid DNA Sequence")));
		verify(mutantsService, times(0)).isMutant(any(String[].class));
	}

	@Test
	void testValidateMutantBadRequestForWrongLetterInDNASequence() throws Exception {
		String request = objectMapper
				.writeValueAsString(MutantRequest.builder().dna(BAD_DNA_SEQUENCE_WRONG_LETTER).build());
		this.mockMvc.perform(post(MUTANT_URL).contentType(MediaType.APPLICATION_JSON).content(request)).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("There are invalid characters in the DNA Sequence")));
		verify(mutantsService, times(0)).isMutant(any(String[].class));
	}

	@Test
	void testGetStats() throws Exception {
		StatsResponse response = StatsResponse.builder().countMutants(1).countHumans(2).ratio(0.5d).build();
		String jsonResponse = objectMapper.writeValueAsString(response);
		when(mutantsService.getMutantsStats()).thenReturn(response);
		this.mockMvc.perform(get(STATS_URL)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(jsonResponse)));
		verify(mutantsService, times(1)).getMutantsStats();
	}

}
