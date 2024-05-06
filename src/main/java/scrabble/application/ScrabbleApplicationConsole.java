package scrabble.application;

import scrabble.model.Game;

public class ScrabbleApplicationConsole {
	public static String SEPARATOR = "-------------------------------------------------------";

	public static void main(String[] args) {
		System.out.println(SEPARATOR);
		System.out.println("-- Bienvenue dans notre magnifique jeu de scrabble ! --");
		System.out.println("-- développé par Hugo                                --");
		System.out.println("-- et par Eliott                                     --");
		System.out.println("-- et par Lucas                                      --");
		System.out.println(SEPARATOR);

		Game game = new Game();
		game.start();
	}

}
