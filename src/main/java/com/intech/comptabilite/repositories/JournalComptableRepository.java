package com.intech.comptabilite.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intech.comptabilite.model.JournalComptable;

@Repository
public interface JournalComptableRepository extends CrudRepository<JournalComptable, Integer> {

}
