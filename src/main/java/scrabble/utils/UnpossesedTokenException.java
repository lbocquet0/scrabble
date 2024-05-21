package scrabble.utils;

public class UnpossesedTokenException extends Exception {
	public UnpossesedTokenException() {
		super("Le joueur ne possède pas ce jeton");
	}
}