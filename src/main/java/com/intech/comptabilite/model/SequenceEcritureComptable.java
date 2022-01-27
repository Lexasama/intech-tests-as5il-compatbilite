package com.intech.comptabilite.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(SequenceId.class)
@Table(name = "sequence_ecriture_comptable")
public class SequenceEcritureComptable {
	@Id
	private String journalCode;
	@Id
    private Integer annee;
    
    private Integer derniereValeur;

    // ==================== Constructeurs ====================
    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

    public SequenceEcritureComptable(String journalCode, Integer annee, Integer derniereValeur) {
		super();
		this.journalCode = journalCode;
		this.annee = annee;
		this.derniereValeur = derniereValeur;
	}

	// ==================== Getters/Setters ====================
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer pAnnee) {
        annee = pAnnee;
    }
    public Integer getDerniereValeur() {
        return derniereValeur;
    }
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }

}
