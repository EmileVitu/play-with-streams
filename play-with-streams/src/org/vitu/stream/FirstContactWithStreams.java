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
		// arrayOfStrings.stream(); --> ne fonctionne pas, car la méthode stream() ne s'applique pas à ce type d'objets. 
		System.out.println("\nStream sur un tableau");
		Arrays.stream(arrayOfStrings).forEach(System.out::println);
		
		
	
	}
}
