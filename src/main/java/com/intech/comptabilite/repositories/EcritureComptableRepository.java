package com.intech.comptabilite.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intech.comptabilite.model.EcritureComptable;

@Repository
public interface EcritureComptableRepository extends CrudRepository<EcritureComptable, Integer> {

	public Optional<EcritureComptable> getByReference(String reference);
	
}
