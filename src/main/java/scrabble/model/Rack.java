package scrabble.model;

import java.util.ArrayList;

import scrabble.gui.console.Console;
import scrabble.model.token.Token;
import scrabble.utils.TokenIndexOutOfRack;

public class Rack {

	public static int MAX_TOKENS_AMOUNT = 7;

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
	
	public Token removeToken(int index) throws TokenIndexOutOfRack {
		if (index < 0 || index >= this.tokens.size()) {
			throw new TokenIndexOutOfRack(index);
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
			for(int i=0;i < MAX_TOKENS_AMOUNT; i++) {
				Console.message("   |");
			}
		}else {
			
			String line = "|  ";
			// TODO :  to refactor with a for each loop
			for(int i=0;i < MAX_TOKENS_AMOUNT; i++) {
				Token token = this.getToken(i);
				
				if (token != null) {
					String tokenDisplay = token.display();
					
					if (tokenDisplay.length() == 3) {
						line = line + tokenDisplay + "  |  ";
					} else {
						line = line + tokenDisplay + " |  ";
					}
				} else {
					line = "     |";
				}
			}
			Console.message(line);
		}
		Console.message(HORIZONTAL_LINE);
	}

	public boolean isEmpty() {
		if (this.remainingTokens() == 0) {
			return true;
		}
		return false;
	}

	public Integer getTokenIndex(Token token) {
		for (int i = 0; i < this.tokens.size(); i++) {
			if (this.tokens.get(i).getLetter() == token.getLetter()) {
				return i + 1;
			}
		}

		return -1;
	}
}