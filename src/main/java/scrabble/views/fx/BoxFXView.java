package scrabble.views.fx;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import scrabble.model.board.Box;
import scrabble.model.board.Effect;
import scrabble.model.token.Joker;
import scrabble.model.token.Token;

public class BoxFXView extends StackPane implements FXView {
	private final Box box;

	public BoxFXView(Box box) {
		super();

		this.box = box;
		this.setPrefSize(TokenFXView.TOKEN_SIZE, TokenFXView.TOKEN_SIZE);

		this.updateView();
	}
	
	public void updateView() {
		this.getChildren().clear();
		this.setStyle("-fx-border-color: black; -fx-border-width: 1;");

		if (!this.box.isEmpty()) {

			Token token = this.box.token();

			if (token.isJoker()) {
				Joker joker = (Joker) token;

				JokerFXView jokerFXView = new JokerFXView(joker);
				this.getChildren().add(jokerFXView);
			} else {

				TokenFXView tokenFXView = new TokenFXView(token);
				this.getChildren().add(tokenFXView);
			}
		
		} else if (this.box.isMiddle()) {

			ImageView middleImageView = this.getMiddleImageView();
			this.getChildren().add(middleImageView);
		
		} else {
			Effect effect = this.box.effect();
			String content = effect.content();


			if (content != null) {
				Label label = new Label(content);
				this.getChildren().add(label);
			}

			String backgroundColor = effect.backgroundColor();
			if (backgroundColor != null) {
				this.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-color: black; -fx-border-width: 1;");
			}
		}
	}

	private ImageView getMiddleImageView() {
		Image image = new Image("file:src/main/resources/images/star.png");

		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(TokenFXView.TOKEN_SIZE);
		imageView.setFitHeight(TokenFXView.TOKEN_SIZE);

		return imageView;
	}

	public Box box() {
		return this.box;
	}
}