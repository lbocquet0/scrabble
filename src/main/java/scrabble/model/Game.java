package scrabble.model;

public class Game {
	
	private Player player;
	private Board board;
	private Bag bag;
	
	public Game() {
		Player player = new Player(this);
		Board board = new Board();
		Bag bag = new Bag();
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Bag getBag() {
		return this.bag;
	}
}
