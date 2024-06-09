package scrabble.controller.fx;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import scrabble.controller.Game;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.board.Box;
import scrabble.model.token.Token;
import scrabble.utils.Position;
import scrabble.utils.exceptions.TokenDoesntExists;
import scrabble.views.fx.BoardFXView;
import scrabble.views.fx.BoxFXView;
import scrabble.views.fx.RackFXView;
import scrabble.views.fx.TokenFXView;

public class TokenDragAndDropController {
	
	public static void manageSourceDragAndDrop(TokenFXView source) {
		source.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Dragboard db = source.startDragAndDrop(TransferMode.ANY);

				ClipboardContent content = new ClipboardContent();
				content.putString(source.toString());
				db.setContent(content);

				event.consume();
			}
		});	
		
		source.setOnDragDone(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.consume();
			}
		});
	}

	public static void manageTokenUpdateOrderTargetDragAndDrop(TokenFXView target) {
		
		target.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != target) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});
		
		target.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.consume();
				}
			});
		
		target.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.consume();
			}
		});

		target.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {

				TokenFXView source = (TokenFXView) event.getGestureSource();
				RackFXView rackView = (RackFXView) source.getParent();

				Rack rack = rackView.rack();

				try {
					rack.swapTokens(source.token(), target.token());
					event.setDropCompleted(true);

					rackView.updateView();
				} catch (TokenDoesntExists e) {
					event.setDropCompleted(false);
				}
					
				event.consume();
			}
		});
	}

	public static void manageBoardCellDragAndDrop(Game game, BoxFXView target) {
	
		target.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != target) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

		target.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.consume();
			}
		});

		target.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.consume();
			}
		});

		target.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				
				TokenFXView source = (TokenFXView) event.getGestureSource();
				RackFXView rackFxView = (RackFXView) source.getParent();
				BoardFXView boardFxView = (BoardFXView) target.getParent();

				Token token = source.token();
				Box box = target.box();
				Board board = boardFxView.board();

				Position boxPos = board.getBoxPosition(box);
			
				try {
					game.playLetter(token, boxPos.row(), boxPos.column());
					event.consume();
	
					boardFxView.updateView();
					rackFxView.updateView();

				} catch (Exception e) {
					event.setDropCompleted(false);
				}

				event.setDropCompleted(true);
			}
		});
	}
}
