package scrabble.model.board.action;

import scrabble.model.board.Box;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.EmptyBoxException;

public class Action {
	private int rowPosition;
	private int columnPosition;
	private Box box;

	public Action(int rowPosition, int columnPosition, Box box) throws EmptyBoxException {
		if (box.isEmpty()) {
			throw new EmptyBoxException();
		}

		this.rowPosition = rowPosition;
		this.columnPosition = columnPosition;
		this.box = box;
	}

	public int getRowPosition() {
		return rowPosition;
	}

	public int getColumnPosition() {
		return columnPosition;
	}

	public Box getBox() {
		return box;
	}

	public Token getToken() {
		return box.getToken();
	}

	public Token undo() {
		Token token = box.getToken();
		box.setToken(null);
	
		return token;
	}
}
