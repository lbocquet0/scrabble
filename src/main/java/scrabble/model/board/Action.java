package scrabble.model.board;

import scrabble.model.token.Token;
import scrabble.utils.EmptyBoxEception;

public class Action {
	private int rowPosition;
	private int columnPosition;
	private Box box;

	public Action(int rowPosition, int columnPosition, Box box) throws EmptyBoxEception {
		Token token = box.getToken();
		if (token == null) {
			throw new EmptyBoxEception();
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (rowPosition != other.rowPosition)
			return false;
		if (columnPosition != other.columnPosition)
			return false;
		if (box == null) {
			if (other.box != null)
				return false;
		} else if (!box.equals(other.box))
			return false;
		return true;
	}
}