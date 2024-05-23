package scrabble.utils.exceptions;

public class BoxIndexOutOfBoard extends Exception {
	private Integer row;
	private Integer column;

	public BoxIndexOutOfBoard(Integer row, Integer column) {
		super("The box index is out of the board.");
		this.row = row;
		this.column = column;
	}

	public Integer getRow() {
		return this.row;
	}

	public Integer getColumn() {
		return this.column;
	}
}
