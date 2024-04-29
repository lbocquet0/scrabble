package scrabble.model;

import java.util.ArrayList;

public class Easel {
	private ArrayList<Token> tokens;

	public Easel() {
		this.tokens = new ArrayList<Token>();
	}

	public ArrayList<Token> getTokens() {
		return this.tokens;
	}
}