package scrabble.views.fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import scrabble.model.token.Token;

public class TokenFXView extends StackPane implements FXView {
	private final Token token;

	public static final int TOKEN_SIZE = 50;
	private static final int LETTER_FONT_SIZE = 20;
	private static final int POINTS_FONT_SIZE = 10;

	public TokenFXView(Token token) {
		super();
		this.token = token;

		this.setPrefSize(TOKEN_SIZE, TOKEN_SIZE);
		this.setStyle("-fx-border-color: black; -fx-border-width: 1;");
		
		this.updateView();
	}

	public void updateView() {
		this.getChildren().clear();

		Label label = new Label(token.getLetter().toString());
		label.setFont(new Font(LETTER_FONT_SIZE));
		
		Label pointsLabel = new Label(Integer.toString(token.getScore()));
		pointsLabel.setFont(new Font(POINTS_FONT_SIZE));
		
		this.getChildren().add(label);
		this.getChildren().add(pointsLabel);

		StackPane.setAlignment(label, Pos.CENTER);
		StackPane.setAlignment(pointsLabel, Pos.BOTTOM_RIGHT);

		StackPane.setMargin(pointsLabel, new Insets(0, 10, 7.5, 0));
	}
}