package scrabble.model.board;

import scrabble.model.token.Token;

public class Box {

	private final boolean middle;
	private Token token;
	private Effect effect;

	public Box(boolean middle, Token token) {
		this(middle, token, Effect.NORMAL);
	}
	
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

	public Integer getLetterEffectMultiplicator() {
		if (this.effect == Effect.DOUBLE_LETTER) {
			return 2;
		} else if (this.effect == Effect.TRIPLE_LETTER) {
			return 3;
		}
		
		return 1;
	}

	public Integer getWordEffectMultiplicator() {
		if (this.effect == Effect.DOUBLE_WORD) {
			return 2;
		} else if (this.effect == Effect.TRIPLE_WORD) {
			return 3;
		}
		
		return 1;
	}

	public Effect effect() {
		return this.effect;
	}
}