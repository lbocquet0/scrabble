package scrabble.model;

import scrabble.gui.console.Console;
import scrabble.model.token.Token;

public class Player {
	private Rack easel;
	private Game game;

	public Player(Game game) {
		this.game = game;
		this.easel = new Rack(this);
	}

	public Rack getEasel() {
		return this.easel;
	}

	public Game getGame() {
		return this.game;
	}

	public void play() {
		Console.message("Voici votre chevalet :");
		this.easel.display();

		Integer tokenToSwap = Console.askInt("Quel jeton voulez-vous échanger ?", 1, this.easel.getTokensAmount());
		Token token = this.easel.getToken(tokenToSwap - 1);

		this.easel.swapTokens(token);

		Console.message("Vous venez de d'échanger le jeton " + token.display() + " avec le sac");
		Console.message("Voici votre nouveau chevalet :");
		
		this.easel.display();
	}
}
