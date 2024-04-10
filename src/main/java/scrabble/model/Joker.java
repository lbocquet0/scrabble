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
	public String describe() {
		return "⭐";
	}
}
