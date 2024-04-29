package scrabble.model;

public class Player {
	private int score;
	private Easel easel;

	public Player() {
		this.score = 0;
		this.easel = new Easel();
	}

	public int getScore() {
		return this.score;
	}

	public Easel getEasel() {
		return this.easel;
	}
}
