package scrabble.model.token;

public class Joker extends Token {
	public static int OCCURRENCES_AMOUNT = 2;

	public Joker() {
		super(null);
	}

	@Override
	public boolean isJoker() {
		return true;
	}

	@Override
	public String display() {
		return "  0";
	}

	public void setLetter(FrenchLetter letter) {
		this.letter = letter;
	}
	// TODO: Add a method to check if the joker has a letter
}
