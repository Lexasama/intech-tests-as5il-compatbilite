package com.intech.comptabilite.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class LigneId implements Serializable {
	
	@Transient
	private static final long serialVersionUID = -1023248724908108139L;
	
	private Integer ecritureId;
	private Integer ligneId;
	
	public LigneId() {
	}
	
	public LigneId(Integer ecritureId, Integer ligneId) {
		super();
		this.ecritureId = ecritureId;
		this.ligneId = ligneId;
	}
	
	public Integer getEcritureId() {
		return ecritureId;
	}
	
	public void setEcritureId(Integer ecritureId) {
		this.ecritureId = ecritureId;
	}
	
	public Integer getLigneId() {
		return ligneId;
	}
	
	public void setLigneId(Integer ligneId) {
		this.ligneId = ligneId;
	}	

}
