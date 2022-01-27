package com.intech.comptabilite.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "journal_comptable")
public class JournalComptable {

	@Id
    @NotNull
    @Size(min = 1, max = 5)
    private String code;

    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    public JournalComptable() {
    }

    public JournalComptable(String pCode, String pLibelle) {
        code = pCode;
        libelle = pLibelle;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String pCode) {
        code = pCode;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }

}
