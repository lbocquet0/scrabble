package scrabble.model;

import java.util.ArrayList;

import scrabble.util.EmptyBagException;

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

		try {
			Token token = bag.pickToken();
			this.addToken(token);

		} catch (EmptyBagException _e) {
			
		}
	}
	
	private void addToken(Token token) {
		this.tokens.add(token);
	}

	public void swapTokens(Token token) {
		this.tokens.remove(token);
	
		Bag bag = this.owner.getGame().getBag();
		bag.putToken(token);

		this.pickRandomToken();
	}
	
	public void display() {
		Integer i = 0;
		for(Token token : tokens) { 
			i++;
			System.out.println("┏     ┓" + "\n" + "┃ " + token.display() + " ┃" + "\n" + "┗     ┛");
		}
		for(Integer rest = MAX_TOKENS - i;rest > 0; rest--) {
			System.out.println ("┏     ┓" + "\n" + "┃     ┃" + "\n" + "┗     ┛");
		}
	}
}