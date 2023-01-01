package org.vitu.stream;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountingStreamElement {
	
	public static void main(String [] args) {
		
		// Quel element apparait le plus souvent dans cette liste
		List<String> strings = 
				List.of("one", "five", "one", "two", "six", "seven", "eight", "two", "three", "four",
						"five", "one", "eight", "nine", "ten", "two", "three", "four", "six", "seven", 
						"eight", "two", "three", "four", "five", "one", "eight", "nine", "ten", "two", 
						"one", "eight", "nine", "ten", "eight", "nine", "ten", "nine", "ten", "two");
		
		Function<String, String> classifier = word -> word;
		// Map<String, Long> map = 
		// Map<String, List<String>> map = 
		Map<String, Long> map =		
			strings.stream()
				.collect(Collectors.groupingBy(classifier, Collectors.counting()));
		System.out.println("Map = ");
		map.forEach((key, value) -> System.out.println(key + " -> " + value));
		
		Map.Entry<String, Long> maxEntry =  map.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();
		
		System.out.println("\nMax entry by value = ");
		System.out.println(maxEntry.getKey() + " -> " + maxEntry.getValue());
		// Ceci fonctionne tant que nous n'avons pas d'ex equo en max, que se passe t-il si nous avons deux max()
		// Nous allons renverser la table de hashage en mettant le nombre d'occurences en clef et une liste de string associes
		// 4 -> [one, two]
		// Plutôt que chaque string (sans doublon) en clef et leurs nombre d'occurences en valeurs
		// one -> 4
		// two -> 4
		// Map<Long, List<Map.Entry<String, Long>>> map2 = 
		Map<Long, List<String>> map2 = map.entrySet().stream()						// Map.Entry<String, Long>
			.collect(Collectors.groupingBy(entry -> entry.getValue(),
					Collectors.mapping(entry -> entry.getKey(), Collectors.toList())));
		
		Map.Entry<Long, List<String>> maxEntry2 = map2.entrySet().stream().max(Map.Entry.comparingByKey()).orElseThrow();
		
		System.out.println("\nMax entry by key = ");
		System.out.println(maxEntry2.getKey() + " -> " + maxEntry2.getValue());
		// Retourne 6 -> [two=6, eight=6], ce qui n'est pas très beau, donc nous allons passer un downstream collector
	}
}
