package scrabble.utils.exceptions;

public class TokenDoesntExists extends Exception {
	public TokenDoesntExists() {
		super("Le jeton n'existe pas dans le chevalet.");
	}
}
