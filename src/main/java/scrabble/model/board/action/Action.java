package scrabble.model.board.action;

import scrabble.model.board.Box;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.EmptyBoxException;

public class Action {
	private int row;
	private int column;
	private Box box;

	public Action(int rowPosition, int columnPosition, Box box) throws EmptyBoxException {
		if (box.isEmpty()) {
			throw new EmptyBoxException();
		}

		this.row = rowPosition;
		this.column = columnPosition;
		this.box = box;
	}

	public int row() {
		return row;
	}

	public int column() {
		return column;
	}

	public Box getBox() {
		return box;
	}

	public Token getToken() {
		return box.token();
	}

	public Token undo() {
		Token token = box.token();
		box.setToken(null);
	
		return token;
	}
}
