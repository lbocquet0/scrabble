package scrabble.utils.exceptions;

public class TokenIndexOutOfRack extends Exception {
	public TokenIndexOutOfRack(Integer index) {
		super("L'indice " + index + " est en dehors du chevalet.");
	}
}
