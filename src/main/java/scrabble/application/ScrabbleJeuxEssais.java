package scrabble.application;

import scrabble.controller.Game;
import scrabble.gui.console.Console;
import scrabble.model.Bag;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.Player;

public class ScrabbleJeuxEssais {

	public static void main(String[] args) {
		Game game = new Game();
		Bag bag = game.getBag();
		Player player = game.getPlayer();
		Board board = game.getBoard();
		Rack rack = player.getRack();

		Console.message("Bag tokens amount (should be equals to 102) : " + bag.remainingTokens());
		Console.message("Player easel tokens amount (should be equals to 7) : " + rack.remainingTokens());
		Console.message("Test of display of the board (should be empty) : ");
		board.display();
		Console.message("Test of display of the easel (should display 7 tokens) : ");
		rack.display();
	}

}
