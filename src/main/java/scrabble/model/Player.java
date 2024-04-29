package scrabble.model;

public class Player {
	private Easel easel;
	private Game game;

	public Player(Game game) {
		this.game = game;
		this.easel = new Easel(this);
	}

	public Easel getEasel() {
		return this.easel;
	}

	public Game getGame() {
		return this.game;
	}
}
