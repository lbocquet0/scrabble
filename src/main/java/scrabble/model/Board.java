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
				
				boolean isMiddle = position.getX() == 7 && position.getY() == 7;
				Box box = new Box(isMiddle, null);

				this.boxes.put(position, box);
			}
		}
	}
	
	public void display() {
	    System.out.print("┏");
	    for (int i = 1; i < SIZE; i++) {
	        System.out.print("━━━━━┳");
	    }
	    Console.message("━━━━━┓");

	    for (int i = 1; i < SIZE; i++) {
	        System.out.print("┃");
	        for (int j = 1; j < SIZE; j++) {
	            Position position = new Position(i, j);
	            Box box = this.boxes.get(position);
	            String tokenDisplay = " ";
	            if (box != null && box.getToken() != null) {
	                tokenDisplay = box.getToken().display(); 
	            }
	            System.out.printf("  %s  ┃", tokenDisplay);
	        }
	        Console.message("");
	        if (i < SIZE - 1) {
	            System.out.print("┣");
	            for (int j = 1; j < SIZE; j++) {
	                System.out.print("━━━━━╋");
	            }
	            Console.message("━━━━━┫");
	        }
	    }

	    System.out.print("┗");
	    for (int i = 0; i < SIZE; i++) {
	        System.out.print("━━━━━┻");
	    }
	    Console.message("━━━━━┛");
	}
	
	public void setToken(Position pos, Token token) {
		Box box = this.boxes.get(pos);
		
		box.setToken(token);
	}
}
