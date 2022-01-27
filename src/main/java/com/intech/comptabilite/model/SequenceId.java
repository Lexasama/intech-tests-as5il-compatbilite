package com.intech.comptabilite.model;

import java.io.Serializable;

import javax.persistence.Transient;

public class SequenceId implements Serializable {

	@Transient
	private static final long serialVersionUID = -3816603702192890820L;
	
	private String journalCode;
	private Integer annee;
	
	public SequenceId() {
	}
	
	public SequenceId(String code, Integer annee) {
		super();
		this.journalCode = code;
		this.annee = annee;
	}
	
	public String getJournalCode() {
		return journalCode;
	}
	public void setJournalCode(String code) {
		this.journalCode = code;
	}
	public Integer getAnnee() {
		return annee;
	}
	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

}
