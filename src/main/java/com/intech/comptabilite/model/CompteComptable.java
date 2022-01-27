package com.intech.comptabilite.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "compte_comptable")
public class CompteComptable {
	
	@Id
    @NotNull
    private Integer numero;

    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    public CompteComptable() {
    }

    public CompteComptable(Integer pNumero) {
        numero = pNumero;
    }

    public CompteComptable(Integer pNumero, String pLibelle) {
        numero = pNumero;
        libelle = pLibelle;
    }

    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer pNumero) {
        numero = pNumero;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
}
