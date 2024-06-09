package scrabble.model.token;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JokerTest {

	Joker joker = new Joker();
	@BeforeEach
	public void initialize() {
		joker = new Joker();
	}

	@Test
	public void shouldReturnTrueIfJoker() {		
		boolean isJoker = joker.isJoker();

		assertTrue(isJoker);
	}

	@Test
	public void testDescribeJokerWhenDoesntContainsLetter() {
		String display = joker.display();

		assertEquals("JOK", display);
	}

	@Test
	public void testDescribeJokerWhenContainsLetter() {
		FrenchLetter letter = FrenchLetter.A;
		joker.setLetter(letter);
		
		String display = joker.display();

		assertEquals(FrenchLetter.A.display(), display);
	}

	@Test
	public void testGetScoreJoker() {	
		Integer score = joker.getScore();

		assertEquals(0, score);
	}

	@Test
	public void testSetLetter() {
		FrenchLetter letter = FrenchLetter.A;
		
		joker.setLetter(letter);
		
		FrenchLetter getLetterResult = joker.getLetter();
		assertEquals(letter, getLetterResult);
	}

	@Test
	public void shouldReturnFalseIfDoesntContainsLetter() {
		
		boolean haveLetter = joker.haveLetter();
		assertFalse(haveLetter);
	}
	
	@Test
	public void shouldReturnTrueIfContainsLetter() {
		FrenchLetter letter = FrenchLetter.A;
		joker.setLetter(letter);
		
		boolean haveLetter = joker.haveLetter();
		assertTrue(haveLetter);
	}
	
	@Test
	public void testGetLetterNull() {
		
		FrenchLetter letter = joker.getLetter();
		assertNull(letter);
	}
}