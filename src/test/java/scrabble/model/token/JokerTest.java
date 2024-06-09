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
	void initialize() {
		joker = new Joker();
	}

	@Test
	void should_return_true_if_joker() {		
		boolean isJoker = joker.isJoker();

		assertTrue(isJoker);
	}

	@Test
	void test_describe_joker_when_doesnt_contains_letter() {
		String display = joker.display();

		assertEquals("JOK", display);
	}

	@Test
	void test_describe_joker_when_contains_letter() {
		FrenchLetter letter = FrenchLetter.A;
		joker.setLetter(letter);
		
		String display = joker.display();

		assertEquals(FrenchLetter.A.display(), display);
	}

	@Test
		void test_get_score_joker() {	
		Integer score = joker.getScore();

		assertEquals(0, score);
	}

	@Test
	void test_set_letter() {
		FrenchLetter letter = FrenchLetter.A;
		
		joker.setLetter(letter);
		
		FrenchLetter getLetterResult = joker.getLetter();
		assertEquals(letter, getLetterResult);
	}

	@Test
	void should_return_false_if_doesnt_contains_letter() {
		
		boolean haveLetter = joker.haveLetter();
		assertFalse(haveLetter);
	}
	
	@Test
	void should_return_true_if_contains_letter() {
		FrenchLetter letter = FrenchLetter.A;
		joker.setLetter(letter);
		
		boolean haveLetter = joker.haveLetter();
		assertTrue(haveLetter);
	}
	
	@Test
	void test_get_letter_null() {
		
		FrenchLetter letter = joker.getLetter();
		assertNull(letter);
	}
}
