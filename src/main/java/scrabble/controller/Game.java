package scrabble.controller;

import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
import scrabble.utils.exceptions.EmptyBagException;
import scrabble.utils.exceptions.EmptyBoxException;
import scrabble.utils.exceptions.OccupiedBoxException;
import scrabble.utils.exceptions.TokenIndexOutOfRack;
import scrabble.utils.exceptions.UnpossesedTokenException;

import java.util.ArrayList;

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
		if (player.remainingTokenInRack() < Rack.MAX_TOKENS_AMOUNT) {
			Token token = this.bag.pickToken();
			
			player.addTokenToRack(token);
		}
	}

	public void fullFillPlayerRack(Player player) throws EmptyBagException {
		while (player.remainingTokenInRack() < Rack.MAX_TOKENS_AMOUNT) {
			fillUpPlayerRack(player);
		}
	}

	public void switchTokenFromRack(Player player, int tokenIndex) throws EmptyBagException, TokenIndexOutOfRack {
		if (this.bag.remainingTokens() == 0) {
			throw new EmptyBagException();
		}

		Token token = player.removeTokenFromRack(tokenIndex);
		
		fillUpPlayerRack(player);
		this.bag.putToken(token);
	}

	public void playLetter(Token token, int row, int column) throws OccupiedBoxException, EmptyBoxException, UnpossesedTokenException, BoxIndexOutOfBoard, TokenIndexOutOfRack {
		Token currentToken = this.board.getToken(row, column);
		if (currentToken != null) {
			if (currentToken.getLetter() == token.getLetter()) {
				return;
			} else {
				throw new OccupiedBoxException();
			}
        }

		Integer tokenRackIndex = this.player.getTokenRackIndex(token);
		if (tokenRackIndex == -1) {
			throw new UnpossesedTokenException( token.display() );
		}

		this.board.setToken(token, row, column);
		this.player.removeTokenFromRack(tokenRackIndex);
	}

	public void cancelWord() {
		ArrayList<Token> tokens = this.board.cancelLastAction();

		for (Token token : tokens) {
			this.player.addTokenToRack(token);
		}
	}
	
	public void clearRoundHistory() {
		this.board.clearHistory();
	}
}