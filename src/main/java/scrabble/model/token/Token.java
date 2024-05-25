package scrabble.model.token;

public class Token {
	protected FrenchLetter letter;

	public Token(FrenchLetter letter) {
		this.letter = letter;
	}

	public FrenchLetter getLetter() {
		return letter;
	}

	public boolean isJoker() {
		return false;
	}
}