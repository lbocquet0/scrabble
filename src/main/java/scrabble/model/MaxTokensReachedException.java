package scrabble.model;

public class MaxTokensReachedException extends Throwable {

	public MaxTokensReachedException() {
		super("Max tokens reached");
	}
}
