package scrabble.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.IntegerProperty;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.TokenDoesntExists;

public class PlayerTest {

	private Player player;

	@BeforeEach
	public void initialize() {
		player = new Player();
	}

	@Test
	public void rack_should_be_empty_at_initialize() {
		Rack rack = player.rack();

		assertTrue(rack.isEmpty());
	}

	@Test
	public void should_return_zero_when_empty() {
		Integer remainingTokens = player.remainingTokenInRack();

		assertEquals(0, remainingTokens);
	}

	@Test
	public void should_add_token_to_rack() {
		Token token = new Token(FrenchLetter.A);

		player.addTokenToRack(token);

		Integer remainingTokens = player.remainingTokenInRack();
		assertEquals(1, remainingTokens);
	}

	@Test
	void should_remove_token_from_rack() {
		Token token = new Token(FrenchLetter.A);
		
		player.addTokenToRack(token);

		try {
			assertTrue(player.removeTokenFromRack(token));
		} catch (TokenDoesntExists e) {
			fail(e);
		}
		
		Integer remainingTokens = player.remainingTokenInRack();
		assertEquals(0, remainingTokens);
	}

	@Test
	void should_throw_error_when_attempt_to_remove_no_existing_token() throws TokenDoesntExists {
		Token token = new Token(FrenchLetter.A);
		Token token2 = new Token(FrenchLetter.B);
		
		player.addTokenToRack(token);

		assertThrows(TokenDoesntExists.class, () -> {
			player.removeTokenFromRack(token2);
		});

		Integer remainingTokens = player.remainingTokenInRack();
		assertEquals(1, remainingTokens);
	}

	@Test
	void should_return_true_if_empty() {
		boolean rackIsEmpty = player.rackIsEmpty();
		assertTrue(rackIsEmpty);
	}

	@Test
	void should_return_the_token_position() {
		Token token = new Token(FrenchLetter.A);
		player.addTokenToRack(token);

		Integer position = player.getTokenRackPosition(token);
		assertEquals(1, position);
	}

	@Test
	void should_return_0_score_at_initialize() {
		assertEquals(0, player.score());
	}

	@Test
	void should_add_score() {
		Integer score = 10;
		Integer newScore = player.addScore(score);

		assertEquals(10, newScore);
	}
	
	@Test
	void should_return_score_property() {
		IntegerProperty scoreProperty = player.scoreProperty();

		assertNotNull(scoreProperty);
	}

	@Test
	void should_return_initial_score_value() {
		IntegerProperty scoreProperty = player.scoreProperty();

		assertEquals(0, scoreProperty.get());
	}

	@Test
	void should_update_score_property() {
		IntegerProperty scoreProperty = player.scoreProperty();
		player.addScore(10);

		assertEquals(10, scoreProperty.get());
	}
}
