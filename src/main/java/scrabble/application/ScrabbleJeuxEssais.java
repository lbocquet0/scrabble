package scrabble.application;

import scrabble.model.Bag;
import scrabble.model.Board;
import scrabble.model.Console;
import scrabble.model.Easel;
import scrabble.model.Game;
import scrabble.model.Player;

public class ScrabbleJeuxEssais {

	public static void main(String[] args) {
		Game game = new Game();
		Bag bag = game.getBag();
		Player player = game.getPlayer();
		Board board = game.getBoard();
		Easel easel = player.getEasel();

		Console.message("Bag tokens amount (should be equals to 102) : " + bag.countTokens());
		Console.message("Player easel tokens amount (should be equals to 7) : " + easel.getTokensAmount());
		Console.message("Test of display of the board (should be empty) : ");
		board.display();
		Console.message("Test of display of the easel (should display 7 tokens) : ");
		easel.display();
	}

}