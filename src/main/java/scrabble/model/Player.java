package scrabble.model;

import scrabble.model.token.Token;
import scrabble.utils.exceptions.TokenDoesntExists;

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

	public Boolean removeTokenFromRack(Token token) throws TokenDoesntExists {
		return this.rack.removeToken(token);
	}
	
	public boolean RackIsEmpty(){
		if (this.getRack().remainingTokens() == 0) {
			return true;
		}
		return false;
	}

	public Integer getTokenRackIndex(Token token) {
		return this.rack.getTokenIndex(token);
	}

}
