package scrabble.views.fx;

import javafx.scene.layout.HBox;
import scrabble.model.board.Box;
import scrabble.model.token.Token;

public class BoxFXView extends HBox implements FXView {
	private final Box box;

	public BoxFXView(Box box) {
		super();

		this.box = box;
	}
	
	public void updateView() {
		this.getChildren().clear();
		this.setStyle("-fx-border-color: black; -fx-border-width: 1;");

		if (!this.box.isEmpty()) {

			Token token = this.box.token();
			TokenFXView tokenFXView = new TokenFXView(token);

			this.getChildren().add(tokenFXView);
		}
	}
}