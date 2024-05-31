package scrabble.utils.exceptions;

public class OccupiedBoxException extends Exception {
	public OccupiedBoxException() {
		super("La case est déjà occupée.");
	}
}
