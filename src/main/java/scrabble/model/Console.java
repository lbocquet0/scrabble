package scrabble.model;

import java.util.Scanner;

public class Console {
	public static void message(String message) {
		System.out.println(message);
	}

	public static Integer askInt(String question, Integer min, Integer max) {
		Scanner scanner = new Scanner(System.in);
		Console.message(question);

		Integer response = null;
		while (response == null) {
			response = scanner.nextInt();
			if (response < min || response > max) {
				Console.message("Veuillez saisir un nombre entre " + min + " et " + max);
				response = null;
			}
		}

		scanner.close();

		return response;
	}
}