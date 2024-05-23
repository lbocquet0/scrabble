package scrabble.utils.exceptions;

public class TokenIndexOutOfRack extends Exception {
	private Integer index;

	public TokenIndexOutOfRack(Integer index) {
		super("The token index is out of the rack.");
		this.index = index;
	}

	public Integer getIndex() {
		return this.index;
	}
}
