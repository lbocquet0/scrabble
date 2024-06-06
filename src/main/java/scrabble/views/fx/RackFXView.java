package scrabble.views.fx;

import java.util.ArrayList;

import javafx.scene.layout.HBox;
import scrabble.model.Rack;
import scrabble.model.token.Joker;
import scrabble.model.token.Token;

public class RackFXView extends HBox implements FXView {
	private final Rack rack;

	private static int TOKEN_SPACING = 5;

	public RackFXView(Rack rack) {
		this.rack = rack;

		this.setSpacing(TOKEN_SPACING);

		this.updateView();
	}

	public void updateView() {
		this.getChildren().clear();

		ArrayList<Token> tokens = rack.tokens();

		for (Token token : tokens) {

			if (token.isJoker()) {
				Joker joker = (Joker) token;

				JokerFXView jokerFXView = new JokerFXView(joker);
				this.getChildren().add(jokerFXView);
			} else {

				TokenFXView tokenFXView = new TokenFXView(token);
				this.getChildren().add(tokenFXView);
			}
		}
	}
}