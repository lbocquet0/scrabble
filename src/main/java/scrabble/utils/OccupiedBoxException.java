package scrabble.utils;

public class OccupiedBoxException extends Exception {
	public OccupiedBoxException() {
		super("The box is occupied.");
	}
}
