package scrabble.utils.exceptions;

public class PlayALetterOutOfBoard extends Exception {
	public PlayALetterOutOfBoard( Integer x, Integer y) {
		super("La lettre en position (colonne: " + x + ", ligne: " + y + ") est en dehors du plateau.");
	}
}