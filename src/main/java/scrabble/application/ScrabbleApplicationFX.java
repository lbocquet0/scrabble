package scrabble.application;

import java.util.Optional;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
import scrabble.model.token.Token;
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
		Label playerScoreLabel = new Label();
		playerScoreLabel.textProperty().bind(Bindings.concat("Score : ", playerScore));

		IntegerProperty roundAmount = game.roundNumberProperty();
		Label roundAmountLabel = new Label();
		roundAmountLabel.textProperty().bind(Bindings.concat("Manche : ", roundAmount));

		statisticPane.getChildren().addAll(playerScoreLabel, roundAmountLabel);
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
		
		HBox buttonsPanel = getButtons(game, player, rack, boardFXView, rackFXView);

		BorderPane root = new BorderPane();
		root.setCenter(boardFXView);
		root.setBottom(rackFXView);
		BorderPane.setMargin(rackFXView, new Insets(0, 0, 20, 0));
		
		VBox statisticPane = this.getStatisticPane(game);
		root.setRight(statisticPane);
		root.setLeft(buttonsPanel);

		primaryStage.setScene(new Scene(root, 1900, 1000));
	}

	private HBox getButtons(Game game, Player player, Rack rack, BoardFXView boardFXView, RackFXView rackFXView) {
		Button swapTokenButton = new Button("Changer un jeton");
		swapTokenButton.setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.NONE);
			alert.setTitle("Changer un jeton");
			alert.setHeaderText("Sélectionnez le jeton que vous voulez échanger");

			for (int i = 0; i < rack.remainingTokens(); i++) {
				Token token = rack.token(i);

				ButtonType button = new ButtonType(token.display());
				alert.getDialogPane().getButtonTypes().add(button);
			}

			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent()) {
				ButtonType selectedButton = result.get();
				int index = alert.getDialogPane().getButtonTypes().indexOf(selectedButton);
				
				try {
					Token token = rack.token(index);

					game.switchTokenFromRack(player, token);
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

			rackFXView.updateView();
			boardFXView.updateView();
		});
		
		HBox buttonsPanel = new HBox();
		buttonsPanel.getChildren().addAll(swapTokenButton, playButton);
		return buttonsPanel;
	}

	private static void playWord(Game game, Rack rack) throws BoxIndexOutOfBoard {
		Boolean isFirstRound = false;

		if (game.getBoard().gameHaveNotStarted()) {
			isFirstRound = true;
		}
		
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Jouer un mot");
		alert.setHeaderText("Entrez la position initiale");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField rowInput = new TextField();
		rowInput.setPromptText("Ligne");
		TextField columnInput = new TextField();
		columnInput.setPromptText("Colonne");

		if (isFirstRound) {
			grid.add(new Label("Ligne : 8 [CENTRE]"), 0, 0);
			grid.add(new Label("Colonne : 8 [CENTRE]"), 0, 1);
		} else {
			grid.add(new Label("Ligne :"), 0, 0);
			grid.add(rowInput, 1, 0);
			grid.add(new Label("Colonne :"), 0, 1);
			grid.add(columnInput, 1, 1);
		}

		grid.add(new Label("Direction :"), 0, 2);

		ChoiceBox<Direction> directionChoices = new ChoiceBox<Direction>();
		directionChoices.getItems().addAll(Direction.HORIZONTAL, Direction.VERTICAL);
		directionChoices.setValue(Direction.HORIZONTAL);
		grid.add(directionChoices, 1, 2);

		alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
		alert.getDialogPane().setContent(grid);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			Direction direction = directionChoices.getValue();

			int row;
			int column;
			if (!isFirstRound) {
				if (rowInput.getText().isEmpty() || columnInput.getText().isEmpty()) {
					displayError("Veuillez remplir les champs");
					return;
				}

				row = Integer.parseInt(rowInput.getText());
				column = Integer.parseInt(columnInput.getText());
			} else {

				row = 8;
				column = 8;
			}

			playLetter(game, rack, row, column, direction);
		}
	}
	
	private static void playLetter(Game game, Rack rack, Integer row, Integer column, Direction direction) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Jouer un mot");
		alert.setHeaderText("Selectionnez la lettre à jouer");

		for (int i = 0; i < rack.remainingTokens(); i++) {
			Token token = rack.token(i);
			
			alert.getDialogPane().getButtonTypes().add(new ButtonType(token.display()));
		}

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			ButtonType selectedButton = result.get();
			int index = alert.getDialogPane().getButtonTypes().indexOf(selectedButton);
			try {
				game.playLetter(rack.token(index), row, column);

				if (game.roundNumber() == 1 && row == 8 && column == 8) {
					if (direction == Direction.HORIZONTAL) {
						column++;
					} else {
						row++;
					}

					playLetter(game, rack, row, column, direction);
				} else {

					continuePlayWord(game, rack, row, column, direction);
				}
			} catch (Exception err) {
				displayError(err.getMessage());
				game.cancelLastWord();
			}
        }
	}

	private static void continuePlayWord(Game game, Rack rack, Integer row, Integer column, Direction direction) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Jouer un mot");
		alert.setHeaderText("Voulez-vous continuer à jouer ?");

		alert.getDialogPane().getButtonTypes().add(ButtonType.YES);
		alert.getDialogPane().getButtonTypes().add(ButtonType.NO);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			ButtonType selectButton = result.get();
			if (selectButton == ButtonType.YES) {
				if (direction == Direction.HORIZONTAL) {
					column++;
				} else {
					row++;
				}

				playLetter(game, rack, row, column, direction);
			} else {
				
				try {
					game.validateWord(direction);
					game.nextRound();
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
