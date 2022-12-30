package org.vitu.stream.model;

import java.util.Objects;

public class Commune {

	private String nom;
	private String codePostal;
	
	public Commune(){
	}

	public Commune(String nom, String codePostal) {
		super();
		this.nom = nom;
		this.codePostal = codePostal;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	@Override
	public String toString() {
		return "Commune [nom=" + nom + ", codePostal=" + codePostal + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codePostal, nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commune other = (Commune) obj;
		return Objects.equals(codePostal, other.codePostal) && Objects.equals(nom, other.nom);
	}
}
