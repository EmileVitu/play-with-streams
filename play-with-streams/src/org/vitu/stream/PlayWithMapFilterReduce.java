package org.vitu.stream;

import java.util.Comparator;
import java.util.List;

public class PlayWithMapFilterReduce {

	public static void main(String[] args) {

		List<String> strings = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
		
		// Jusqu'a present nous avons vu l'operation de mapping et l'operation de filtrage
		// --> Le mapping ne change pas le nombre d'elements traites, mais on change le type des elements
		int maxLength =
		strings.stream() // Stream<String>
			.map(s -> s.length()) // Stream<Integer>
			.max(Comparator.naturalOrder()) // L'operation max retourne un optional
			.orElseThrow(); // Puisque c'est un optional, get(), ne fonctionnera pas
		
		System.out.println("Longueur max = " + maxLength); // Retourne 5
		
		// Maintenant que se passe t'il si on reprends le code qui permet de lire le fichier
		
	}

}
