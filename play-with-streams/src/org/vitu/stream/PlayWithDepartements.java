package org.vitu.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.vitu.stream.model.Commune;
import org.vitu.stream.model.Departement;

public class PlayWithDepartements {

	public static void main(String[] args) throws IOException {
		
		// Appel a la methode
		String path = "files/departements.txt";
		List<Departement> departements = readDepartements(path);
		
		departements.forEach(System.out::println);
		
		List<Commune> communes = readCommunes("files/communes.txt");
		System.out.println("# Communes = " + communes.size());
		
		// Maintenant pour distribuer les communes par departement, nous allons utiliser l'API Collectors dans une table de hashage
		
		// D'abord nous allons creer la function qui nous retournera le departement en fonction de la commnune
		Function<Commune, String> toCodeDepartement = 
			commune -> 
				commune.getCodePostal().startsWith("97") ?
					commune.getCodePostal().substring(0, 3) : 
					commune.getCodePostal().substring(0, 2);
		// Maintenant pour tester que notre fonction fonctionne bien		
		// communes.stream()
		// 	.map(toCodeDepartement)
		// 	.distinct()
		// 	.forEach(System.out::println);
		
		// Pour regrouper les sous-stream en list<>, nous pouvons passer un downstream collector au collector groupingBy()
		Map<String, List<Commune>> communeByCodeDepartementList =
			communes.stream()
				.collect(Collectors.groupingBy(toCodeDepartement, Collectors.toList()));

		Map<String, Set<Commune>> communeByCodeDepartementSet =
				communes.stream()
					.collect(Collectors.groupingBy(toCodeDepartement, Collectors.toSet()));
		
		System.out.println("# of map = " + communeByCodeDepartementList.size());
		System.out.println("# of map = " + communeByCodeDepartementSet.size());
		
		Map<String, Long> numberOfCommunesByCodeDepartement = 
			communes.stream()
				.collect(
						Collectors.groupingBy(
								toCodeDepartement, 
								Collectors.counting())
						);
		System.out.println("# communes " + numberOfCommunesByCodeDepartement);
		
		communeByCodeDepartementList.get("93")
			.forEach(System.out::println);
		
		communeByCodeDepartementSet.get("78")
		.forEach(System.out::println);
		
		long count = communeByCodeDepartementList.get("93").stream()
				// .count();
				.collect(Collectors.counting()); // Nous pouvons aussi compter aeec un collector
		System.out.println("# communes dans le 93 : " + count);
		
		// Nombre total de communes
		// Departement departement = departements.get(0);
		Consumer<Departement> addCommunesToDepartement = 
				d -> communes.stream()
					.filter(c -> c.getCodePostal().startsWith(d.getCodePostal()))
					.forEach(d::addCommune);
		departements.forEach(addCommunesToDepartement);
		departements.forEach(d -> System.out.println(d.getNom() + " possède " + d.getCommunes().size() + " communes."));
		
		// Flatmap
		Function<Departement, Stream<Commune>> flatMapper = d -> d.getCommunes().stream();
		
		long countCommunes = departements.stream().flatMap(flatMapper).count();
		System.out.println("# communes = " + countCommunes);
		
		// Le departement qui a le plus de communes
		// communes {78=4, 974=23, 93=4}
		Map.Entry<String, Long> maxEntry = numberOfCommunesByCodeDepartement.entrySet().stream()
				// .max(Comparator.comparing(entry -> entry.getValue()))
				.max(Map.Entry.comparingByValue())						// Equivalent a la ligne precedente
				.orElseThrow();											// Car le max() nous retourne un optional
		String maxCodeDepartement = maxEntry.getKey();
		Long maxCountOfCommunes = maxEntry.getValue();
		System.out.println(maxCodeDepartement + " -> " + maxCountOfCommunes);
		
		Set<String> keySet = numberOfCommunesByCodeDepartement.keySet();
		Collection<Long> values = numberOfCommunesByCodeDepartement.values();
		Set<Entry<String, Long>> entrySet = numberOfCommunesByCodeDepartement.entrySet();
	}

	private static List<Commune> readCommunes(String path) throws IOException {
		Path pathToCommunes = Path.of(path);
		
		Predicate<String> isComment = line -> line.startsWith("#");

		Function<String, String> toNom = l -> l.substring(0,l.indexOf(" ("));
		
		Function<String, String> toCodePostal = l -> l.substring(l.indexOf(" (") + 2, l.length() - 1);
		
		Function<String, Commune> toCommune = l -> new Commune(toNom.apply(l), toCodePostal.apply(l));
		
		List<Commune> communes = 
			Files.lines(pathToCommunes)
				.filter(isComment.negate())
				.map(toCommune)
				.collect(Collectors.toList());
		return communes;
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
	
	// String line = "La Plaine-des-Palmistes (97406)";
	
	// String nom = line.substring(0, line.indexOf(" ("));
	// String codePostal = line.substring(line.indexOf(" (" ) + 2,  line.length() - 1);		
	// System.out.println("nom = " + nom);
	// System.out.println("code postal = " + codePostal);
}
