package scrabble.model.board.action;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import scrabble.model.board.Box;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.EmptyBoxException;

public class ActionHistoryTest {

	private ActionHistory actionHistory;
	private Action action;
	private Box box;
	private Token token;

	@BeforeEach
	void initialize() throws EmptyBoxException {
		this.actionHistory = new ActionHistory();
		token = new Token(FrenchLetter.A);

		box = new Box(false, token);
		action = new Action(0, 0, box);
	}
	
	@Test
	void should_be_empty_at_initialization() {
		ActionHistory actionHistory = new ActionHistory();
		
		Integer amount = actionHistory.amount();

		assertEquals(0, amount);
	}

	@Test
	void should_add_action() {
		actionHistory.add(action);

		Integer amount = actionHistory.amount();

		assertEquals(1, amount);
		assertThat(actionHistory.actions()).contains(action);
	}

	@Test
	void should_clear_actions() {
		actionHistory.add(action);
		actionHistory.clear();

		Integer amount = actionHistory.amount();

		assertEquals(0, amount);
	}

	@Test
	void should_undo_action() {
		Token undidToken = actionHistory.undoAction(action);

		ArrayList<Action> actions = actionHistory.actions();

		assertThat(actions).doesNotContain(action);
		assertEquals(token, undidToken);
	}

	@Test
	void should_undo_all_actions() throws EmptyBoxException {
		Token token2 = new Token(FrenchLetter.B);
		Box box2 = new Box(false, token2);

		Action action2 = new Action(1, 1, box2);

		actionHistory.add(action);
		actionHistory.add(action2);

		ArrayList<Token> undidTokens = actionHistory.undoAllActions();

		assertEquals(2, undidTokens.size());
		assertThat(undidTokens).contains(token, token2);
		assertThat(actionHistory.actions()).isEmpty();
	}

	@Test
    void should_undo_last_action() {
        actionHistory.add(action);
        Token undidToken = actionHistory.undoLastAction();

        assertEquals(token, undidToken);
        assertTrue(actionHistory.isEmpty());
    }

    @Test
    void should_return_null_when_undoing_last_action_from_empty_history() {
        Token undidToken = actionHistory.undoLastAction();

        assertNull(undidToken);
    }

	@Test
	public void should_return_null_on_get_first_action_when_empty() {
		Action firstAction = actionHistory.getFirstAction();

		assertNull(firstAction);
	}

	@Test
	public void should_return_null_on_get_action_when_doesnt_exists() {
		Action action = actionHistory.getAction(0);

		assertNull(action);
	}

	@Test
	public void should_return_null_on_get_action_is_higher_than_amount() {
		actionHistory.add(action);

		Action action = actionHistory.getAction(1);

		assertNull(action);
	}

	@Test
	public void should_return_action() {
		actionHistory.add(action);

		Action action = actionHistory.getAction(0);

		assertEquals(this.action, action);
	}
}
