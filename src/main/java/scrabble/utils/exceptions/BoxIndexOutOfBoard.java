package scrabble.utils.exceptions;

public class BoxIndexOutOfBoard extends Exception {
	public BoxIndexOutOfBoard(Integer row, Integer column) {
		super("Les coordonnées de la case (ligne: " + row + ", colonne: " + column + ") est en dehors du plateau.");
	}
}
