package com.intech.comptabilite.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intech.comptabilite.model.CompteComptable;

@Repository
public interface CompteComptableRepository extends CrudRepository<CompteComptable, Integer> {

}
