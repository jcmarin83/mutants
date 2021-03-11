package com.meli.tech.mutants.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.meli.tech.mutants.model.Person;

public interface PersonsRepository extends JpaRepository<Person, String>{

	Optional<Person> findByDna(String dna);
	
	@Query(value = "SELECT COUNT(p.dna) FROM Person p WHERE p.mutant = true")
	Integer countMutant();
	
	@Query(value = "SELECT COUNT(p.dna) FROM Person p WHERE p.mutant = false")
	Integer countHuman();
	
}
