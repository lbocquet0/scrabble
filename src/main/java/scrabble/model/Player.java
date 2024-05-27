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

	public Rack rack() {
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
		return this.rack.isEmpty();
	}

	public Integer getTokenRackPosition(Token token) {
		return this.rack.getTokenPosition(token);
	}

	public Integer score() {
		return this.score;
	}

	public Integer addScore(Integer score) {
		this.score += score;

		return this.score;
	}
}
