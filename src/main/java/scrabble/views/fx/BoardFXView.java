package scrabble.views.fx;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import scrabble.controller.Game;
import scrabble.controller.fx.TokenDragAndDropController;
import scrabble.model.board.Board;
import scrabble.model.board.Box;

public class BoardFXView extends GridPane implements FXView {
	private final Board board;
	private final Game game;

	public BoardFXView(Game game, Board board) {
		this.game = game;
		this.board = board;
	
		this.updateView();
	}

	public void updateView() {
		this.getChildren().clear();

		ArrayList<ArrayList<Box>> boxes = board.boxes();

		int i = 0;
		for (ArrayList<Box> row : boxes) {
			int j = 0;
			for (Box box : row) {
				BoxFXView boxFXView = new BoxFXView(box);
				this.add(boxFXView, j, i);
			
				TokenDragAndDropController.manageBoardCellDragAndDrop(this.game, boxFXView);

				j++;
			}

			i++;
		}
	}

	public Board board() {
		return this.board;
	}
}
