package scrabble.application;

import scrabble.controller.Game;
import scrabble.gui.console.Console;

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
		game.start();
	}

}
