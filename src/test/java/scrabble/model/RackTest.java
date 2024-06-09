package scrabble.model;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.TokenDoesntExists;

public class RackTest {

	private Rack rack;

	@BeforeEach
	public void initialize() {
		rack = new Rack();
	}

	@Test
	public void tokensListShouldBeEmpty() {
		assertThat(rack.tokens()).isEmpty();
	}

	@Test
	public void remaningTokensShouldReturn0WhenEmpty() {
		int remainingTokens = rack.remainingTokens();

		assertEquals(0, remainingTokens);
	}

	@Test
	public void remaningTokensShouldReturnTokensCountWhenNotEmpty() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);

		int remainingTokens = rack.remainingTokens();

		assertEquals(1, remainingTokens);
	}

	@Test
	public void shouldReturnTheTokenFromTheIndex() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);
		
		Token rackToken = rack.token(0);
		assertEquals(token, rackToken);
	}

	@Test
	public void shouldRemoveTheToken() throws TokenDoesntExists {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);
		
		boolean tokenRemoved = rack.removeToken(token);
		assertTrue(tokenRemoved);
		
		int remainingTokens = rack.remainingTokens();
		assertEquals(0, remainingTokens);
	}

	@Test
	public void shouldThrowExceptionWhenRemoveInvalidToken() {
		Token token = new Token(FrenchLetter.A);

		assertThrows(TokenDoesntExists.class, () -> rack.removeToken(token));
	}

	@Test
	public void shouldAddTokenInTheRack() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);
		
		assertThat(rack.tokens()).containsExactly(token);
	}

	@Test
	public void isEmptyShouldReturnTrueWhenEmpty() {
		boolean isEmpty = rack.isEmpty();
		assertTrue(isEmpty);
	}

	@Test
	public void isEmptyShouldReturnFalseWhenNotEmpty() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);
		
		boolean isEmpty = rack.isEmpty();
		assertFalse(isEmpty);
	}

	@Test
	public void shouldReturnTheTokenPositionIfExists() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);
		
		int tokenIndex = rack.getTokenPosition(token);
		assertEquals(1, tokenIndex);
	}

	@Test
	public void shouldReturnMinus1WhenTokenDoesntExists() {
		Token token = new Token(FrenchLetter.A);

		int tokenIndex = rack.getTokenPosition(token);
		assertEquals(-1, tokenIndex);
	}

	@Test
	public void shouldSwapTokens() throws TokenDoesntExists {
		Token tokenA = new Token(FrenchLetter.A);
		Token tokenB = new Token(FrenchLetter.B);

		rack.addToken(tokenA);
		rack.addToken(tokenB);

		rack.swapTokens(tokenA, tokenB);

		assertThat(rack.tokens()).containsExactly(tokenB, tokenA);
	}

	@Test
	public void shouldThrowExceptionWhenSwapInvalidTokens() {
		Token tokenA = new Token(FrenchLetter.A);
		Token tokenB = new Token(FrenchLetter.B);

		assertThrows(TokenDoesntExists.class, () -> rack.swapTokens(tokenA, tokenB));
	}
}