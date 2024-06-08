package scrabble.model.board;

import scrabble.model.token.Token;

public class Box {

	private final boolean middle;
	private Token token;
	private Effect effect;
	
	public Box(boolean middle, Token token, Effect effect) {
		this.middle = middle;
		this.token = token;
		this.effect = effect;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
	
	public Token token() {
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

	public Effect effect() {
		return this.effect;
	}
}