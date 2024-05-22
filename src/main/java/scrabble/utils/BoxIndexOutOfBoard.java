package scrabble.utils;

public class BoxIndexOutOfBoard extends Exception {
	public BoxIndexOutOfBoard() {
		super("The box index is out of the board.");
	}
}
