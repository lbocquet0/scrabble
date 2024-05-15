package scrabble.utils;

public class EmptyBagException extends Exception {
	public EmptyBagException() {
		super("The bag is empty.");
	}
}