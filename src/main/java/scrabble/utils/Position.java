package scrabble.utils;

public class Position {
	public Integer row;
	public Integer column;

	public Position(Integer row, Integer column) {
		this.row = row;
		this.column = column;
	}

	public Integer row() {
		return this.row;
	}

	public Integer column() {
		return this.column;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		Position position = (Position) obj;

		return this.row == position.row && this.column == position.column;
	}
}