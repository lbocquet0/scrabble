package scrabble.gui.console;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
	public static final String HORIZONTAL_LINE = "---------------------------------------------------------";
	private static final Scanner scanner = new Scanner(System.in);

	public static void message(String message) {
		System.out.println(message);
	}

	public static Integer askInt(String question, Integer min, Integer max) {
		Console.message(question);

		Integer response = null;
		while (response == null) {
			try {
				response = scanner.nextInt();
			} catch (InputMismatchException e) {
				Console.message("Veuillez saisir un nombre");
				scanner.next();
			} catch (NoSuchElementException e) {
				Console.message("Veuillez saisir un nombre");
				scanner.next();
			}

			if (response < min || response > max) {
				Console.message("Veuillez saisir un nombre entre " + min + " et " + max);
				response = null;
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