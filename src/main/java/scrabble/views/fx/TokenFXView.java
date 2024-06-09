package scrabble.views.fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;

public class TokenFXView extends StackPane implements FXView {
	private final Token token;
	private final Label letterLabel;
	private final Label pointsLabel;

	public static final int TOKEN_SIZE = 50;
	private static final int LETTER_FONT_SIZE = 20;
	private static final int POINTS_FONT_SIZE = 10;

	public TokenFXView(Token token) {
		super();
		this.token = token;

		this.setPrefSize(TOKEN_SIZE, TOKEN_SIZE);
		this.setStyle("-fx-border-color: black; -fx-border-width: 1;");
		
		this.letterLabel = new Label();
		this.letterLabel.setFont(new Font(LETTER_FONT_SIZE));

		this.pointsLabel = new Label();
		this.pointsLabel.setFont(new Font(POINTS_FONT_SIZE));

		this.getChildren().add(letterLabel);
		this.getChildren().add(pointsLabel);

		StackPane.setAlignment(letterLabel, Pos.CENTER);
		StackPane.setAlignment(pointsLabel, Pos.BOTTOM_RIGHT);

		StackPane.setMargin(pointsLabel, new Insets(0, 10, 7.5, 0));

		this.updateView();
	}

	public void updateView() {
		FrenchLetter letter = token.getLetter();
		
		letterLabel.setText(letter.toString());

		Integer points = this.token.getScore();
		this.pointsLabel.setText(points.toString());
	}

	public Label getLetterLabel() {
		return letterLabel;
	}

	public Label getPointsLabel() {
		return pointsLabel;
	}

	public Token token() {
		return token;
	}
}