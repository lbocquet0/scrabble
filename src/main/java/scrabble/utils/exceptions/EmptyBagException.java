package scrabble.utils.exceptions;

public class EmptyBagException extends Exception {
	public EmptyBagException() {
		super("Le sac est vide.");
	}
}