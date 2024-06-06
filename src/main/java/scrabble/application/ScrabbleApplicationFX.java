package scrabble.application;

import java.util.Optional;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scrabble.controller.Game;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.utils.Direction;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
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
		
		Button swapTokenButton = new Button("Changer un jeton");
		swapTokenButton.setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.NONE);
			alert.setTitle("Changer un jeton");
			alert.setHeaderText("Sélectionnez le jeton que vous voulez échanger");

			for (int i = 0; i < rack.remainingTokens(); i++) {
				alert.getDialogPane().getButtonTypes().add(new ButtonType(rack.token(i).display()));
			}

			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent()) {
				int index = alert.getDialogPane().getButtonTypes().indexOf(result.get());
				try {
					game.switchTokenFromRack(player, rack.token(index));
					rackFXView.updateView();
				} catch (Exception err) {
					displayError(err.getMessage());
				} 
			}
		});

		Button playButton = new Button("Jouer un mot");
		playButton.setOnAction(e -> {
			try {
				playWord(game, rack);
			} catch (BoxIndexOutOfBoard err) {
				displayError(err.getMessage());
			}
			try {
				game.fullFillPlayerRack(player);
			} catch (EmptyBagException err) {
				displayError(err.getMessage());
			}
			rackFXView.updateView();
			boardFXView.updateView();
		});
		
		HBox Buttons = new HBox();
		Buttons.getChildren().addAll(swapTokenButton, playButton);

		BorderPane root = new BorderPane();
		root.setCenter(boardFXView);
		root.setBottom(rackFXView);
		BorderPane.setMargin(rackFXView, new Insets(0, 0, 20, 0));
		
		VBox statisticPane = this.getStatisticPane(game);
		root.setRight(statisticPane);
		root.setLeft(Buttons);

		primaryStage.setScene(new Scene(root, 1920, 1080));
	}

	private static void playWord(Game game, Rack rack) throws BoxIndexOutOfBoard {
		Boolean isFirstRound = false;

		if (game.roundNumber() == 1 && game.getBoard().gameHaveNotStarted()) {
			isFirstRound = true;
		}
		
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Jouer un mot");
		alert.setHeaderText("Entrez la position initiale");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField line = new TextField();
		line.setPromptText("Ligne");
		TextField column = new TextField();
		column.setPromptText("Colonne");

		if (isFirstRound) {
			grid.add(new Label("Ligne : 8 [CENTRE]"), 0, 0);
			grid.add(new Label("Colonne : 8 [CENTRE]"), 0, 1);
		} else {
			grid.add(new Label("Ligne :"), 0, 0);
			grid.add(line, 1, 0);
			grid.add(new Label("Colonne :"), 0, 1);
			grid.add(column, 1, 1);
		}

		grid.add(new Label("Direction :"), 0, 2);
		ChoiceBox<Object> direction = new ChoiceBox<Object>();
		direction.getItems().addAll(Direction.HORIZONTAL, Direction.VERTICAL);
		direction.setValue(Direction.HORIZONTAL);
		grid.add(direction, 1, 2);


		alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
		alert.getDialogPane().setContent(grid);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			if (!isFirstRound) {
				if (line.getText().isEmpty() || column.getText().isEmpty()) {
					displayError("Veuillez remplir les champs");
					return;
				}

				if (Integer.parseInt(line.getText()) < 0 || Integer.parseInt(line.getText()) > 14) {
					displayError("La ligne doit être comprise entre 0 et 14");
					return;
				}

				if (Integer.parseInt(column.getText()) < 0 || Integer.parseInt(column.getText()) > 14) {
					displayError("La colonne doit être comprise entre 0 et 14");
					return;
				}

				int x = Integer.parseInt(line.getText());
				int y = Integer.parseInt(column.getText());
				Direction dir = direction.getValue() == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
				playLetter(game, rack, x, y, dir);

			} else {
				Direction dir = direction.getValue() == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
				playLetter( game, rack, 8, 8, dir);
			}

		}
	}
	
	private static void playLetter(Game game, Rack rack, Integer x, Integer y, Direction dir) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Jouer un mot");
		alert.setHeaderText("Selectionnez la lettre à jouer");

		for (int i = 0; i < rack.remainingTokens(); i++) {
			alert.getDialogPane().getButtonTypes().add(new ButtonType(rack.token(i).display()));
		}

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			int index = alert.getDialogPane().getButtonTypes().indexOf(result.get());
			try {
				game.playLetter(rack.token(index), x, y);
				if (game.roundNumber() == 1 && x == 8 && y == 8) {
					if (dir == Direction.HORIZONTAL) {
						x++;
					} else {
						y++;
					}
					playLetter(game, rack, x, y, dir);
				} else {
					continuePlayWord(game, rack, x, y, dir);
				}
			} catch (Exception err) {
				displayError(err.getMessage());
				game.cancelLastWord();
			}
        }
	}

	private static void continuePlayWord(Game game, Rack rack, Integer x, Integer y, Direction dir) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Jouer un mot");
		alert.setHeaderText("Voulez-vous continuer à jouer ?");

		alert.getDialogPane().getButtonTypes().add(ButtonType.YES);
		alert.getDialogPane().getButtonTypes().add(ButtonType.NO);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			if (result.get() == ButtonType.YES) {
				if (dir == Direction.HORIZONTAL) {
					x++;
				} else {
					y++;
				}
				playLetter(game, rack, x, y, dir);
			} else {
				try {
					game.validateWord(dir);
				} catch (Exception err) {
					displayError(err.getMessage());
					game.cancelLastWord();
				}
			}
		}
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
