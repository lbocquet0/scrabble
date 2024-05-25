package scrabble.model;

import scrabble.model.token.Token;
import scrabble.utils.exceptions.TokenDoesntExists;

public class Player {
	private Rack rack;
	private Integer score;

	public Player() {
		this.rack = new Rack();
		this.score = 0;
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
	
	public boolean rackIsEmpty(){
		if (this.rack.remainingTokens() == 0) {
			return true;
		}
		return false;
	}

	public Integer getTokenRackIndex(Token token) {
		return this.rack.getTokenIndex(token);
	}

	// TODO: JUnit test
	public Integer getScore() {
		return this.score;
	}

	// TODO: JUnit test
	public Integer addScore(Integer score) {
		this.score += score;

		return this.score;
	}
}
