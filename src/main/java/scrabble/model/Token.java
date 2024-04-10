package scrabble.model;

public class Token {
	protected Letter letter;

	public Token(Letter letter) {
		this.letter = letter;
	}

	public Letter getLetter() {
		return letter;
	}

	public String describe() {
		return letter.toString() + " (" + letter.getPoint() + ")";
	}
}
