package scrabble.utils.exceptions;

public class PositionOutOfBoard extends Exception {
	public PositionOutOfBoard(Integer row, Integer column) {
		super("Les coordonnées de la case (ligne: " + row + ", colonne: " + column + ") sont en dehors du plateau.");
	}
}
