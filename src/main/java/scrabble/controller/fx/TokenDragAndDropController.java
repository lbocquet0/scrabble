package scrabble.controller.fx;

import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import scrabble.model.Rack;
import scrabble.utils.exceptions.TokenDoesntExists;
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

	public static void manageTokenUpdateOrderTargetDragAndDrop(RackFXView rackView, TokenFXView target) {
		
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
}
