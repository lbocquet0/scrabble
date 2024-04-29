package scrabble.model;

public class Player {
	private Easel easel;

	public Player() {
		this.easel = new Easel(this);
	}

	public Easel getEasel() {
		return this.easel;
	}
}
