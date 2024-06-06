package scrabble.application;

import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.ChoiceDialog;
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
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.FrenchLetterComparator;
import scrabble.model.token.Joker;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
import scrabble.utils.exceptions.EmptyBagException;
import scrabble.utils.exceptions.EmptyBoxException;
import scrabble.utils.exceptions.OccupiedBoxException;
import scrabble.utils.exceptions.TokenDoesntExists;
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

	private static Token answerToken(Rack rack, String title, String header) {
		ArrayList<Token> tokens = rack.tokens();
		ChoiceDialog<Token> dialog = new ChoiceDialog<Token>(tokens.get(0), tokens);

		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText("Jeton: ");

		Optional<Token> result = dialog.showAndWait();
		if (result.isPresent()) {

			return result.get();
		} else {

			return null;
		}
	}

	private static boolean createConfirmation(String title, String header) {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Jouer un mot");
		alert.setHeaderText("Voulez-vous continuer à jouer ?");

		alert.getDialogPane().getButtonTypes().add(ButtonType.YES);
		alert.getDialogPane().getButtonTypes().add(ButtonType.NO);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			ButtonType selectButton = result.get();
			if (selectButton == ButtonType.YES) {
				return true;
			}
		}

		return false;
	}
		
	private void swapTokens(Game game, Player player) {
		Rack rack = player.rack();

		Token token = answerToken(rack, "Changer un jeton", "Sélectionnez le jeton que vous voulez échanger");
		if (token != null) {
			try {
				game.switchTokenFromRack(player, token);
			} catch (EmptyBagException | TokenDoesntExists e) {
				displayError(e.getMessage());
			}
		}
	}

	private HBox getButtons(Game game, Player player, Rack rack, BoardFXView boardFXView, RackFXView rackFXView) {
		Button swapTokenButton = new Button("Changer un jeton");
		
		swapTokenButton.setOnAction(e -> {
			swapTokens(game, player);
			rackFXView.updateView();
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

	private static void updateJokerLetter(Joker joker) {
		FrenchLetter[] choices = FrenchLetter.values();
		Arrays.sort(choices, new FrenchLetterComparator());
		
		ChoiceDialog<FrenchLetter> dialog = new ChoiceDialog<>(choices[0], choices);
		dialog.setTitle("Changer la lettre du joker");
		dialog.setHeaderText("Selectionnez la lettre à jouer");

		Optional<FrenchLetter> result = dialog.showAndWait();
		if (result.isPresent()) {

			FrenchLetter selectedLetter = result.get();
			joker.setLetter(selectedLetter);
		}
	}
	
	private static void playLetter(Game game, Rack rack, Integer row, Integer column, Direction direction) {
		Token token = answerToken(rack, "Jouer un mot", "Selectionnez la lettre à jouer");

		if (token != null) {
			if (token.isJoker()) {
				Joker joker = (Joker) token;
	
				updateJokerLetter(joker);
			}
	
			try {
				game.playLetter(token, row, column);
			} catch (OccupiedBoxException | BoxIndexOutOfBoard | TokenDoesntExists | EmptyBoxException e) {
				displayError(e.getMessage());
				game.cancelLastWord();
				return;
			}
	
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
		}
	}

	private static void continuePlayWord(Game game, Rack rack, Integer row, Integer column, Direction direction) {
		boolean shouldContinue = createConfirmation("Jouer un mot", "Voulez-vous continuer à jouer ?");

		if (shouldContinue) {
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
