import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scrabble.model.Player;
import scrabble.model.Rack;
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
	public void rackShouldBeEmptyAtInitialize() {
		Rack rack = player.getRack();

		assertTrue(rack.isEmpty());
	}

	@Test
	public void shouldReturn0WhenEmpty() {
		Integer remainingTokens = player.remainingTokenInRack();

		assertEquals(0, remainingTokens);
	}

	@Test
	public void shouldAddTokenToRack() {
		Token token = new Token(FrenchLetter.A);

		player.addTokenToRack(token);

		Integer remainingTokens = player.remainingTokenInRack();
		assertEquals(1, remainingTokens);
	}

	@Test
	public void shouldRemoveTokenFromRack() {
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
	public void shouldThrowErrorWhenAttemptToRemoveNoExistingToken() throws TokenDoesntExists {
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
	public void shouldReturnTrueIfEmpty() {
		boolean rackIsEmpty = player.rackIsEmpty();
		assertTrue(rackIsEmpty);
	}

	@Test
	public void shouldReturnTheTokenPosition() {
		Token token = new Token(FrenchLetter.A);
		player.addTokenToRack(token);

		Integer position = player.getTokenRackPosition(token);
		assertEquals(1, position);
	}

	@Test
	public void shouldReturn0ScoreAtInitialize() {
		assertEquals(0, player.getScore());
	}

	@Test
	public void shouldAddScore() {
		Integer score = 10;
		Integer newScore = player.addScore(score);

		assertEquals(10, newScore);
	}
}