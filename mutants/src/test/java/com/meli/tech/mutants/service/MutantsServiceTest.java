package com.meli.tech.mutants.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meli.tech.mutants.controller.response.StatsResponse;
import com.meli.tech.mutants.exception.MutantAlreadyEvaluatedException;
import com.meli.tech.mutants.model.Person;
import com.meli.tech.mutants.repository.PersonsRepository;

@ExtendWith(MockitoExtension.class)
class MutantsServiceTest {

	private static final String[] DNA_MUTANT_BY_ROW = {"GTGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
	private static final String[] DNA_MUTANT_BY_COLUMN = {"GTGCGA","CAGTGC","TTATGT","AGAAGG","CCCGTA","TCACTG"};
	private static final String[] DNA_MUTANT_BY_MAIN_DIAGONAL = {"ATGCAA","CAGTGC","TTATGT","AGAAGG","CCCGTA","TCACTG"};
	private static final String[] DNA_MUTANT_BY_SECONDARY_DIAGONAL = {"ATGCAA","CCGTAC","TTAAGT","AGAAGG","CCCGTA","TCACTG"};
	private static final String[] DNA_HUMAN = {"ATGCAC","CCGTAC","TTAAGT","AGAAGG","CCCGTA","TCACTG"};
	
	@Mock
	private PersonsRepository personsRepository;
	
	@InjectMocks
	private MutantsService mutantsService;
	
	
	@BeforeEach
	public void setUp() {
		lenient().when(personsRepository.findByDna(anyString())).thenReturn(Optional.empty());
	}
	
	@Test
	void testIsMutantByRowValidation() {
		assertTrue(mutantsService.isMutant(DNA_MUTANT_BY_ROW));
		verify(personsRepository, times(1)).save(any(Person.class));
	}
	
	@Test
	void testIsMutantByColumnValidation() {
		assertTrue(mutantsService.isMutant(DNA_MUTANT_BY_COLUMN));
		verify(personsRepository, times(1)).save(any(Person.class));
	}
	
	@Test
	void testIsMutantByMainDiagonalValidation() {
		assertTrue(mutantsService.isMutant(DNA_MUTANT_BY_MAIN_DIAGONAL));
		verify(personsRepository, times(1)).save(any(Person.class));
	}
	
	@Test
	void testIsMutantBySecondaryDiagonalValidation() {
		assertTrue(mutantsService.isMutant(DNA_MUTANT_BY_SECONDARY_DIAGONAL));
		verify(personsRepository, times(1)).save(any(Person.class));
	}
	
	@Test
	void testIsMutantAlreadyValidated() {
		assertThrows(MutantAlreadyEvaluatedException.class, () -> {
			when(personsRepository.findByDna(anyString())).thenReturn(Optional.of(new Person()));
			mutantsService.isMutant(DNA_MUTANT_BY_ROW);
		});
	}
	
	@Test
	void testIsHuman() {
		assertFalse(mutantsService.isMutant(DNA_HUMAN));
		verify(personsRepository, times(1)).save(any(Person.class));
	}
	
	@Test
	void testGetStats() {
		when(personsRepository.countMutant()).thenReturn(40);
		when(personsRepository.countHuman()).thenReturn(100);
		StatsResponse response = mutantsService.getMutantsStats();
		
		assertNotNull(response);
		assertEquals(40, response.getCountMutants());
		assertEquals(100, response.getCountHumans());
		assertEquals(0.4d, response.getRatio());
	}
	
	@Test
	void testGetStatsNoHumans() {
		when(personsRepository.countMutant()).thenReturn(40);
		when(personsRepository.countHuman()).thenReturn(0);
		StatsResponse response = mutantsService.getMutantsStats();
		
		assertNotNull(response);
		assertEquals(40, response.getCountMutants());
		assertEquals(0, response.getCountHumans());
		assertEquals(0d, response.getRatio());
	}
}
