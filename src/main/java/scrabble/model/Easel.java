package scrabble.model;

import java.util.ArrayList;

public class Easel {
	public int DEFAULT_TOKENS_AMOUNT = 7;

	private ArrayList<Token> tokens;
	private Player owner;

	public Easel(Player owner) {
		this.tokens = new ArrayList<Token>();
		this.owner = owner;

		for (int i = 0; i < DEFAULT_TOKENS_AMOUNT; i++) {
			this.pickRandomToken();
		}
	}

	public ArrayList<Token> getTokens() {
		return this.tokens;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void pickRandomToken() {
		Bag bag = this.owner.getGame().getBag();
		Token token = bag.pickToken();

		if (token != null) {
			this.addToken(token);
		}
	}
	
	private void addToken(Token token) {
		this.tokens.add(token);
	}

	private void swapTokens(Token token) {
		this.tokens.remove(token);
	
		Bag bag = this.owner.getGame().getBag();
		bag.putToken(token);

		this.pickRandomToken();
	}
}