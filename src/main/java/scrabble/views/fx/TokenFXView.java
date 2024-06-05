package scrabble.views.fx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import scrabble.model.token.Token;

public class TokenFXView extends HBox {
	private final Token token;

	private static int TOKEN_SIZE = 50;
	private static int LETTER_FONT_SIZE = 20;
	private static int POINTS_FONT_SIZE = 15;

	public TokenFXView(Token token) {
		super();
		this.token = token;

		this.setPrefSize(TOKEN_SIZE, TOKEN_SIZE);
		
		updateView();
	}

	private void updateView() {
		this.getChildren().clear();

		Label label = new Label(token.getLetter().toString());
		label.setFont(new Font(LETTER_FONT_SIZE));
		label.setTextAlignment(TextAlignment.CENTER);
		
		Label pointsLabel = new Label(Integer.toString(token.getScore()));
		pointsLabel.setFont(new Font(POINTS_FONT_SIZE));
		pointsLabel.setTextAlignment(TextAlignment.CENTER);
		pointsLabel.setTranslateY(LETTER_FONT_SIZE);
		
		this.getChildren().add(label);
		this.getChildren().add(pointsLabel);

		this.setStyle("-fx-border-color: black; -fx-border-width: 1;");
	}
}