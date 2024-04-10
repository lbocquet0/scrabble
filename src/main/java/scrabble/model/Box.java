package scrabble.model;

public class Box {

	private boolean middle;
	private Token token;
	
	public String describe() {
		if(token == null)
		{
			return "";
		}
		
		return token.describe();
	}
}
