package scrabble.model;

public class Joker extends Token {
	public Joker() {
		super(null);
	}

	@Override
	public boolean isJoker() {
		return true;
	}

	@Override
	public String display() {
		return " ";
	}

	// TODO: Add a method to set the letter of the joker
	// TODO: Add a method to check if the joker has a letter
}
