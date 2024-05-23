package scrabble.model;

import scrabble.model.token.Token;
import scrabble.utils.TokenIndexOutOfRack;

public class Player {
	private Rack rack;

	public Player() {
		this.rack = new Rack();
	}

	public Rack getRack() {
		return this.rack;
	}
	
	public Integer remainingTokenInRack() {
		return this.rack.remainingTokens();
	}
	
	public void addTokenToRack(Token token) {
		this.rack.addToken(token);
	}

	public void displayRack() {
		this.rack.display();
	}

	public Token removeTokenFromRack(int i) throws TokenIndexOutOfRack {
		return this.rack.removeToken(i-1);
	}
	
	public boolean rackIsEmpty(){
		if (this.rack.remainingTokens() == 0) {
			return true;
		}
		return false;
	}

	public Integer getTokenRackIndex(Token token) {
		return this.rack.getTokenIndex(token);
	}

}
