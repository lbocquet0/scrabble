package scrabble.utils.exceptions;

public class UnpossesedTokenException extends Exception {
	public UnpossesedTokenException(String letter) {
		super("Vous ne possédez pas le jeton " + letter + ".");
	}
}