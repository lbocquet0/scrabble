package scrabble.model;

import scrabble.controller.Game;
import scrabble.model.token.Token;

public class Player {
	private Rack rack;
	private Game game;

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

	public Game getGame() {
		return this.game;
	}

	public void displayRack() {
		this.rack.display();
	}

	public Token removeTokenFromRack(int i) throws IndexOutOfBoundsException {
		return this.rack.removeToken(i-1);
	}
}
