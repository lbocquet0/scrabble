package scrabble.model.board.action;

import java.util.ArrayList;

import scrabble.model.board.Box;
import scrabble.model.token.Token;
import scrabble.utils.Direction;

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
	
	public boolean isEmpty() {
		return this.actions.isEmpty();
	}

	public ArrayList<Action> actions() {
		return this.actions;
	}

	public Token undoAction(Action action) {
		Token token = action.undo();
		this.actions.remove(action);

		return token;
	}

	public Token undoLastAction() {
		if (this.actions.isEmpty()) {
			return null;
		}

		Action lastAction = this.actions.get(this.actions.size() - 1);
		Token token = lastAction.undo();
		this.actions.remove(lastAction);

		return token;
	}

	public ArrayList<Token> undoAllActions() {
		
		ArrayList<Token> undidTokens = new ArrayList<Token>();
		
		for (Action action : this.actions) {
			Token token = action.undo();

			if (token != null) {
				undidTokens.add(token);
			}
		}
		this.actions.clear();

		return undidTokens;
	}

	public Action getFirstAction() {
		if (this.actions.isEmpty()) {
			return null;
		}

		return this.actions.get(0);
	}

	public Action getAction(int index) {
		if (index < 0 || index >= this.actions.size()) {
			return null;
		}

		return this.actions.get(index);
	}

	public boolean positionIsInActions(int row, int column) {
		for (Action action : this.actions) {
			if (action.row() == row && action.column() == column) {
				return true;
			}
		}

		return false;
	}

	public boolean boxIsInActions(Box box) {
		for (Action action : this.actions) {
			if (action.box().equals(box)) {
				return true;
			}
		}

		return false;
	}

	public Direction getDirectionByActions() {
		if (this.actions.isEmpty()) {
			return null;
		}

		if (this.actions.size() == 1) {
			return Direction.HORIZONTAL;
		}

		Action firstAction = this.actions.get(0);
		Action secondAction = this.actions.get(1);

		if (firstAction.row() == secondAction.row()) {
			return Direction.HORIZONTAL;
		}

		return Direction.VERTICAL;
	
	}

	public boolean isAllActionsInSameDirection() {
		if (this.actions.size() < 2) {
			return true;
		}

		Direction direction = this.getDirectionByActions();

		Action firstAction = this.actions.get(0);
		for (int i = 1; i < this.actions.size(); i++) {
			Action action = this.actions.get(i);

			if (direction == Direction.HORIZONTAL && firstAction.row() != action.row()) {
				return false;
			}

			if (direction == Direction.VERTICAL && firstAction.column() != action.column()) {
				return false;
			}
		}

		return true;
	}
}