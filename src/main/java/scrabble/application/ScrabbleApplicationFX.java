package scrabble.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scrabble.controller.Game;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.utils.exceptions.EmptyBagException;
import scrabble.views.fx.BoardFXView;
import scrabble.views.fx.RackFXView;

public class ScrabbleApplicationFX extends Application {

	private void startGame(Stage primaryStage) throws EmptyBagException {
		Game game = new Game();
		Board board = game.getBoard();
		Player player = game.getPlayer();
		Rack rack = player.rack();

		game.initialize();

		BoardFXView boardFXView = new BoardFXView(board);
		RackFXView rackFXView = new RackFXView(rack);

		rackFXView.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(boardFXView);
		root.setBottom(rackFXView);

		primaryStage.setScene(new Scene(root, 1920, 1080));
	}

	@Override
	public void start(Stage primaryStage) throws EmptyBagException {

		primaryStage.setTitle("Scrabble");
		this.startGame(primaryStage);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
