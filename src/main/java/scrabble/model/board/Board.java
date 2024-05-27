package scrabble.model.board;

import java.util.ArrayList;

import scrabble.gui.console.Console;
import scrabble.model.board.action.Action;
import scrabble.model.board.action.ActionHistory;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
import scrabble.utils.exceptions.EmptyBoxException;
import scrabble.utils.exceptions.WordNotFoundException;

public class Board {
	
	public static int SIZE = 15;
	
	private ArrayList<ArrayList<Box>> boxes;

	private ActionHistory actionHistory;
	
	public Board() {
		this.boxes = new ArrayList<ArrayList<Box>>();
		this.actionHistory = new ActionHistory();

		fillBoxes();
	}

	private void fillBoxes() {
		for (int i = 1; i <= SIZE; i++) {
			ArrayList<Box> currentLine = new ArrayList<>();
			
			for (int j = 1; j <= SIZE; j++) {
				boolean isMiddle = (i == 8 && j ==8);
				currentLine.add(new Box(isMiddle, null));
			}
			this.boxes.add(currentLine);
		}
	}

	public ArrayList<ArrayList<Box>> getBoxes() {
		return this.boxes;
	}

	public Boolean gameHaveNotStarted() throws BoxIndexOutOfBoard {
        return this.getBox(8, 8).isEmpty();
	}

	public Box getBox(Integer row, Integer column) throws BoxIndexOutOfBoard {
		if (row < 1 || row > SIZE || column < 1 || column > SIZE) {
			throw new BoxIndexOutOfBoard(row, column);
		}

		return this.boxes.get(row-1).get(column-1);
	}

	public Token getToken(Integer row, Integer column) throws BoxIndexOutOfBoard {
		Box box = this.getBox(row, column);

		return box.getToken();
	}
	
	public void setToken(Token token, Integer row, Integer column) throws BoxIndexOutOfBoard, EmptyBoxException {
		Box box = this.getBox(row, column);
		
		box.setToken(token);
		this.actionHistory.add(new Action(row, column, box));
	}

	public Boolean isLetterAround(Integer row, Integer column) {
		Boolean isLetterAround = false;
		// Check if there is a letter on side of boxes but not on diagonal and the boxes
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (i >= 1 && i <= SIZE && j >= 1 && j <= SIZE) {
					if (i != row || j != column) {
						if ((i == row && j != column) || (i != row && j == column)) {
							try {
								if (!this.getBox(i, j).isEmpty()) {
									isLetterAround = true;
								}
							} catch (BoxIndexOutOfBoard e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}

		return isLetterAround;
	}

	public ArrayList<Token> cancelLastAction() {
		return this.actionHistory.undoAllActions();
	}

	public void clearHistory() {
		this.actionHistory.clear();
	}

	public ArrayList<Action> getActionsHistory() {
		return this.actionHistory.actions();
	}

	// TODO: Assert throws
	public ArrayList<Box> getWord(Integer row, Integer column, Direction direction) throws BoxIndexOutOfBoard, WordNotFoundException {
		Box currentBox = this.getBox(row, column);
		if (currentBox.isEmpty()) {
			throw new WordNotFoundException();
		}
		
		ArrayList<Box> word = new ArrayList<>();

		Integer currentPosition = direction == Direction.HORIZONTAL ? column : row;
		Integer otherPosition = direction == Direction.HORIZONTAL ? row : column;
		
		while (currentPosition > 1) {
			currentPosition--;
			Box previousBox = direction == Direction.HORIZONTAL ? this.getBox(otherPosition, currentPosition) : this.getBox(currentPosition, otherPosition);
			if (previousBox.isEmpty()) {
				break;
			}
			word.add(previousBox);
		}

		word.add(currentBox);

		currentPosition = direction == Direction.HORIZONTAL ? column : row;
		otherPosition = direction == Direction.HORIZONTAL ? row : column;

		while (currentPosition < SIZE) {
			currentPosition++;
			Box nextBox = direction == Direction.HORIZONTAL ? this.getBox(otherPosition, currentPosition) : this.getBox(currentPosition, otherPosition);
			if (nextBox.isEmpty()) {
				break;
			}
			word.add(nextBox);
		}

		if (word.size() == 1) {
			throw new WordNotFoundException();
		}

		return word;
	}

	public void display() {
	    StringBuilder horizontalLine = new StringBuilder("");
		String line;
	    for (int i = 0; i <= SIZE; i++) {
	        horizontalLine.append("------");
	    }

	    Console.message(horizontalLine.toString());

	    for (Integer i = 0; i <= SIZE; i++) {
	    	if (i < 10) {
	    		line = i + "   ┃";
	    	} else {
	    		line = i + "  ┃";
	    	}
	       
	        for (Integer j = 1; j <= SIZE; j++) {
	        	String tokenDisplay = "    ";
	        	if (i == 0) {
	        		tokenDisplay = "  " + j.toString() + " ";
	        		
	        	} else {

					try {
						Box box = this.getBox(i, j);
	   
						if (box != null && box.getToken() != null) {
							tokenDisplay = box.getToken().display();
						} else {
							if (box != null && box.isMiddle()) {
								tokenDisplay = "  ⭐ ";
							}
						}
					} catch (BoxIndexOutOfBoard e) {
						e.printStackTrace();
					
					}
	        	}

	            if (tokenDisplay.length() == 4) {
					line = line + tokenDisplay + " ┃";
	            } else {
	            	line = line + tokenDisplay + "┃";
				}
	        }

			Console.message(line);
	        

	        if (i != SIZE) {
	            Console.message(horizontalLine.toString());
	        }
	    }

	    StringBuilder bottomHorizontalLine = new StringBuilder("");
	    for (int i = 0; i <= SIZE; i++) {
	        bottomHorizontalLine.append("------");
	    }
		
	    bottomHorizontalLine.append("-\n");
	    Console.message(bottomHorizontalLine.toString());
	}
}
