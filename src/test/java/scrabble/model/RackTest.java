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
	void initialize() {
		rack = new Rack();
	}

	@Test
	void tokens_list_should_be_empty() {
		assertThat(rack.tokens()).isEmpty();
	}

	@Test
	void remaining_tokens_should_return_0_when_empty() {
		int remainingTokens = rack.remainingTokens();

		assertEquals(0, remainingTokens);
	}

	@Test
	void remaining_tokens_should_return_tokens_count_when_not_empty() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);

		int remainingTokens = rack.remainingTokens();

		assertEquals(1, remainingTokens);
	}

	@Test
	void should_return_the_token_from_the_index() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);

		Token rackToken = rack.token(0);
		assertEquals(token, rackToken);
	}

	@Test
	void should_remove_the_token() throws TokenDoesntExists {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);

		boolean tokenRemoved = rack.removeToken(token);
		assertTrue(tokenRemoved);

		int remainingTokens = rack.remainingTokens();
		assertEquals(0, remainingTokens);
	}

	@Test
	void should_throw_exception_when_remove_invalid_token() {
		Token token = new Token(FrenchLetter.A);

		assertThrows(TokenDoesntExists.class, () -> rack.removeToken(token));
	}

	@Test
	void should_add_token_in_the_rack() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);

		assertThat(rack.tokens()).containsExactly(token);
	}

	@Test
	void is_empty_should_return_true_when_empty() {
		boolean isEmpty = rack.isEmpty();
		assertTrue(isEmpty);
	}

	@Test
	void is_empty_should_return_false_when_not_empty() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);

		boolean isEmpty = rack.isEmpty();
		assertFalse(isEmpty);
	}

	@Test
	void should_return_the_token_position_if_exists() {
		Token token = new Token(FrenchLetter.A);

		rack.addToken(token);

		int tokenIndex = rack.getTokenPosition(token);
		assertEquals(1, tokenIndex);
	}

	@Test
	void should_return_minus_1_when_token_doesnt_exist() {
		Token token = new Token(FrenchLetter.A);

		int tokenIndex = rack.getTokenPosition(token);
		assertEquals(-1, tokenIndex);
	}

	@Test
	void should_swap_tokens() throws TokenDoesntExists {
		Token tokenA = new Token(FrenchLetter.A);
		Token tokenB = new Token(FrenchLetter.B);

		rack.addToken(tokenA);
		rack.addToken(tokenB);

		rack.swapTokens(tokenA, tokenB);

		assertThat(rack.tokens()).containsExactly(tokenB, tokenA);
	}

	@Test
	void should_throw_exception_when_swap_invalid_tokens() {
		Token tokenA = new Token(FrenchLetter.A);
		Token tokenB = new Token(FrenchLetter.B);

		assertThrows(TokenDoesntExists.class, () -> rack.swapTokens(tokenA, tokenB));
	}
}
