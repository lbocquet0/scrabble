package scrabble.gui.console;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
	public static final String HORIZONTAL_LINE = "---------------------------------------------------------";
	private static final Scanner scanner = new Scanner(System.in);

	public static void message(String message) {
		System.out.println(message);
	}
	public static void makeSeparator() {
		Console.message("\n" + HORIZONTAL_LINE + "\n");
	}

	public static Integer askInt(String question, Integer min, Integer max) {
		Console.message(question);

		Integer response = null;
		while (response == null) {
			try {
				response = scanner.nextInt();
			} catch (NoSuchElementException | NullPointerException e) {
				Console.message("Veuillez saisir un nombre");
				scanner.next();
				continue;
			}
			
            if (response < min || response > max) {
				Console.message("Veuillez saisir un nombre entre " + min + " et " + max);
				response = null;
			}
		}

		return response;
	}

	public static String askString(String question) {
		Console.message(question);
		String response = null;

		while (response == null) {
			try {
				response = scanner.next();
			} catch (NoSuchElementException e) {
				Console.message("Veuillez saisir une chaîne de caractères");
				scanner.next();
			}
		}

		return response;
	}

	public static void welcomeMessage() {
		Console.message(Console.HORIZONTAL_LINE);
		Console.message("-- Bienvenue dans notre magnifique jeu de scrabble ! --");
		Console.message("-- développé par Hugo                                --");
		Console.message("-- et par Eliott                                     --");
		Console.message("-- et par Lucas                                      --");
		Console.message(Console.HORIZONTAL_LINE);
	}
}