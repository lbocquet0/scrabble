package scrabble.utils;

public class PlayALetterOutOfBoard extends Exception {
	public PlayALetterOutOfBoard( Integer x, Integer y) {
		super("The letter is out of the board at position (" + x + ", " + y + ")");
	}
}