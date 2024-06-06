package scrabble.controller;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.board.action.Action;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.ScoreCounter;
import scrabble.utils.exceptions.*;

public class Game {
	
	private Player player;
	private Board board;
	private Bag bag;
	private IntegerProperty roundNumber;
	
	public Game() {
		this.bag = new Bag();
		this.player = new Player();
		this.board = new Board();

		this.roundNumber = new SimpleIntegerProperty(1);
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

	public void cancelLastWord() {
		ArrayList<Token> tokens = this.board.cancelLastWord();

		for (Token token : tokens) {
			this.player.addTokenToRack(token);
		}
	}

	public void cancelLastAction() {
		Token token = this.board.cancelLastAction();
		if (token != null) {
			this.player.addTokenToRack(token);
		}
	}

	public void clearRoundHistory() {
		this.board.clearHistory();
	}

	public Integer validateWord(Direction direction) throws BoxIndexOutOfBoard, IllegalMoveException {
		ArrayList<Action> actions = this.board.getActionsHistory();

		Boolean isLetterAround = false;
		Integer i = 0;
		
		Action firstAction = actions.get(0);
		
		if (firstAction.getRowPosition() == 8 && firstAction.getColumnPosition() == 8) {
			isLetterAround = true;
		}
		
		
		while (!isLetterAround && i < actions.size()) {
			Action action = actions.get(i);
			isLetterAround = board.isLetterAround(action.getRowPosition(), action.getColumnPosition(), actions);
			i++;
		}

		if (!isLetterAround) {
			throw new IllegalMoveException();
		}

		Integer score = ScoreCounter.countScore(this.board, actions, direction);
		Integer newScore = this.player.addScore(score);

		this.clearRoundHistory();

		return newScore;
	}

	public void switchTokenFromRack(Player player, Token token) throws EmptyBagException, TokenDoesntExists {
		
        if (this.bag.remainingTokens() == 0) {
            throw new EmptyBagException();
        }

		player.removeTokenFromRack(token);
        
        this.fillUpPlayerRack(player);
        this.bag.putToken(token);
    }

	public Integer roundNumber() {
		return this.roundNumber.get();
	}

	public Integer nextRound() throws EmptyBagException {
		this.roundNumber.set(this.roundNumber() + 1);
		this.fullFillPlayerRack(this.player);

		return this.roundNumber();
	}

	public IntegerProperty roundNumberProperty() {
		return this.roundNumber;
	}
}