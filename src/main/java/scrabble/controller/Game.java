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

	private static int MAX_PLAYERS = 2;

	private Player currentPlayer;
	private ArrayList<Player> players;
	private Board board;
	private Bag bag;
	private IntegerProperty roundNumber;

	public Game() {
		this.bag = new Bag();
		this.players = new ArrayList<Player>();
		for (int i = 0; i < MAX_PLAYERS; i++) {
			this.players.add(new Player());
		}

		this.currentPlayer = this.players.get(0);
		this.board = new Board();

		this.roundNumber = new SimpleIntegerProperty(1);
	}

	public Board getBoard() {
		return this.board;
	}

	public Bag getBag() {
		return this.bag;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void initialize() throws EmptyBagException {
		for (Player player : this.players) {
			this.fullFillPlayerRack(player);
		}
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	public Boolean bagIsEmpty() {
		return this.bag.remainingTokens() == 0;
	}

	public Boolean rackIsEmpty() {
		return this.currentPlayer.remainingTokenInRack() == 0;
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

	public void playLetter(Token token, int row, int column) throws OccupiedBoxException, PositionOutOfBoard, TokenDoesntExists, EmptyBoxException, IllegalMoveException {
		Token currentToken = this.board.getToken(row, column);
		if (currentToken != null) {
			if (currentToken.getLetter() == token.getLetter()) {
				return;
			} else {
				throw new OccupiedBoxException();
			}
		}

		this.currentPlayer.removeTokenFromRack(token);

		try {
			this.board.placeToken(token, row, column);
		} catch (Exception e) {
			this.currentPlayer.addTokenToRack(token);

			throw e;
		}
	}

	public void cancelLastWord() {
		ArrayList<Token> tokens = this.board.cancelLastWord();

		for (Token token : tokens) {
			this.currentPlayer.addTokenToRack(token);
		}
	}

	public void cancelLastAction() {
		Token token = this.board.cancelLastAction();
		if (token != null) {
			this.currentPlayer.addTokenToRack(token);
		}
	}

	public void clearRoundHistory() {
		this.board.clearHistory();
	}

	public Integer validateWord(Direction direction) throws PositionOutOfBoard, IllegalMoveException {
		if (!this.board.gameIsNotStarted() && !this.board.isMiddleBoxInActionHistory()) {
			Boolean isLetterAround = this.board.isAlreadyPlayedLetterAroundActions();
			if (!isLetterAround) {
				throw new IllegalMoveException();
			}
		}

		Integer score = ScoreCounter.countScore(this.board, direction);
		Integer newScore = this.currentPlayer.addScore(score);

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
		this.fullFillPlayerRack(this.currentPlayer);
		
		this.currentPlayer = this.players.get((this.roundNumber() - 1) % this.players.size());

		return this.roundNumber();
	}

	public IntegerProperty roundNumberProperty() {
		return this.roundNumber;
	}

	public ArrayList<Action> getActions() {
		return this.board.getActions();
	}
}