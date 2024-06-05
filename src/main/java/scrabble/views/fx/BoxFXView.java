package scrabble.views.fx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import scrabble.model.board.Box;
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
			TokenFXView tokenFXView = new TokenFXView(token);

			this.getChildren().add(tokenFXView);
		
		} else if (this.box.isMiddle()) {
			System.out.println("ok");

			ImageView middleImageView = this.getMiddleImageView();
			this.getChildren().add(middleImageView);
		}
	}

	private ImageView getMiddleImageView() {
		Image image = new Image("file:src/main/resources/images/star.png");

		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(TokenFXView.TOKEN_SIZE);
		imageView.setFitHeight(TokenFXView.TOKEN_SIZE);

		return imageView;
	}
}