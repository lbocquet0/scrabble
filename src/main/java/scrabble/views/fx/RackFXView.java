package scrabble.views.fx;

import java.util.ArrayList;

import javafx.scene.layout.HBox;
import scrabble.controller.fx.TokenDragAndDropController;
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

			TokenFXView fxView;
			if (token.isJoker()) {
				Joker joker = (Joker) token;

				fxView = new JokerFXView(joker);
				this.getChildren().add(fxView);

			} else {
					
				fxView = new TokenFXView(token);
				this.getChildren().add(fxView);

			}

			TokenDragAndDropController.manageSourceDragAndDrop(fxView);
			TokenDragAndDropController.manageTokenUpdateOrderTargetDragAndDrop(this, fxView);
		}
	}

	public Rack rack() {
		return this.rack;
	}
}