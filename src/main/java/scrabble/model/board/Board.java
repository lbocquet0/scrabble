package scrabble.model.board;

import java.util.ArrayList;
import scrabble.model.token.Token;
import scrabble.model.board.Action;
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
	
	public void display() {
	    StringBuilder horizontalLine = new StringBuilder("");
	    for (int i = 0; i <= SIZE; i++) {
	        horizontalLine.append("------");
	    }
	    horizontalLine.append("-\n");

	    System.out.print(horizontalLine);

	    for (Integer i = 0; i <= SIZE; i++) {
	    	if (i < 10) {
	    		System.out.print(i + "   ┃");
	    	} else {
	    		System.out.print(i + "  ┃");
	    	}
	       
	        for (Integer j = 1; j <= SIZE; j++) {
	        	String tokenDisplay = "    ";
	        	if (i == 0) {
	        		tokenDisplay = "  " + j.toString() + " ";
	        		
	        	} else {
		            Box box = this.boxes.get(i-1).get(j-1);
   
		            if (box != null && box.getToken() != null) {
		                tokenDisplay = box.getToken().display();
		            } else {
		            	if (box != null && box.isMiddle()) {
		            		tokenDisplay = "  ⭐ ";
		            	}
		            }
	        		
	        	}
	        	
	            

	            if (tokenDisplay.length() == 4) {
	                System.out.printf("%s ┃", tokenDisplay);
	            } else {
	                System.out.printf("%s┃", tokenDisplay);
	            }
	        }

	        System.out.println();

	        if (i != SIZE) {
	            System.out.print(horizontalLine);
	        }
	    }

	    StringBuilder bottomHorizontalLine = new StringBuilder("");
	    for (int i = 0; i <= SIZE; i++) {
	        bottomHorizontalLine.append("------");
	    }
	    bottomHorizontalLine.append("-\n");
	    System.out.print(bottomHorizontalLine);
	}


	public Token getToken(Integer i, Integer j) {
		Box box = this.boxes.get(i-1).get(j-1);

		return box.getToken();
	}
	
	public void setToken(Token token, Integer i, Integer j) throws EmptyBoxException {
		Box box = this.boxes.get(i-1).get(j-1);
		
		box.setToken(token);
		this.actionHistory.add(new Action(i, j, box));
	}
}
