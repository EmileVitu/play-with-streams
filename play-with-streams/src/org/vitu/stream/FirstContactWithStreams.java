package org.vitu.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirstContactWithStreams {
	
	public static void main(String... args) throws IOException {
		
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
		// \n pour faire un retour chariot
		Stream.of("one", "two", "three", "four").forEach(System.out::println);
		
		// Stream sur les lignes d'un fichier (repertoire files cree a la racine du projet et non dans le repertoire source)
		// Path path = Path.of("c:/tmp/debug.log"); est une maniere d'importer en chemin absolu
		String fileName = "files/lines.txt";
		Path path = Path.of(fileName);
		// Nous testons si le chemin existe
		boolean exists = Files.exists(path);
		System.out.println("\nLe fichier " + fileName + " existe : " + exists);
		Stream<String> lines = Files.lines(path); // Classe factory Files (privee), erreur de compilation a l'ecriture, resolue en ajoutant "throws IOException lors de la déclaration main
		System.out.println("\nStream sur les lignes d'un fichier");
		lines.forEach(System.out::println);
		
		// Stream sur un pattern
		String line = "one two three four";
		// Pattern 1
		String[] split = line.split(" ");
		System.out.println("\nStream sur une ligne découpée par split");
		Arrays.stream(split).forEach(System.out::println);
		// Pattern 2
		Pattern pattern = Pattern.compile(" "); // permet de creer un pattern d'expression reguliere
		// String[] split2 = pattern.split(line); equivalent a la meme chose que precedemment
		System.out.println("\nStream sur une ligne découpée par un pattern");
		pattern.splitAsStream(line).forEach(System.out::println);
		// La difference est que dans le premier cas, Pattern 1, un tableau est cree en decoupant la chaine de caracteres, qui seront donc stockees en memoire
		// Le Pattern 2 lui, va traiter les elements un par un, et donc ne pas les stocker en memoire
		
		// Maintenant nous allons utiliser le tableau pour recuperer le premier string de longueur 3
		// Cette methode va nous retourner un optional, donc pour le voir, au lieu d'utiliser get(), nous utilisons la methode orElseThrow()
		String s1 = Arrays.stream(split).filter(s -> s.length() == 3).findFirst().orElseThrow();
		System.out.println("\nPremière chaîne de longueur 3 : " + s1);
		
		
		// A present nous utilisons le pattern pour retourner le premier string de longueur 3
		// Le code est un peu plus long, mais au lieu de tout decouper avant de filtrer les elements, il va d'abord faire le premier element puis le tester et ainsi de suite
		String s2 = Pattern.compile(" ").splitAsStream(line).filter(s -> s.length() == 3).findFirst().orElseThrow();
		System.out.println("\nPremière chaîne de longueur 3 : " + s2);
		// Mais dans ce cas là pourquoi retourne t'on un optional ?
		// Parce que findFirst(), si il est utilisé sur un Stream<> vide, nous ne saurons pas quoi retourner
		
		// Stream<> vide --> Que se passe t'il si nous demandons le premier élément sur un Stream<> qui est vide ? Ca n'a pas de sens, donc cela nous retourne un optional
		Stream<String> emptyStream = Stream.empty();
		Optional<String> first = emptyStream.findFirst();
		boolean empty = first.isEmpty();
		System.out.println("\nStream vide find first = " + empty);
		// L'Optional est donc le choix qui a ete fait pour toutes les operations dont l'issue est incertaine, donc si elles retournent des Stream<> vides ou non
		// Ici c'est evident mais parfois on ne sait pas si le Stream<> retourne sera vide, par exemple
		Stream<String> emptyStream2 = strings.stream().filter(s -> s.length() > 50);
		Optional<String> first2 = emptyStream2.findFirst();
		boolean empty2 = first2.isEmpty();
		System.out.println("\nStream vide find first2 = " + empty2);
		
		//Regroupement par longueur
		Map<Integer, List<String>> map = strings.stream().collect(Collectors.groupingBy(s -> s.length()));
		map.forEach((key, value) -> System.out.println(key + " -> " + value));
	}
}
