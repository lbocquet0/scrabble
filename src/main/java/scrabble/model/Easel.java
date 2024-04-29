package scrabble.model;

import java.util.ArrayList;

public class Easel {
	private static Integer MAX_TOKENS = 7;

	private ArrayList<Token> tokens;
	private Player owner;

	public Easel(Player owner) {
		this.tokens = new ArrayList<Token>();
		this.owner = owner;

		for (int i = 0; i < MAX_TOKENS; i++) {
			try {
				this.pickRandomToken();
			} catch (MaxTokensReachedException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Token> getTokens() {
		return this.tokens;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void pickRandomToken() throws MaxTokensReachedException {
		if (this.tokens.size() >= MAX_TOKENS) {
			throw new MaxTokensReachedException();
		}

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

		try {
			this.pickRandomToken();
		} catch (MaxTokensReachedException e) {
			e.printStackTrace();
		}
	}
}