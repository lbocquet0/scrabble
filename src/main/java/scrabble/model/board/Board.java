package scrabble.model.board;

import java.util.ArrayList;

import scrabble.gui.console.Console;
import scrabble.model.token.Token;
import scrabble.utils.BoxIndexOutOfBoard;
import scrabble.utils.EmptyBoxException;

public class Board {
	
	public static int SIZE = 15;
	
	private ArrayList<ArrayList<Box>> boxes;

	private ActionHistory actionHistory;
	
	public Board() {
		this.boxes = new ArrayList<ArrayList<Box>>();
		this.actionHistory = new ActionHistory();

		for (int i = 1; i <= SIZE; i++) {
			ArrayList<Box> currentLine = new ArrayList<>();
			
			for (int j = 1; j <= SIZE; j++) {
				boolean isMiddle = (i == 8 && j ==8);
				currentLine.add(new Box(isMiddle, null));
			}
			this.boxes.add(currentLine);
		}
	}

	public Box getBox(Integer row, Integer column) throws BoxIndexOutOfBoard {
		if (row < 1 || row > SIZE || column < 1 || column > SIZE) {
			throw new BoxIndexOutOfBoard();
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

	public ArrayList<Token> cancelLastAction() {
		return this.actionHistory.undoAllActions();
	}

	public void clearHistory() {
		this.actionHistory.clear();
	}

	public void display() {
	    StringBuilder horizontalLine = new StringBuilder("");
	    for (int i = 0; i <= SIZE; i++) {
	        horizontalLine.append("------");
	    }
	    horizontalLine.append("-\n");

	    Console.message(horizontalLine.toString());

	    for (Integer i = 0; i <= SIZE; i++) {
	    	if (i < 10) {
	    		Console.message(i + "   ┃");
	    	} else {
	    		Console.message(i + "  ┃");
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
	                Console.message(tokenDisplay + " ┃");
	            } else {
	            	Console.message(tokenDisplay + "┃");
				}
	        }

	        Console.message("");

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
