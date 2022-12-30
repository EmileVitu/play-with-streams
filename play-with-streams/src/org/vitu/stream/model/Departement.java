package org.vitu.stream.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Departement {
		
		// Nous creons la classe departement, qui est un bean, possedant deux proprietes, code postal et nom
		// Nous creons d'abord les deux champs, puis les getter and setter, ce sont ces derniers qui font que ces champs deviennent des proprietes
	
		private String codePostal;
		private String nom;
		private List<Commune> communes = new ArrayList<>();
		
		
		public Departement() {
		}

		public Departement(String codePostal, String nom) {
			this.codePostal = codePostal;
			this.nom = nom;
		}
		
		public String getCodePostal() {
			return codePostal;
		}

		public void setCodePostal(String codePostal) {
			this.codePostal = codePostal;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}
		
		public void addCommune(Commune commune) {
			this.communes.add(commune);
		}
		
		// Ceci retourne une version immutable de la List
		public List<Commune> getCommunes() {
			return Collections.unmodifiableList(this.communes);
		}
		
		@Override
		public String toString() {
			return "Departement [codePostal=" + codePostal + ", nom=" + nom + "]";
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
			Departement other = (Departement) obj;
			return Objects.equals(codePostal, other.codePostal) && Objects.equals(nom, other.nom);
		}
}
