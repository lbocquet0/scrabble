package scrabble.utils.exceptions;

public class EmptyBoxException extends Exception {
	public EmptyBoxException() {
		super("The box is empty.");
	}
}
