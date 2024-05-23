package scrabble.application;

import scrabble.controller.Game;
import scrabble.gui.console.Console;
import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.board.Board;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.BoxIndexOutOfBoard;
import scrabble.utils.Direction;
import scrabble.utils.EmptyBagException;
import scrabble.utils.EmptyBoxException;
import scrabble.utils.OccupiedBoxException;
import scrabble.utils.UnpossesedTokenException;
import scrabble.utils.PlayALetterOutOfBoard;
import scrabble.utils.TokenIndexOutOfRack;

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
					Console.message("Jouer un mot");
					Console.message("Choissisez votre position de départ :");
					Integer x = Console.askInt("Ligne ?", 1, Board.SIZE);
					Integer y = Console.askInt("Colonne ?", 1, Board.SIZE);

					String letter = Console.askString("Quel lettre voulez-vous jouer ?");
					Token token = new Token(FrenchLetter.valueOf(letter.toUpperCase()));
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
					} catch (UnpossesedTokenException e) {
						Console.message("Vous n'avez pas le token [" + token.getLetter() + "] dans votre chevalet.");
						continueGame = false;
						game.cancelWord();
					} catch (BoxIndexOutOfBoard e) {
						Console.message("Les coordonnées (" + e.getRow() + "," + e.getColumn() + ") que vous avez renseignées sont en dehors du plateau.");
						continueGame = false;
						game.cancelWord();
					} catch (TokenIndexOutOfRack e) {
						Console.message("Vous n'avez pas de jeton n°" + e.getIndex() + " dans votre chevalet.");
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

							letter = Console.askString("Quel lettre voulez-vous jouer ?");
							token = new Token(FrenchLetter.valueOf(letter.toUpperCase()));

							try {
								game.playLetter(token, row, column);
							} catch (OccupiedBoxException e) {
								Console.message("La case est déjà occupée.");
								game.cancelWord();
							} catch (EmptyBoxException e) {
								Console.message("La case n'a pas correctement été remplie.");
								game.cancelWord();
							} catch (UnpossesedTokenException e) {
								Console.message("Vous n'avez pas le token [" + token.getLetter() + "] dans votre chevalet.");
								game.cancelWord();
							} catch (BoxIndexOutOfBoard e) {
								Console.message("Les coordonnées (" + e.getRow() + "," + e.getColumn() + ") que vous avez renseigner sont en dehors du plateau.");
							} catch (TokenIndexOutOfRack e) {
								Console.message("Vous n'avez pas de jeton n°" + e.getIndex() + " dans votre chevalet.");
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

	private static boolean answerSwapToken(Game game, Player player) {
		Integer remainingTokenInRack = player.remainingTokenInRack();
		Integer tokenToSwapIndex = Console.askInt("Quel jeton voulez-vous échanger ?", 1, remainingTokenInRack);
		
		try {
			game.switchTokenFromRack(player, tokenToSwapIndex);
			return true;
		} catch (EmptyBagException e) {

			Console.message("Le sac est vide, impossible d'échanger un jeton.");
			return false;
		} catch (TokenIndexOutOfRack e) {

			Console.message("Le jeton demandé n'existe pas.");
			return false;
		}
	}
}