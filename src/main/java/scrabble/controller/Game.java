package scrabble.controller;

import scrabble.gui.console.Console;
import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.board.Board;
import scrabble.model.token.Token;
import scrabble.utils.EmptyBagException;

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

	public void start() {
		while (this.player.remainingTokenInRack() < 7) {
			fillUpPlayerRack(this.player);
		}
		
		Console.message("Voici votre chevalet :");
		this.player.displayRack();

		Integer tokenToSwap = Console.askInt("Quel jeton voulez-vous échanger ?", 1, this.player.remainingTokenInRack());
		Token token = this.player.removeTokenFromRack(tokenToSwap - 1);

		Console.message("Vous venez de d'échanger le jeton " + token.display() + " avec le sac");
		Console.message("Voici votre nouveau chevalet :");
		
		this.player.displayRack();
	}
	
	public void fillUpPlayerRack(Player player) {
		if (player.remainingTokenInRack() < 7) {
			Token token;
			try {
				token = this.bag.pickToken();
				player.addTokenToRack(token);
			} catch (EmptyBagException _e) {
			}
		}
	}
}

