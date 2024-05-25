package scrabble.model.token;

public class Joker extends Token {
	public static int OCCURRENCES_AMOUNT = 2;

	public Joker() {
		super(null);
	}

	@Override
	public boolean isJoker() {
		return true;
	}

	@Override
	public String display() {
		if(this.haveLetter()) {
			return letter.display();
		}else {
			return "JOK";
		}
	}

	@Override
	public Integer getScore() {
		return 0;
	}

	public void setLetter(FrenchLetter letter) {
		this.letter = letter;
	}

	public boolean haveLetter() {
		return (this.letter != null);
	}
}
