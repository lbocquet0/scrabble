package scrabble.model.board;

import java.util.ArrayList;

import scrabble.gui.console.Console;
import scrabble.model.board.action.Action;
import scrabble.model.board.action.ActionHistory;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.Position;
import scrabble.utils.exceptions.PositionOutOfBoard;
import scrabble.utils.exceptions.EmptyBoxException;
import scrabble.utils.exceptions.IllegalMoveException;
import scrabble.utils.exceptions.WordNotFoundException;

public class Board {
	
	public static int SIZE = 15;
	private static Position[] TRIPLE_WORD_POSITIONS = {
		new Position(1, 1), new Position(1, 8), new Position(1, 15),
		new Position(8, 1), new Position(8, 15),
		new Position(15, 1), new Position(15, 8), new Position(15, 15)
	};

	private static Position[] DOUBLE_WORD_POSITIONS = {
		new Position(2, 2), new Position(2, 14),
		new Position(3, 3), new Position(3, 13),
		new Position(4, 4), new Position(4, 12),
		new Position(5, 5), new Position(5, 11),
		new Position(11, 5), new Position(11, 11),
		new Position(12, 4), new Position(12, 12),
		new Position(13, 3), new Position(13, 13),
		new Position(14, 2), new Position(14, 14)
	};

	private static Position[] DOUBLE_LETTER_POSITIONS = {
		new Position(1, 4), new Position(1, 12),
		new Position(3, 7), new Position(3, 9),
		new Position(4, 1), new Position(4, 8), new Position(4, 15),
		new Position(7, 3), new Position(7, 7), new Position(7, 9), new Position(7, 13),
		new Position(8, 4), new Position(8, 12),
		new Position(9, 3), new Position(9, 7), new Position(9, 9), new Position(9, 13),
		new Position(12, 1), new Position(12, 8), new Position(12, 15),
		new Position(13, 7), new Position(13, 9),
		new Position(15, 4), new Position(15, 12)
	};

	private static Position[] TRIPLE_LETTER_POSITIONS = {
		new Position(2, 6), new Position(2, 10),
		new Position(6, 2), new Position(6, 6), new Position(6, 10), new Position(6, 14),
		new Position(10, 2), new Position(10, 6), new Position(10, 10), new Position(10, 14),
		new Position(14, 6), new Position(14, 10)
	};
	
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
				Effect effect = getEffectAtPosition(i, j);

				currentLine.add(new Box(isMiddle, null, effect));
			}
			this.boxes.add(currentLine);
		}
	}

	public ArrayList<ArrayList<Box>> boxes() {
		return this.boxes;
	}

	public Boolean gameHaveNotStarted() throws PositionOutOfBoard {
        return this.getBox(8, 8).isEmpty();
	}
	
	public Boolean isMiddleBoxInActionHistory() {
		return this.actionHistory.positionIsInActions(8, 8);
	}

	public Box getBox(Integer row, Integer column) throws PositionOutOfBoard {
		if (!this.isOnBoard(row, column)) {
			throw new PositionOutOfBoard(row, column);
		}

		return this.boxes.get(row-1).get(column-1);
	}

	public Token getToken(Integer row, Integer column) throws PositionOutOfBoard {
		Box box = this.getBox(row, column);

		return box.token();
	}
	
	public void setToken(Token token, Integer row, Integer column) throws PositionOutOfBoard, EmptyBoxException, IllegalMoveException {
		Box box = this.getBox(row, column);
		if (this.gameHaveNotStarted() && !box.isMiddle()) {
			throw new IllegalMoveException();
		}
		
		box.setToken(token);
		this.actionHistory.add(new Action(row, column, box));
	}

	public Boolean hasAlreadyPlayedLetterAround(Integer row, Integer column) throws PositionOutOfBoard{
		Boolean result = false;

		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (isOnBoard(i, j)) {
					if (i != row || j != column) {
						if (isOnSide(row, column, i, j)) {
							if (!this.actionHistory.positionIsInActions(i, j)) {
								if (!this.getBox(i, j).isEmpty()) {
									result = true;
								}
							}
						}
					}
				}
			}
		}

		return result;
	}

	private boolean isOnBoard(int row, int column) {
		return row >= 1 && row <= SIZE && column >= 1 && column <= SIZE;
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

	public ActionHistory getActionsHistory() {
		return this.actionHistory;
	}

	public ArrayList<Box> getWord(Integer row, Integer column, Direction direction) throws PositionOutOfBoard, WordNotFoundException {
		if (!this.isOnBoard(row, column)) {
			throw new PositionOutOfBoard(row, column);
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

	private boolean isTripleWordPosition(Position position) {
		for (Position tripleWordPosition : TRIPLE_WORD_POSITIONS) {
			if (tripleWordPosition.equals(position)) {
				return true;
			}
		}

		return false;
	}

	private boolean isDoubleWordPosition(Position position) {
		for (Position doubleWordPosition : DOUBLE_WORD_POSITIONS) {
			if (doubleWordPosition.equals(position)) {
				return true;
			}
		}

		return false;
	}

	private boolean isTripleLetterPosition(Position position) {
		for (Position tripleLetterPosition : TRIPLE_LETTER_POSITIONS) {
			if (tripleLetterPosition.equals(position)) {
				return true;
			}
		}

		return false;
	}

	private boolean isDoubleLetterPosition(Position position) {
		for (Position doubleLetterPosition : DOUBLE_LETTER_POSITIONS) {
			if (doubleLetterPosition.equals(position)) {
				return true;
			}
		}

		return false;
	}

	public Effect getEffectAtPosition(Integer row, Integer column) {
		Position position = new Position(row, column);

		if (isTripleWordPosition(position)) {
			return Effect.TRIPLE_WORD;
		} else if (isDoubleWordPosition(position)) {
			return Effect.DOUBLE_WORD;
		} else if (isTripleLetterPosition(position)) {
			return Effect.TRIPLE_LETTER;
		} else if (isDoubleLetterPosition(position)) {
			return Effect.DOUBLE_LETTER;
		}

		return Effect.NORMAL;
	}

	public boolean isAlreadyPlayedLetterAroundActions() throws PositionOutOfBoard {
		Boolean result = false;

		for (Action action : this.actionHistory.actions()) {
			Integer row = action.getRowPosition();
			Integer column = action.getColumnPosition();

			if (!result) {
				result = this.hasAlreadyPlayedLetterAround(row, column);
			}
		}

		return result;
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
					} catch (PositionOutOfBoard e) {
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