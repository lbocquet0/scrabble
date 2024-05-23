package scrabble.utils;

public class UnpossesedTokenException extends Exception {
	private String letter;

	public UnpossesedTokenException(String letter) {
		super("Le joueur ne possède pas ce jeton");
		this.letter = letter;
	}

	public String getLetter() {
		return this.letter;
	}
}