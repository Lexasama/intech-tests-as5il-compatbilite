package com.intech.comptabilite.service.entityservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intech.comptabilite.model.CompteComptable;
import com.intech.comptabilite.repositories.CompteComptableRepository;

@Service
public class CompteComptableService {

	@Autowired
	private CompteComptableRepository repository;
	
    /**
     * Renvoie le {@link CompteComptable} de numéro {@code pNumero} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link CompteComptable}
     * @param pNumero le numero du {@link CompteComptable} à chercher
     * @return {@link CompteComptable} ou {@code null}
     */
    public CompteComptable getByNumero(List<? extends CompteComptable> pList, Integer pNumero) {
        CompteComptable vRetour = null;
        for (CompteComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getNumero(), pNumero)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }

	public List<CompteComptable> getListCompteComptable() {
		List<CompteComptable> result = new ArrayList<CompteComptable>();
		repository.findAll().forEach(result::add);
		return result;
	}
	
}
