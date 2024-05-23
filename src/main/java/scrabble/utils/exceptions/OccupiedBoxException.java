package scrabble.utils.exceptions;

public class OccupiedBoxException extends Exception {
	public OccupiedBoxException() {
		super("The box is occupied.");
	}
}
