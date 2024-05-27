package scrabble.model.board;

import scrabble.model.token.Token;

public class Box {

	private final boolean middle;
	private Token token;
	
	public Box(boolean middle, Token token) {
		this.middle = middle;
		this.token = token;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
	
	public Token getToken() {
		return this.token;
	}

	public String describe() {
		if (this.isEmpty()) {
			if (this.middle) {
				return "⭐";
			} else {
				return " ";
			}
		}
		
		return token.display();
	}
	
	public boolean isMiddle() {
		return this.middle;
	}

	public boolean isEmpty() {
		return this.token == null;
	}

	public Integer getScore() {
		if (this.isEmpty()) {
			return 0;
		}
		
		return this.token.getScore();
	}
}