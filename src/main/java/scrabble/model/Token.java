package scrabble.model;

public class Token {
	protected Letter letter;

	public Token(Letter letter) {
		this.letter = letter;
	}

	public Letter getLetter() {
		return letter;
	}

	public boolean isJoker() {
		return false;
	}

	public String display() {
		return letter.toString() + " (" + letter.getPoint() + ")";
	}
}
