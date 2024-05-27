import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scrabble.model.board.Board;
import scrabble.model.board.Box;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.ScoreCounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class ScoreCounterTest {

	private Token tokenA;
	private Board board;

	@BeforeEach
	public void initialize() {
		board = new Board();
		tokenA = new Token(FrenchLetter.A);
	}

	@Test
	public void shouldCountWordScore() {
		Box box = new Box(false, tokenA);
		Box box2 = new Box(false, tokenA);

		ArrayList<Box> wordLetters = new ArrayList<>();
		wordLetters.add(box);
		wordLetters.add(box2);

		Integer score = ScoreCounter.countWordScore(wordLetters);

		assertEquals(FrenchLetter.A.getPoint() * 2, score);
	}
}