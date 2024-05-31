package scrabble.model.token;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TokenTest {

	@Test
	public void testIsJokerFalse() {
		Token token = new Token(FrenchLetter.A);
		
		assertEquals(false, token.isJoker());
	}

	@Test
	public void testDescribeNoJoker() {
		Token token = new Token(FrenchLetter.A);

		assertEquals("A 1", token.display());
	}

	@Test
	public void testGetLetter() {
		Token token = new Token(FrenchLetter.A);
		
		assertEquals(FrenchLetter.A, token.getLetter());
	}

	@Test
	public void testGetScore() {
		Token token = new Token(FrenchLetter.A);
	
		assertEquals(1, token.getScore());
	}
}
