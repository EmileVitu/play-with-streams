package org.vitu.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayWithMapFilterReduce {

	public static void main(String[] args) throws IOException {

		List<String> strings = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
		
		// Jusqu'a present nous avons vu l'operation de mapping et l'operation de filtrage
		// --> Le mapping ne change pas le nombre d'elements traites, mais on change le type des elements
		int maxLength =
		strings.stream() // Stream<String>
			.map(s -> s.length()) // Stream<Integer>
			.max(Comparator.naturalOrder()) // L'operation max retourne un optional
			.orElseThrow(); // Puisque c'est un optional, get(), ne fonctionnera pas
		
		System.out.println("Longueur max = " + maxLength); // Retourne 5
		
		// Maintenant que se passe t'il si on reprends le code qui permet de lire le fichier (en reprenant du code de la class FirstContactWithStream
		String fileName = "files/lines.txt";
		Path path = Path.of(fileName);
		
		// Maintenant si on cherche un max entier : 
		int maxLengthLine =
		Files.lines(path)
			.mapToInt(String::length) // En passant mapToInt() cela nous retournera une IntStream() au lieu de map(), ca nous evite de passer un comparateur dans max()
			.max() // Car nous savons comment comparer des entiers de facon naturelle
			.orElseThrow();	
		System.out.println("Longueur max = " + maxLengthLine); // Nous donne la longueur de la ligne la plus longue (20) et non pas la ligne la plus longue
		
		// Et si nous cherchons un max de chaine de caracteres :
		String maxLineByLength =
		Files.lines(path)
			.max(Comparator.comparing(line -> line.length()))
			.orElseThrow(); // le max de string retourne un optional donc nous devons utiliser orElseThrow()
		System.out.println("Ligne de longueur maximale = " + maxLineByLength); // retourne "five six seven eight"
		
		// Pour le moment nous avons 4 lignes, donc 4 elements dans notre Stream<>
		long count = Files.lines(path).count();
		System.out.println("Count = " + count);
		
		// Mais nous voudrions avoir 12 elements dans notre Stream<>, nous pouvons utiliser la technique pattern
		System.out.println("\nStream sur une ligne découpée par un pattern");
		
		Pattern pattern = Pattern.compile("[ ,:!]");
		
		Function<String, Stream<String>> toWords = 
			l -> pattern.splitAsStream(l);
		
		String line = "one two three four";
		Stream<String> stream = toWords.apply(line);
// 			Pattern.compile(" ")
// 				.splitAsStream(line);
		stream.forEach(System.out::println);
		
		// Ici nous avons mis le pattern de split dans une fonction, et l'appliquons sur la ligne que nous voulons
		Stream<Stream<String>> streamOfStream =
		Files.lines(path) // Retourne un Stream<String>
			.map(toWords); // Stream<Stream<String>>
		
		long count2 = streamOfStream.count();
		System.out.println("Count = " + count2); // Nous obtenons toujours 4, car la methode map() ne modifie jamais le nombre d'elements du Stream<> sur lequel elle opere
		
		Files.lines(path)
			.map(toWords)
			.forEach(System.out::println);
		// Retourne :
		// java.util.stream.ReferencePipeline$Head@66d33a
		// java.util.stream.ReferencePipeline$Head@7cf10a6f
		// java.util.stream.ReferencePipeline$Head@7e0babb1
		// java.util.stream.ReferencePipeline$Head@6debcae2 --> donc les 4 elements retournes sont bien des Stream car c'est l'interface Stream qui nous est retournee

		// Maintenant plutot que d'avoir un Stream<> qui contient 4 Stream<> qui contiennent des elements, nous voulons un seul Stream<> avec tous les elements des Stream<Stream<>> remis a plat
		long countwords = Files.lines(path) // Retourne un Stream<String>
			.flatMap(toWords) // Retourne aussi un Stream<String>
			.count();
		System.out.println("Count words : " + countwords);
		// Ainsi flatMap() prends une fonction et cette fonction doit retourner un Stream<>, et elle prends les Stream<> créés par cette fonction et les met a plat dans un Stream<> unique
		// Donc map() ne change pas le nombre d'elements du Stream<>, mais flatMap() si. 		
		
		// Travaillons a présent avec une liste contenant tous les mots du fichier
		List<String> words = 
		Files.lines(path)
			.flatMap(toWords)
			.collect(Collectors.toList());
		System.out.println("\nMots du fichier texte :");
		words.forEach(System.out::println); // Ici nous utilisons la methode forEach() de List<>
		

		String word = "eleven";
		Stream<Character> letters = word.chars().mapToObj(letter -> (char)letter);
		// Ici nous pourrions très bien dire que letters est une fonction, qui prends un string, et retourne un Stream<Character>
		Function<String, Stream<Character>> toLetters = w -> w.chars().mapToObj(letter -> (char)letter);
		
		System.out.println("\nLettres :");
		letters.forEach(System.out::println);
		
		System.out.println("\nLettres en utilisant la méthode chars :");
		toLetters.apply("twelve").forEach(System.out::println);
		
		// Nous pouvons donc faire un flatMap sur cette exemple aussi, pour avoir un seul Stream<Character>
		System.out.println("\nLettres du fichier :");
		Files.lines(path) // Stream<String> : lignes du fichier
			.flatMap(toWords) // Stream<String> : mots du fichier
			.flatMap(toLetters) // Stream<Character> : lettres du fichier
			.distinct() // Pour enlever les doublons
			.sorted() // Pour les trier par ordre alphabétique
			.forEach(System.out::print); // Pour toutes les imprimer sur la meme ligne et non une ligne par lettre pour "println"
		
		// Maintenant nous ajoutons des caracteres speciaux au fichier lines.txt
		// Pour ne pas avoir ces caracteres speciaux dans nos Stream<> il nous suffit d'ajouter ces caracteres speciaux au pattern initial (uniquement les espaces)
		// --> Pattern pattern = Pattern.compile("[ ,:!]");
		// Si a présent on ajoute "émile" au fichier .txt : Nous sommes sense avoir une erreur, car Java est sensé utiliser des fichiers UTF-8
		// On peut modifier l'encryptage du fichier en faisant clic droit > propriétés sur le fichier dans le package explorer
		
	
	}
}
