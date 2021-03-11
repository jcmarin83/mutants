package com.meli.tech.mutants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.meli.tech.mutants.controller.response.StatsResponse;
import com.meli.tech.mutants.exception.MutantAlreadyEvaluatedException;
import com.meli.tech.mutants.model.Person;
import com.meli.tech.mutants.repository.PersonsRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MutantsService {

	private static final Integer ZERO = 0;

	@Autowired
	private PersonsRepository personsRepository;

	/**
	 * Check if a person is a mutant according to his/her dna
	 * 
	 * @param dna
	 * @return boolean
	 */
	public boolean isMutant(String[] dna) {
		boolean isMutant = false;
		char[][] dnaMatrix = getCharMatrix(dna);

		for (int i = 0; i < dnaMatrix.length; i++) {
			// Check mutant by row
			if (isMutant(dnaMatrix[i])) {
				log.info("Mutant by ROW");
				isMutant = true;
				break;
			} else {
				// Check mutant by Column
				if (isMutant(this.getColumn(dnaMatrix, i))) {
					log.info("Mutant by COLUMN");
					isMutant = true;
					break;
				}
			}
		}

		// Check to avoid unnecessary loops
		if (!isMutant) {
			// Check mutant by main diagonal
			if (isMutant(getMainDiagonal(dnaMatrix))) {
				log.info("Mutant by MAIN DIAGONAL");
				isMutant = true;
			} else {
				// Check mutant by main diagonal
				if (isMutant(getSecondaryDiagonal(dnaMatrix))) {
					log.info("Mutant by SECONDARY DIAGONAL");
					isMutant = true;
				}
			}
		}

		storeMutantData(dna, isMutant);

		return isMutant;
	}

	public StatsResponse getMutantsStats() {
		Integer countMutants = personsRepository.countMutant();
		Integer countHumans = personsRepository.countHuman();
		Double ratio = 0d;
		if (!ZERO.equals(countHumans)) {
			ratio = countMutants.doubleValue() / countHumans.doubleValue();
		}

		return StatsResponse.builder().countMutants(countMutants).countHumans(countHumans).ratio(ratio).build();
	}

	/**
	 * Save a person and the mutant evaluation in the DB
	 * @param dna
	 * @param result
	 */
	private void storeMutantData(String[] dna, boolean result) {
		String dnaToStore = StringUtils.arrayToCommaDelimitedString(dna);
		Person person = Person.builder().dna(dnaToStore).mutant(result).build();

		if (personsRepository.findByDna(dnaToStore).isPresent()) {
			throw new MutantAlreadyEvaluatedException();
		}
		
		personsRepository.save(person);
	}

	/**
	 * Method to convert the Array into a char matrix to perform calculations
	 * 
	 * @param data
	 * @return char[][]
	 */
	private char[][] getCharMatrix(String[] data) {
		char[][] matrix = new char[data.length][];
		for (int i = 0; i < data.length; i++) {
			matrix[i] = data[i].toUpperCase().toCharArray();
		}

		return matrix;
	}

	/**
	 * Get the column according to the index
	 * 
	 * @param dnaMatrix
	 * @param index
	 * @return char[]
	 */
	private char[] getColumn(char[][] dnaMatrix, int index) {
		char[] column = new char[dnaMatrix.length];
		for (int i = 0; i < column.length; i++) {
			column[i] = dnaMatrix[i][index];
		}
		return column;
	}

	/**
	 * Get the main diagonal of the matrix
	 * 
	 * @param dnaMatrix
	 * @return char[]
	 */
	private char[] getMainDiagonal(char[][] dnaMatrix) {
		char[] mainDiagonal = new char[dnaMatrix.length];
		for (int i = 0; i < dnaMatrix.length; i++) {
			for (int j = 0; j < dnaMatrix.length; j++) {
				if (i == j) {
					mainDiagonal[i] = dnaMatrix[i][j];
				}
			}
		}

		return mainDiagonal;
	}

	/**
	 * Get the secondary diagonal of the matrix
	 * 
	 * @param dnaMatrix
	 * @return char[]
	 */
	private char[] getSecondaryDiagonal(char[][] dnaMatrix) {
		char[] secondaryDiagonal = new char[dnaMatrix.length];
		for (int i = 0; i < dnaMatrix.length; i++) {
			for (int j = 0; j < dnaMatrix.length; j++) {
				if (i + j == dnaMatrix.length - 1) {
					secondaryDiagonal[i] = dnaMatrix[i][j];
				}
			}
		}

		return secondaryDiagonal;
	}

	/**
	 * Validate if there are 4 equals characters to validate if it's a mutant
	 * 
	 * @param array
	 * @return
	 */
	private boolean isMutant(char[] array) {
		int countSameLetter = 0;
		char letter = 'Z';
		for (int i = 0; i < array.length; i++) {
			if (letter != array[i]) {
				letter = array[i];
				countSameLetter = 1;
			} else {
				countSameLetter++;
			}

			if (countSameLetter == 4) {
				return true;
			}
		}
		return false;
	}

}
