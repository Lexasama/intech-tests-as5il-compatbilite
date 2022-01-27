package com.intech.comptabilite.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intech.comptabilite.model.SequenceEcritureComptable;
import com.intech.comptabilite.model.SequenceId;

@Repository
public interface SequenceEcritureComptableRepository extends CrudRepository<SequenceEcritureComptable, SequenceId> {
	
}
