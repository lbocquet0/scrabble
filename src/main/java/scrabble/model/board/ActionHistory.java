package scrabble.model.board;

import java.lang.reflect.Array;
import java.util.ArrayList;

import scrabble.model.token.Token;

public class ActionHistory {
	private ArrayList<Action> actions;

	public ActionHistory() {
		this.actions = new ArrayList<Action>();
	}

	public void add(Action action) {
		this.actions.add(action);
	}

	public int amount() {
		return this.actions.size();
	}
	
	public void clear() {
		this.actions.clear();
	}

	public ArrayList<Action> actions() {
		return this.actions;
	}

	public Token undoAction(Action action) {
		Box box = action.getBox();
		Token token = action.getToken();
		
		box.setToken(null);
		
		this.actions.remove(action);

		return token;
	}

	public ArrayList<Token> undoAllActions() {
		
		ArrayList<Token> undidTokens = new ArrayList<Token>();
		
		for (Action action : this.actions) {
			Token token = this.undoAction(action);

			if (token != null) {
				undidTokens.add(token);
			}
		}
		this.actions.clear();

		return undidTokens;
	}
}