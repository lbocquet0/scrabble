package scrabble.utils.exceptions;

public class EmptyBoxException extends Exception {
	public EmptyBoxException() {
		super("La case est vide.");
	}
}
