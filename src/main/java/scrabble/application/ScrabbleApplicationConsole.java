package scrabble.application;

import scrabble.controller.Game;
import scrabble.gui.console.Console;
import scrabble.model.Player;
import scrabble.model.board.Board;
import scrabble.utils.EmptyBagException;

public class ScrabbleApplicationConsole {
	public static String SEPARATOR = "-------------------------------------------------------";

	public static void main(String[] args) {
		Console.message(SEPARATOR);
		Console.message("-- Bienvenue dans notre magnifique jeu de scrabble ! --");
		Console.message("-- développé par Hugo                                --");
		Console.message("-- et par Eliott                                     --");
		Console.message("-- et par Lucas                                      --");
		Console.message(SEPARATOR);

		Game game = new Game();

		try {
			game.initialize();
		} catch (EmptyBagException e) {
			Console.message("Le sac est vide, impossible de commencer la partie.");
			return;
		}
		
		Player player = game.getPlayer();
		Board board = game.getBoard();

		Console.message("Voici le plateau du jeu: ");
		board.display();

		Console.message("Voici votre chevalet :");
		player.displayRack();

		Console.message(SEPARATOR);

		boolean tokenMooved = answerSwapToken(game, player);

		if (tokenMooved) {

			Console.message("Voici votre chevalet après l'échange :");
			player.displayRack();
		}
	}

	private static boolean answerSwapToken(Game game, Player player) {
		Integer remainingTokenInRack = player.remainingTokenInRack();
		Integer tokenToSwapIndex = Console.askInt("Quel jeton voulez-vous échanger ?", 1, remainingTokenInRack);
		
		try {
			game.switchTokenFromRack(player, tokenToSwapIndex);
			return true;
		} catch (EmptyBagException e) {

			Console.message("Le sac est vide, impossible d'échanger un jeton.");
			return false;
		} catch (IndexOutOfBoundsException e) {

			Console.message("Le jeton demandé n'existe pas.");
			return false;
		}
	}
}