package com.intech.comptabilite.service.entityservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intech.comptabilite.model.SequenceEcritureComptable;
import com.intech.comptabilite.model.SequenceId;
import com.intech.comptabilite.repositories.SequenceEcritureComptableRepository;
import com.intech.comptabilite.service.exceptions.NotFoundException;

@Service
public class SequenceEcritureComptableService {

	@Autowired
	private SequenceEcritureComptableRepository repository;
	
	public int getDernierValeurByCodeAndAnnee(String journalCode, Integer annee) throws NotFoundException {
		Optional<SequenceEcritureComptable> seq =
				repository.findById(new SequenceId(journalCode, annee));
		if(seq.isEmpty()) {
			throw new NotFoundException();
		} else {
			return seq.get().getDerniereValeur();
		}
	}
	
	public void upsert(SequenceEcritureComptable sequenceEcritureComptable) {
		repository.save(sequenceEcritureComptable);
	}
	
}
