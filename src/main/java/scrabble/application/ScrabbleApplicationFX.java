package scrabble.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scrabble.model.board.Board;
import scrabble.views.fx.BoardFXView;

public class ScrabbleApplicationFX extends Application {
	@Override
	public void start(Stage primaryStage) {

		Board board = new Board();
		BoardFXView boardFXView = new BoardFXView(board);

		VBox root = new VBox();
		root.getChildren().add(boardFXView);

		primaryStage.setTitle("Scrabble");
		primaryStage.setScene(new Scene(root, 500, 500));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
