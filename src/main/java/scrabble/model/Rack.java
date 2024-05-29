package scrabble.model;

import java.util.ArrayList;

import scrabble.gui.console.Console;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.TokenDoesntExists;

public class Rack {

	public static int MAX_TOKENS_AMOUNT = 7;

	private ArrayList<Token> tokens;

	public Rack() {
		this.tokens = new ArrayList<Token>();
	}

	public ArrayList<Token> tokens() {
		return this.tokens;
	}

	public int remainingTokens() {
		return this.tokens.size();
	}

	public Token token(int index) {
		return this.tokens.get(index);
	}
	
	public boolean removeToken(Token token) throws TokenDoesntExists {
		if (this.tokens.contains(token)) {
			this.tokens.remove(token);
			return true;
		}
		
		throw new TokenDoesntExists();
	}

	public void addToken(Token token) {
		this.tokens.add(token);
	}
	
	public void display() {
		String HORIZONTAL_LINE = "---------------------------------------------------------";
		
		Console.message(HORIZONTAL_LINE);
				
		if(this.isEmpty()) {
			for(int i=0;i < MAX_TOKENS_AMOUNT; i++) {
				Console.message("   |");
			}
		}else {
			
			String line = "|  ";
			// TODO :  to refactor with a for each loop
			for(int i=0;i < MAX_TOKENS_AMOUNT; i++) {
				Token token = this.token(i);
				
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

	public Integer getTokenPosition(Token token) {
		for (int i = 0; i < this.tokens.size(); i++) {
			if (this.tokens.get(i).getLetter() == token.getLetter()) {
				return i + 1;
			}
		}

		return -1;
	}
}