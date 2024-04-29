package scrabble.model;

public class Player {
	private Easel easel;
	private Game game;

	public Player(Game game) {
		this.easel = new Easel(this);
		this.game = game;
	}

	public Easel getEasel() {
		return this.easel;
	}

	public Game getGame() {
		return this.game;
	}
}
