package scrabble.application;

import scrabble.controller.Game;
import scrabble.gui.console.Console;
import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.board.Board;
import scrabble.utils.EmptyBagException;

public class ScrabbleApplicationConsole {
	public static void main(String[] args) {
		Console.welcomeMessage();

		Game game = new Game();

		try {
			game.initialize();
		} catch (EmptyBagException e) {
			Console.message("Le sac est vide, impossible de commencer la partie.");
			return;
		}
		
		Player player = game.getPlayer();
		Board board = game.getBoard();
		Bag bag = game.getBag();

		Boolean continueGame = true;
		
		while (continueGame) {
			
			if (player.getRack().isEmpty() && bag.isEmpty()) {
				continueGame = false;
				break;
			}
			
			Console.message("Plateau de jeu :");
			board.display();

			Console.makeSeparator();

			Console.message("Votre chevalet :");
			player.displayRack();

			Console.makeSeparator();
			
			Console.message("Que voulez-vous faire ?");
			Console.message("1 - Jouer un mot");
			Console.message("2 - Échanger un jeton");
			Console.message("3 - Quitter la partie");

			Integer choice = Console.askInt("Votre choix ?", 1, 3);
			Console.message(choice.toString());

			switch (choice) {
				case 1:
					continueGame = true;
					// TODO : Implement playWord
					break;
				case 2:
					continueGame = true;
					// TODO : Implement swapToken
					break;
				case 3:
					continueGame = false;
					break;
			}

			Console.makeSeparator();

			try {
				game.fullFillPlayerRack(player);
			} catch (EmptyBagException e) {
				Console.message("Le sac est vide, vous ne pouvez plus piocher de jeton.");
			}
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