package scrabble.utils.exceptions;

public class WordNotFoundException extends Exception {
	public WordNotFoundException() {
		super("Aucun mot n'a été trouvé.");
	}
}