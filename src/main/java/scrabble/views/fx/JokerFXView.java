package scrabble.views.fx;

import javafx.scene.control.Label;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Joker;

public class JokerFXView extends TokenFXView {
	public JokerFXView(Joker joker) {
		super(joker);
	}

	@Override
	public void updateView() {
		Label letterLabel = this.getLetterLabel();
		Label pointsLabel = this.getPointsLabel();

		Joker joker = (Joker) this.getToken();

		if (joker.haveLetter()) {
			FrenchLetter letter = joker.getLetter();
			
			letterLabel.setText(letter.toString());
		} else {
			
			letterLabel.setText(Joker.JOKER_TEXT);
		}

		pointsLabel.setText("");
	}
}