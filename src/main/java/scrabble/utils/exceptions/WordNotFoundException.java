package scrabble.utils.exceptions;

public class WordNotFoundException extends Exception {
	public WordNotFoundException() {
		super("The word is not found.");
	}
}