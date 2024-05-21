package scrabble.controller;

import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.board.Board;
import scrabble.model.token.Token;
import scrabble.utils.EmptyBagException;
import scrabble.utils.EmptyBoxException;
import scrabble.utils.OccupiedBoxException;

public class Game {
	
	private Player player;
	private Board board;
	private Bag bag;
	
	public Game() {
		this.bag = new Bag();
		this.player = new Player();
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

	public void initialize() throws EmptyBagException {
		this.fullFillPlayerRack(this.player);
	}
	
	public void fillUpPlayerRack(Player player) throws EmptyBagException {
		if (player.remainingTokenInRack() < 7) {
			Token token = this.bag.pickToken();
			
			player.addTokenToRack(token);
		}
	}

	public void fullFillPlayerRack(Player player) throws EmptyBagException {
		while (player.remainingTokenInRack() < 7) {
			fillUpPlayerRack(player);
		}
	}

	public void switchTokenFromRack(Player player, int tokenIndex) throws EmptyBagException, IndexOutOfBoundsException {
		if (this.bag.remainingTokens() == 0) {
			throw new EmptyBagException();
		}

		Token token = player.removeTokenFromRack(tokenIndex);
		
		fillUpPlayerRack(player);
		this.bag.putToken(token);
	}

	public void playWord(Token[] tokens, int x, int y) throws IndexOutOfBoundsException, OccupiedBoxException, EmptyBoxException {
		for (int i = 0; i < tokens.length; i++) {
			this.playLetter(tokens[i], x+i, y+i);
		}
	}

	public void playLetter(Token token, int x, int y) throws  IndexOutOfBoundsException, OccupiedBoxException, EmptyBoxException {
		if (this.board.getToken(x, y) != null) {
			throw new OccupiedBoxException();
        }
		if (x < 1 || x > Board.SIZE || y < 1 || y > Board.SIZE) {
			throw new IndexOutOfBoundsException();
		}
		this.board.setToken(token, x, y);
	}
}