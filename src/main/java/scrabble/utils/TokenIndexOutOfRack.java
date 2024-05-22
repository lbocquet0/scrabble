package scrabble.utils;

public class TokenIndexOutOfRack extends Exception {
	public TokenIndexOutOfRack() {
		super("The token index is out of the rack.");
	}
}
