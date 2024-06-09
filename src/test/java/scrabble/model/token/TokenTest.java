package scrabble.model.token;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TokenTest {

	@Test
	void test_is_joker_false() {
		Token token = new Token(FrenchLetter.A);
		
		assertEquals(false, token.isJoker());
	}

	@Test
	void test_describe_no_joker() {
		Token token = new Token(FrenchLetter.A);

		assertEquals("A 1", token.display());
	}

	@Test
	void test_get_letter() {
		Token token = new Token(FrenchLetter.A);
		
		assertEquals(FrenchLetter.A, token.getLetter());
	}

	@Test
	void test_get_score() {
		Token token = new Token(FrenchLetter.A);
	
		assertEquals(1, token.getScore());
	}
}
