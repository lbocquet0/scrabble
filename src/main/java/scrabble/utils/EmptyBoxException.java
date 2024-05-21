package scrabble.utils;

public class EmptyBoxException extends Exception {
	public EmptyBoxException() {
		super("The box is empty.");
	}
}
