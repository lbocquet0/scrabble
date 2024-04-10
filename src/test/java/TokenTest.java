import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import scrabble.model.Joker;
import scrabble.model.Letter;
import scrabble.model.Token;

public class TokenTest {

	@Test
	public void testIsJokerFalse() {
		Token token = new Token(Letter.A);
		assertEquals(false, token.isJoker());
	}

	@Test
	public void testIsJokerTrue() {
		Token token = new Joker();
		assertEquals(true, token.isJoker());
	}

	@Test
	public void testDescribeNoJoker() {
		Token token = new Token(Letter.A);
		assertEquals("A (1)", token.describe());
	}

	@Test
	public void testDescribeJoker() {
		Token token = new Token(null);
		assertEquals("Joker", token.describe());
	}
}
