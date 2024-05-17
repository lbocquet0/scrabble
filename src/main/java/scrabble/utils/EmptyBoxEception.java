package scrabble.utils;

public class EmptyBoxEception extends Exception {
	public EmptyBoxEception() {
		super("The box is empty.");
	}
}
