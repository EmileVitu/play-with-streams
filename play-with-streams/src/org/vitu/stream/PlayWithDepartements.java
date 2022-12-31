package org.vitu.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.vitu.stream.model.Departement;

public class PlayWithDepartements {

	public static void main(String[] args) throws IOException {
		
		// Appel a la methode
		List<Departement> departements = readDepartements("files/departements.txt");
		
		departements.forEach(System.out::println);
		
		Path pathToCommunes = Path.of("files/communes.txt");
		
	}

	private static List<Departement> readDepartements(String path) throws IOException {
		// Nous creons un path menant a la source de donnees
		Path pathToDepartements = Path.of(path);	
		// Nous creons un predicat qui teste si une ligne est une ligne de commentaire
		Predicate<String> isComment = line -> line.startsWith("#");
		// Nous creons une fonction (qui peut servir en parametre de map()) qui transforme un string en un autre string pour recuperer le code postal d'une ligne
		Function<String, String> toCodePostal = l -> l.substring(0, l.indexOf(" - "));
		// Nous creons une fonction (qui peut servir en parametre de map()) qui transforme un string en un autre string pour recuperer le nom d'une ligne		
		Function<String, String> toNom = l -> l.substring(l.indexOf(" - ") + 3);
		// Nous creons une derniere fonction qui combine les deux et que nous pourrons passer en parametre de map()
		Function<String, Departement> toDepartement = l -> new Departement(toCodePostal.apply(l), toNom.apply(l));
		// Nous creons la liste de departement en passant par des stream
		return Files.lines(pathToDepartements)		// Retourne toutes les lignes du fichier
				.filter(isComment.negate())			// Retourne toutes les lignes sauf les lignes de commentaires
				.map(toDepartement)					// Retourne des departements et nom des string
				.collect(Collectors.toList());		// Retourne une List<Departement>
	}

	// int indexOfSeparator = line.indexOf(" - ");
	// String codePostal = line.substring(0, indexOfSeparator);
	// String nom = line.substring(indexOfSeparator + 3);		
	// String codePostal = toCodePostal.apply(line);
	// String nom = toNom.apply(line);
	// String line = "93 - Seine Saint-Denis";
	
	// Departement departement = toDepartement.apply(line);
	
	// System.out.println("departement = " + departement);		
	// System.out.println("code postal = " + codePostal);
	// System.out.println("nom = " + nom);
	
}
