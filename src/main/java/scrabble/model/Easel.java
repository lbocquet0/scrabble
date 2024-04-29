package scrabble.model;

import java.util.ArrayList;

public class Easel {
	private ArrayList<Token> tokens;
	private Player owner;

	public Easel(Player owner) {
		this.tokens = new ArrayList<Token>();
		this.owner = owner;
	}

	public ArrayList<Token> getTokens() {
		return this.tokens;
	}

	public Player getOwner() {
		return this.owner;
	}
}