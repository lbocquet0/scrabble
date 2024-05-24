package scrabble.application;

import java.util.ArrayList;

import scrabble.controller.Game;
import scrabble.gui.console.Console;
import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
import scrabble.utils.exceptions.EmptyBagException;
import scrabble.utils.exceptions.EmptyBoxException;
import scrabble.utils.exceptions.OccupiedBoxException;
import scrabble.utils.exceptions.TokenDoesntExists;

public class ScrabbleApplicationConsole {

	private static String TEXT_INPUT_FOR_JOKER = "JOK";

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
		Rack rack = player.getRack();

		Boolean continueGame = true;
		
		while (continueGame) {
			
			if (player.rackIsEmpty() && bag.isEmpty()) {
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
					Console.message("Jouer un mot");
					Console.message("Choissisez votre position de départ :");

					Integer x = Console.askInt("Ligne ?", 1, Board.SIZE);
					Integer y = Console.askInt("Colonne ?", 1, Board.SIZE);

					Token token = askToken(rack);
					
					Boolean continueWord = true;
					Direction direction = Direction.HORIZONTAL;
					Integer row = x;
					Integer column = y;

					try {
						game.playLetter(token, x, y);
					} catch (OccupiedBoxException e) {
						Console.message("La case est déjà occupée.");
						continueWord = false;
						game.cancelWord();
					} catch (EmptyBoxException e) {
						Console.message("La case n'a pas été correctement remplie.");
						continueGame = false;
						game.cancelWord();
					} catch (BoxIndexOutOfBoard e) {
						Console.message("Les coordonnées (" + e.getRow() + "," + e.getColumn() + ") que vous avez renseignées sont en dehors du plateau.");
						continueGame = false;
						game.cancelWord();
					} catch (TokenDoesntExists e) {
						Console.message("Vous ne possédez pas ce jeton dans votre rack");
						continueGame = false;
						game.cancelWord();
					}

					Console.message("Avez-vous d'autres lettres à jouer ?");
					Console.message("1 - Oui");
					Console.message("2 - Non");

					Integer response = Console.askInt("Votre choix ?", 1, 2);
					if (response == 1) {
						Console.message("Dans quelle direction voulez-vous jouer votre mot ?");
						Console.message("1 - Horizontal");
						Console.message("2 - Vertical");

						Integer directionChoice = Console.askInt("Votre choix ?", 1, 2);
						if (directionChoice == 2) {
							direction = Direction.VERTICAL;
						}
					} else {
						continueWord = false;
					}

					response = 1;
					while (continueWord) {
						if (response == 1) {
							if (direction == Direction.HORIZONTAL) {
								column += 1;
							} else {
								row += 1;
							}

							token = askToken(rack);

							try {
								game.playLetter(token, row, column);
							} catch (OccupiedBoxException e) {
								Console.message("La case est déjà occupée.");
								game.cancelWord();
							} catch (EmptyBoxException e) {
								Console.message("La case n'a pas correctement été remplie.");
								game.cancelWord();
							} catch (BoxIndexOutOfBoard e) {
								Console.message("Les coordonnées (" + e.getRow() + "," + e.getColumn() + ") que vous avez renseigner sont en dehors du plateau.");
							} catch (TokenDoesntExists e) {
								Console.message("Le jeton n'existe pas");
							}

							Console.message("Avez-vous d'autres lettres à jouer ?");
							Console.message("1 - Oui | Continuer le mot");
							Console.message("2 - Non | Valider la saisie");

							response = Console.askInt("Votre choix ?", 1, 2);
						} else {
							continueWord = false;
						}



					}
					game.clearRoundHistory();
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

			game.clearRoundHistory();
			try {
				game.fullFillPlayerRack(player);
			} catch (EmptyBagException e) {
				Console.message("Le sac est vide, vous ne pouvez plus piocher de jeton.");
			}
		}
	}

	private static Token askToken(Rack rack) {
		Token token = null;
		
		while (token == null) {
			String input = Console.askString("Quel lettre voulez-vous jouer (ou " + TEXT_INPUT_FOR_JOKER + " pour un joker) ?");

			try {
				token = getTokenFromInput(rack, input);
			} catch (TokenDoesntExists e) {
				Console.message("Vous ne possédez pas ce jeton dans votre rack");
			}
		}

		return token;
	}

	private static Token getTokenFromInput(Rack rack, String input) throws TokenDoesntExists {
		input = input.toUpperCase();

		ArrayList<Token> tokens = rack.getTokens();

		if (input == TEXT_INPUT_FOR_JOKER.toUpperCase()) {

			for (Token token : tokens) {
				if (token.isJoker()) {
					return token;
				}
			}

		} else {

			try {
				FrenchLetter letter = FrenchLetter.valueOf(input);
			
				if (letter != null) {
	
					for (Token token : tokens) {
						if (token.getLetter() == letter) {
							return token;
						}
					}
				}
				
			} catch (IllegalArgumentException e) {

				throw new TokenDoesntExists();
			}
		}

		throw new TokenDoesntExists();		
	}

	// private static boolean answerSwapToken(Game game, Player player) {
	// 	Integer remainingTokenInRack = player.remainingTokenInRack();
	// 	Integer tokenToSwapIndex = Console.askInt("Quel jeton voulez-vous échanger ?", 1, remainingTokenInRack);
		
	// 	Rack playerRack = player.getRack();

	// 	Token token = playerRack.getToken(tokenToSwapIndex);

	// 	try {
	// 		game.switchTokenFromRack(player, token);
	// 		return true;
	// 	} catch (EmptyBagException e) {

	// 		Console.message("Le sac est vide, impossible d'échanger un jeton.");
	// 		return false;
	// 	} catch (TokenIndexOutOfRack e) {

	// 		Console.message("Le jeton demandé n'existe pas.");
	// 		return false;
	// 	}
	// }
}