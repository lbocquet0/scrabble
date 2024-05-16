package scrabble.model;

import java.util.ArrayList;

import scrabble.gui.console.Console;
import scrabble.model.token.Token;

public class Rack {

	public int DEFAULT_TOKENS_AMOUNT = 7;

	private ArrayList<Token> tokens;
	private Player owner;

	public Rack() {
		this.tokens = new ArrayList<Token>();
	}

	public ArrayList<Token> getTokens() {
		return this.tokens;
	}

	public int remainingTokens() {
		return this.tokens.size();
	}

	public Token getToken(int index) {
		return this.tokens.get(index);
	}
	
	public Token removeToken(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= this.tokens.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.tokens.remove(index);
	}

	public Player getOwner() {
		return this.owner;
	}

	public void addToken(Token token) {
		this.tokens.add(token);
	}
	
	public void display() {
		String HORIZONTAL_LINE = "---------------------------------------------------------";
		
		Console.message(HORIZONTAL_LINE);
				
		if(this.remainingTokens() == 0) {
			for(int i=0;i < DEFAULT_TOKENS_AMOUNT; i++) {
				Console.message("   |");
			}
		}else {
			String retour = "|  ";
			// TODO :  to refactor with a for each loop
			for(int i=0;i < DEFAULT_TOKENS_AMOUNT; i++) {
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