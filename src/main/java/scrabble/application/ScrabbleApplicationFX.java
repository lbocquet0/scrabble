package scrabble.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scrabble.model.Rack;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.views.fx.RackFXView;

public class ScrabbleApplicationFX extends Application {
	@Override
	public void start(Stage primaryStage) {

		Rack rack = new Rack();
		for (int i = 0; i < 6; i++) {

			Token token = new Token(FrenchLetter.A);
			rack.addToken(token);
		}

		RackFXView rackFXView = new RackFXView(rack);

		VBox root = new VBox();
		root.getChildren().add(rackFXView);

		primaryStage.setTitle("Scrabble");
		primaryStage.setScene(new Scene(root, 400, 250));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
