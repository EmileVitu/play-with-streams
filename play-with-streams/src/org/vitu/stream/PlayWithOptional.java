package org.vitu.stream;

import java.util.Optional;

public class PlayWithOptional {
	
	public static void main(String[] args) {
		// Quelle est la difference entre int10 et integer10 ?
		// int10 est un type primitif sur lequel nous n'avons pas de methodes
		// integer10 n'est pas un type primitif, Integer est une classe "wrapper", car c'est une classe qui enveloppe une valeur, ici 10, et qui permet de rajouter des methodes sur cette valeur
		// Donc l'objet de la classe Integer est d'avoir acces a des methodes
		Integer integer10 = Integer.valueOf(10);
		int int10 = 10;
		
		
		// Un optional est une classe wrapper, et ici elle enveloppe la valeur "one"
		// La difference avec la classe Integer, est qu'avec optional, nous pouvons ne pas avoir de valeur enveloppee alors qu'Integer doit systematiquement envelopper une valeur
		Optional<String> opt = Optional.of("one");
		Optional<String> emptyOpt = Optional.empty();
	
		boolean empty = opt.isEmpty();
		System.out.println("Optional vide : " + empty);
		System.out.println("Optional vide : " + emptyOpt.isEmpty());
	
		String string = opt.get();
		System.out.println("Contenu de opt = " + string);
		
		// String string2 = emptyOpt.get(); Ceci va jeter une erreur car il ne peut pas nous retourner l'ensemble vide, il faut donc utiliser orElseThrow()
		// Ci-après jette quand même une erreur mais nous permet de nous rappeler de prendre en consideration que l'optional peut etre vide
		// String string2 = emptyOpt.orElseThrow();
		// System.out.println("Contenu de emptyOpt : " + string2);
	
		// A savoir, la classe optional ne peut pas contenir "null" !
		// En effet, le code ci-dessous va jeter une nullPointerException, alors que le code precedent c'est une autre erreur
		// Optional<String> nullOpt = Optional.of(null);
		// System.out.println("Contenu de nullOpt = " + nullOpt);
		
		// Enfin, nous pouvons tester avec la methode ofNullable() si un optional est null, si il est null, il retourne un optional vide
		// Donc si nous sommes dans du code et nous ne savons pas si l'objet que nous passons en parametre est null ou pas, nous pouvons appeler ofNullable()
		Optional<String> nullOpt = Optional.ofNullable(null);
		boolean empty2 = nullOpt.isEmpty();
		System.out.println("nullOpt is empty = " + empty2);
		

		
	}
}
