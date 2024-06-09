package scrabble.utils.exceptions;

public class CantPlaySingleLetterException extends Exception {
	public CantPlaySingleLetterException() {
		super("Vous ne pouvez pas jouer qu'une seule lettre.");
	}
}