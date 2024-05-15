package scrabble.model;

import java.util.ArrayList;

import scrabble.utils.EmptyBagException;

public class Rack {

	public int DEFAULT_TOKENS_AMOUNT = 7;

	private ArrayList<Token> tokens;
	private Player owner;

	public Rack(Player owner) {
		this.tokens = new ArrayList<Token>();
		this.owner = owner;

		for (int i = 0; i < DEFAULT_TOKENS_AMOUNT; i++) {
			this.pickRandomToken();
		}
	}

	public ArrayList<Token> getTokens() {
		return this.tokens;
	}

	public int getTokensAmount() {
		return this.tokens.size();
	}

	public Token getToken(int index) {
		return this.tokens.get(index);
	}

	public Player getOwner() {
		return this.owner;
	}

	private void pickRandomToken() {
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
		String HORIZONTAL_LINE = "---------------------------------------------------------";
		
		Console.message(HORIZONTAL_LINE);
				
		if(this.getTokensAmount() == 0) {
			for(int i=0;i < 7; i++) {
				Console.message("   |");
			}
		}else {
			String retour = "|  ";
			for(int i=0;i < 7; i++) {
				if (this.getToken(i) != null) {
					if (this.getToken(i).display().length() == 3) {
						retour = retour + this.getToken(i).display() + "  |  ";
					} else {
						retour = retour + this.getToken(i).display() + " |  ";
					}
				} else {
					retour = "     |";
				}
			}
			Console.message(retour);
		}
		Console.message(HORIZONTAL_LINE);
	}
}