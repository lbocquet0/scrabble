package scrabble.controller;

import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.board.Board;

public class Game {
	
	private Player player;
	private Board board;
	private Bag bag;
	
	public Game() {
		this.bag = new Bag();
		this.player = new Player(this);
		this.board = new Board();
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Bag getBag() {
		return this.bag;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void start() {
		this.player.play();
	}	
}
