package scrabble.utils.exceptions;

public class TokenDoesntExists extends Exception {
	
	public TokenDoesntExists() {
		super("The token doesn't exists in the Rack");
	}
}
