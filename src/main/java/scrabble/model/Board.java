package scrabble.model;

import java.util.ArrayList;

public class Board {
	
	private static int SIZE = 15;
	
	private ArrayList<ArrayList<Box>> boxes;
	
	public boolean canReceivedLetter(int posX, int posY, Token token) {
		return true;
		//TODO
	}
	
	public int calculateScore(int posX, int posY, Token token) {
		return 1;
		//TODO
	}
	
	public Board() {
		boxes = new ArrayList<>();
		
		for (int i = 0; i < SIZE; i++) {
			ArrayList<Box> currentLine = new ArrayList<>();
			
			for (int j = 0; j < SIZE; j++) {
				if (i == 7 && j == 7) {
					currentLine.add(new Box(true, null));
				} else {
					currentLine.add(new Box(false, null));
				}
			}
		}
	}
}
