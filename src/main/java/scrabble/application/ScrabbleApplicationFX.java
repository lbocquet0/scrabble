package scrabble.application;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scrabble.controller.Game;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.utils.exceptions.EmptyBagException;
import scrabble.views.fx.BoardFXView;
import scrabble.views.fx.RackFXView;

public class ScrabbleApplicationFX extends Application {

	private static void displayError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private VBox getStatisticPane(Game game) {
		Player player = game.getPlayer();

		VBox statisticPane = new VBox();

		IntegerProperty playerScore = player.scoreProperty();
		Label player1ScoreLabel = new Label();
		player1ScoreLabel.textProperty().bind(playerScore.asString());

		// TODO: Use the round number from the game
		Label roundAmountLabel = new Label("Round: 1");

		statisticPane.getChildren().addAll(player1ScoreLabel, roundAmountLabel);
		statisticPane.setAlignment(Pos.TOP_LEFT);

		return statisticPane;
	}

	private void startGame(Stage primaryStage) throws EmptyBagException {
		Game game = new Game();
		Board board = game.getBoard();
		Player player = game.getPlayer();
		Rack rack = player.rack();

		game.initialize();

		BoardFXView boardFXView = new BoardFXView(board);
		boardFXView.setAlignment(Pos.CENTER);

		RackFXView rackFXView = new RackFXView(rack);

		rackFXView.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(boardFXView);
		root.setBottom(rackFXView);
		
		VBox statisticPane = this.getStatisticPane(game);
		root.setRight(statisticPane);

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
