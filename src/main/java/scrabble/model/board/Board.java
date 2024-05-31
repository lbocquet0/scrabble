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
				boolean isMiddle = (i == SIZE/2 + 1 && j == SIZE/2 + 1);
				currentLine.add(new Box(isMiddle, null));
			}
			this.boxes.add(currentLine);
		}
	}

	public ArrayList<ArrayList<Box>> boxes() {
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

		return box.token();
	}
	
	public void setToken(Token token, Integer row, Integer column) throws BoxIndexOutOfBoard, EmptyBoxException {
		Box box = this.getBox(row, column);
		
		box.setToken(token);
		this.actionHistory.add(new Action(row, column, box));
	}

	public Boolean isLetterAround(Integer row, Integer column) throws BoxIndexOutOfBoard{
		Boolean isLetterAround = false;
		// Check if there is a letter on side of boxes but not on diagonal and the boxes
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (i >= 1 && i <= SIZE && j >= 1 && j <= SIZE) {
					if (i != row || j != column) {
						if (isOnSide(row, column, i, j)) {
							if (!this.getBox(i, j).isEmpty()) {
								isLetterAround = true;
							}
						}
					}
				}
			}
		}

		return isLetterAround;
	}

	private static boolean isOnSide(Integer row, Integer column, int i, int j) {
		return (i == row && j != column) || (i != row && j == column);
	}

	public ArrayList<Token> cancelLastWord() {
		return this.actionHistory.undoAllActions();
	}

	public Token cancelLastAction() {
		return this.actionHistory.undoLastAction();
	}

	public void clearHistory() {
		this.actionHistory.clear();
	}

	public ArrayList<Action> getActionsHistory() {
		return this.actionHistory.actions();
	}

	public ArrayList<Box> getWord(Integer row, Integer column, Direction direction) throws BoxIndexOutOfBoard, WordNotFoundException {
		if (row < 1 || row > SIZE || column < 1 || column > SIZE) {
			throw new BoxIndexOutOfBoard(row, column);
		}

		Box currentBox = this.getBox(row, column);
		if (currentBox.isEmpty()) {
			throw new WordNotFoundException();
		}
	
		ArrayList<Box> word = new ArrayList<>();
	
		Integer currentPosition = direction == Direction.HORIZONTAL ? column : row;
		Integer otherPosition = direction == Direction.HORIZONTAL ? row : column;
	
		Integer tempPosition = currentPosition - 1;

		// TODO : EXTRACT VARIABLE ON GETBOX
		while (tempPosition >= 1 && !this.getBox(direction == Direction.HORIZONTAL ? otherPosition : tempPosition, direction == Direction.HORIZONTAL ? tempPosition : otherPosition).isEmpty()) {
			word.add(0, this.getBox(direction == Direction.HORIZONTAL ? otherPosition : tempPosition, direction == Direction.HORIZONTAL ? tempPosition : otherPosition));
			tempPosition--;
		}
	
		word.add(currentBox);
	
		tempPosition = currentPosition + 1;
		while (tempPosition <= SIZE && !this.getBox(direction == Direction.HORIZONTAL ? otherPosition : tempPosition, direction == Direction.HORIZONTAL ? tempPosition : otherPosition).isEmpty()) {
			word.add(this.getBox(direction == Direction.HORIZONTAL ? otherPosition : tempPosition, direction == Direction.HORIZONTAL ? tempPosition : otherPosition));
			tempPosition++;
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
					if (j < 10) {
						tokenDisplay = "  " + j.toString() + " ";
					} else {
						tokenDisplay = " " + j.toString() + " ";
					}
	        		
	        	} else {

					try {
						Box box = this.getBox(i, j);
	   
						if (box != null && box.token() != null) {
							tokenDisplay = box.token().display();
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
	            	line = line + " " +  tokenDisplay + " ┃";
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
