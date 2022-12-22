package org.vitu.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FirstContactWithStreams {
	
	public static void main(String... args) {
		
		// Stream sur une liste
		List<String> strings = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
		System.out.println("Stream sur une liste");
		strings.stream().forEach(System.out::println);
		
		// Stream sur un tableau
		String[] arrayOfStrings = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
		// arrayOfStrings.stream(); --> ne fonctionne pas, car la methode stream() ne s'applique pas sur ce type d'objets. 
		System.out.println("\nStream sur un tableau");
		Arrays.stream(arrayOfStrings).forEach(System.out::println);
		
		// Stream a partir d'elements
		System.out.println("\nStream sur des élémnets");
		Stream.of("one", "two", "three", "four").forEach(System.out::println);
		
		// Stream sur les lignes d'un fichier (repertoire files cree a la racine du projet et non dans le repertoire source)
		
	
	}
}
