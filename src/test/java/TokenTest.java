import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import scrabble.model.Joker;
import scrabble.model.FrenchLetter;
import scrabble.model.Token;

public class TokenTest {

	@Test
	public void testIsJokerFalse() {
		Token token = new Token(FrenchLetter.A);
		assertEquals(false, token.isJoker());
	}

	@Test
	public void testIsJokerTrue() {
		Token token = new Joker();
		assertEquals(true, token.isJoker());
	}

	@Test
	public void testDescribeNoJoker() {
		Token token = new Token(FrenchLetter.A);
		assertEquals("A 1", token.display());
	}

	@Test
	public void testDescribeJoker() {
		Token token = new Joker();
		assertEquals(" ", token.display());
	}

	@Test
	public void testGetLetter() {
		Token token = new Token(FrenchLetter.A);
		assertEquals(FrenchLetter.A, token.getLetter());
	}
}
