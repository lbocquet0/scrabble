package scrabble.application;

import java.util.ArrayList;

import scrabble.controller.Game;
import scrabble.gui.console.Console;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Joker;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.exceptions.*;

public class ScrabbleApplicationConsole {

	private static String TEXT_INPUT_FOR_JOKER = "JOK";

	public static void main(String[] args) {
		Console.welcomeMessage();

		Game game = new Game();

		try {
			game.initialize();
		} catch (EmptyBagException e) {
			Console.message(e.getMessage());
			return;
		}
		
		Player player = game.getCurrentPlayer();
		Board board = game.getBoard();
		Rack rack = player.rack();

		Boolean continueGame = true;
		
		while (continueGame) {			
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
					Integer x;
					Integer y;

					Boolean isBoardEmpty;

					try {
						isBoardEmpty = board.gameIsNotStarted();
					} catch (PositionOutOfBoard e) {
						Console.message(e.getMessage());
						continueGame = false;
						break;
					}

					if (isBoardEmpty) {
						Console.message("Debut de la partie - Vous commencez automatiquement au centre du plateau.");
						x = Board.SIZE / 2 + 1;
						y = Board.SIZE / 2 + 1;
					} else {
						Console.message("Choissisez votre position de départ :");
						x = Console.askInt("Ligne ?", 1, Board.SIZE);
						y = Console.askInt("Colonne ?", 1, Board.SIZE);
					}

					Token token = askToken(rack);
					
					if(token.isJoker()) {
						token = replaceJokerLetter((Joker) token);
					}
					
					Boolean continueWord = true;
					Direction direction = Direction.HORIZONTAL;
					Integer row = x;
					Integer column = y;

					try {
						game.playLetter(token, x, y);
					} catch (OccupiedBoxException e) {
						Console.message(e.getMessage());
						continueWord = false;
						game.cancelLastAction();

					} catch (EmptyBoxException e) {
						Console.message(e.getMessage());
						continueGame = false;
						game.cancelLastAction();
					} catch (PositionOutOfBoard e) {
						Console.message(e.getMessage());
						continueGame = false;
						game.cancelLastAction();
					} catch (TokenDoesntExists e) {
						Console.message(e.getMessage());
						continueGame = false;
						game.cancelLastAction();
					} catch (IllegalMoveException e) {
						Console.message(e.getMessage());
						continueGame = false;
						game.cancelLastAction();
					}

					Integer response;
					if (isBoardEmpty) {
					
						Console.message("En jouant en premier, vous êtes contraint de jouer plus d'une lettre durant ce tour.");
						response = 1;
					}  else {
						Console.message("Avez-vous d'autres lettres à jouer ?");
						Console.message("1 - Oui");
						Console.message("2 - Non");

						response = Console.askInt("Votre choix ?", 1, 2);
					}

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
							
							if(token.isJoker()) {
								token = replaceJokerLetter((Joker) token);
							}

							try {
								game.playLetter(token, row, column);
							} catch (OccupiedBoxException e) {
								Console.message(e.getMessage());
								game.cancelLastAction();
								if (direction == Direction.HORIZONTAL) {
									column -= 1;
								} else {
									row -= 1;
								}
							} catch (EmptyBoxException e) {
								Console.message(e.getMessage());
								game.cancelLastAction();
								if (direction == Direction.HORIZONTAL) {
									column -= 1;
								} else {
									row -= 1;
								}
							} catch (PositionOutOfBoard e) {
								Console.message(e.getMessage());
							} catch (TokenDoesntExists e) {
								Console.message(e.getMessage());
							} catch (IllegalMoveException e) {
								Console.message(e.getMessage());
								game.cancelLastAction();
								if (direction == Direction.HORIZONTAL) {
									column -= 1;
								} else {
									row -= 1;
								}
							}

							Console.message("Avez-vous d'autres lettres à jouer ?");
							Console.message("1 - Oui | Continuer le mot");
							Console.message("2 - Non | Valider la saisie");

							response = Console.askInt("Votre choix ?", 1, 2);
						} else {
							continueWord = false;
						}
					}

                    try {
                        Integer newScore = game.validateWord(direction);
						Console.message("Vous avez maintenant " + newScore + " points.");
                    } catch (PositionOutOfBoard e) {
						Console.message(e.getMessage());
                    } catch (IllegalMoveException e) {
						Console.message(e.getMessage());
						game.cancelLastWord();
                    }

					break;
				case 2:
					continueGame = true;
					answerSwapToken(game, player);
					break;
				case 3:
					continueGame = false;
					break;
			}

			Console.makeSeparator();

			game.clearRoundHistory();
			try {
				game.fillUpPlayerRack(player);
			} catch (EmptyBagException e) {
				
			}

			if (player.rackIsEmpty()) {
				continueGame = false;
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
				Console.message(e.getMessage());
			}
		}

		return token;
	}

	private static Token getTokenFromInput(Rack rack, String input) throws TokenDoesntExists {
		input = input.toUpperCase();

		ArrayList<Token> tokens = rack.tokens();

		if (input.equals(TEXT_INPUT_FOR_JOKER.toUpperCase())) {

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

	private static Joker replaceJokerLetter(Joker joker) {

	    Console.message("Vous avez joué un Joker, par quelle lettre voulez vous le remplacer ?");
	    Console.message("Votre choix : ");

	    String letterInput = Console.askString("").toUpperCase();
	    FrenchLetter letter;
	    
	    while(letterInput == null || letterInput.isEmpty()) {
	    	Console.message("Veuillez saisir une lettre valide");
	    }      
 
	    try {
	        letter = FrenchLetter.valueOf(letterInput);
	    } catch (IllegalArgumentException e) {
	        Console.message(e.getMessage());
	        return joker;
	    }

	    joker.setLetter(letter);
	    return joker;
	}
	
	private static void answerSwapToken(Game game, Player player) {
		
	 	Integer remainingTokenInRack = player.remainingTokenInRack();	 	
	 	Integer tokenToSwapIndex = Console.askInt("Quel jeton voulez-vous échanger ? (donner la position du jeton dans votre chevalet)", 1, remainingTokenInRack);
	 	
	 	Rack playerRack = player.rack();

	 	Token token = playerRack.token(tokenToSwapIndex - 1);

	 	try {
	 		game.switchTokenFromRack(player, token);
	 		
	 	} catch (EmptyBagException e) {
	 		Console.message(e.getMessage());
	 		
	 	} catch (TokenDoesntExists e) {
			Console.message(e.getMessage());
		}
	}
}