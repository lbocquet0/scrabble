package scrabble.model;

import java.util.HashMap;

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
	    for (int i = 1; i < SIZE; i++) {
	        horizontalLine.append("------");
	    }
	    horizontalLine.append("-\n");

	    System.out.print(horizontalLine);

	    for (int i = 1; i <= SIZE; i++) {
	        System.out.print("┃");
	        for (int j = 1; j <= SIZE; j++) {
	            Position position = new Position(i, j);
	            Box box = this.boxes.get(position);

	            String tokenDisplay = "    ";
	            if (box != null && box.getToken() != null) {
	                tokenDisplay = box.getToken().display();
	            } else {
	            	if (box != null && box.isMiddle()) {
	            		tokenDisplay = "  ⭐ ";
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
	    for (int i = 1; i < SIZE; i++) {
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
