package scrabble.model;

import java.util.HashMap;

public class Board {
	
	private static int SIZE = 15;
	
	private HashMap<Position, Box> boxes;
	
	public Board() {
		this.boxes = new HashMap<>();
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Position position = new Position(i, j);
				
				boolean isMiddle = position.getX() == 7 && position.getY() == 7;
				Box box = new Box(isMiddle, null);

				this.boxes.put(position, box);
			}
		}
	}
	
	public static void display() {
	    final int NB_LIGNES = 15;
	    final int NB_COLONNES = 15;

	    System.out.print("┏");
	    for (int i = 0; i < NB_COLONNES; i++) {
	        System.out.print("━━━━━┳");
	    }
	    System.out.println("━━━━━┓");

	    for (int i = 0; i < NB_LIGNES; i++) {
	        System.out.print("┣");
	        for (int j = 0; j < NB_COLONNES; j++) {
	            System.out.print("━━━━━╋");
	        }
	        System.out.println("━━━━━┫");
	    }

	    System.out.print("┗");
	    for (int i = 0; i < NB_COLONNES; i++) {
	        System.out.print("━━━━━┻");
	    }
	    System.out.println("━━━━━┛");
	}
}
