package scrabble.model;

import scrabble.controller.Game;
import scrabble.gui.console.Console;
import scrabble.model.token.Token;

public class Player {
	private Rack rack;
	private Game game;

	public Player() {
		this.rack = new Rack();
	}

	public Rack getEasel() {
		return this.rack;
	}

	public Game getGame() {
		return this.game;
	}

	public void play() {
		Console.message("Voici votre chevalet :");
		this.rack.display();

		Integer tokenToSwap = Console.askInt("Quel jeton voulez-vous échanger ?", 1, this.rack.remainingTokens());
		Token token = this.rack.getToken(tokenToSwap - 1);

		this.rack.swapTokens(token);

		Console.message("Vous venez de d'échanger le jeton " + token.display() + " avec le sac");
		Console.message("Voici votre nouveau chevalet :");
		
		this.rack.display();
	}
}
