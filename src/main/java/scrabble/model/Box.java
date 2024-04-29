package scrabble.model;

public class Box {

	private final boolean middle;
	private Token token;
	
	public Box(boolean middle, Token token) {
		this.middle = middle;
		this.token = token;
	}
	
	public String describe() {
		if (token == null) {
			if (this.middle) {
				return "⭐";
			} else {
				return " ";
			}
		}
		
		return token.describe();
	}
}