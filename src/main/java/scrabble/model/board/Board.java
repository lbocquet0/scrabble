package scrabble.model.board;

import java.util.HashMap;

import scrabble.model.Token;

public class Board {
	
	private static int SIZE = 15;
	
	private HashMap<Position, Box> boxes;
	
	public Board() {
		this.boxes = new HashMap<>();
		
		for (int i = 1; i < SIZE; i++) {
			for (int j = 1; j < SIZE; j++) {
				Position position = new Position(i, j);
				
				boolean isMiddle = position.getX() == 8 && position.getY() == 8;
				Box box = new Box(isMiddle, null);

				this.boxes.put(position, box);
			}
		}
	}
	
	public void display() {
	    StringBuilder horizontalLine = new StringBuilder("");
	    for (int i = 0; i < SIZE; i++) {
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
	        		Position position = new Position(i, j);
		            Box box = this.boxes.get(position);
   
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
	    for (int i = 0; i < SIZE; i++) {
	        bottomHorizontalLine.append("------");
	    }
	    bottomHorizontalLine.append("-\n");
	    System.out.print(bottomHorizontalLine);
	}


	
	public void setToken(Position pos, Token token) {
		Box box = this.boxes.get(pos);
		
		box.setToken(token);
	}
}