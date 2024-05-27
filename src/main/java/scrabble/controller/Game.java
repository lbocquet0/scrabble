package scrabble.controller;

import java.util.ArrayList;

import scrabble.gui.console.Console;
import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Action;
import scrabble.model.board.Board;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.ScoreCounter;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
import scrabble.utils.exceptions.EmptyBagException;
import scrabble.utils.exceptions.EmptyBoxException;
import scrabble.utils.exceptions.OccupiedBoxException;
import scrabble.utils.exceptions.TokenDoesntExists;

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

	public void playLetter(Token token, int row, int column) throws OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
		Token currentToken = this.board.getToken(row, column);
		if (currentToken != null) {
			if (currentToken.getLetter() == token.getLetter()) {
				return;
			} else {
				throw new OccupiedBoxException();
			}
        }

		this.player.removeTokenFromRack(token);

		try {
			this.board.setToken(token, row, column);
		} catch (Exception e) {
			this.player.addTokenToRack(token);

			throw e;
		}
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

	public Integer validateWord(Direction direction) {
		ArrayList<Action> actions = this.board.getActionsHistory();

		Integer score = ScoreCounter.countScore(this.board, actions, direction);
		Integer newScore = this.player.addScore(score);

		this.clearRoundHistory();

		return newScore;
	}

	public void switchTokenFromRack(Player player, Token token) throws EmptyBagException {
		
        if (this.bag.remainingTokens() == 0) {
            throw new EmptyBagException();
        }

        try {
			player.removeTokenFromRack(token);
		} catch (TokenDoesntExists e) {
			Console.message("Le jeton demandé n'existe pas.");
		}
        
        this.fillUpPlayerRack(player);
        this.bag.putToken(token);
    }
}