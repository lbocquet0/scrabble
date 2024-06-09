package scrabble.model.board.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scrabble.model.board.Box;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.EmptyBoxException;

public class ActionTest {
	
	private static Integer ROW = 0;
	private static Integer COLUMN = 0;

	private Token token;
	private Box box;
	private Action action;

	@BeforeEach
	void initialize() throws EmptyBoxException {
		token = new Token(FrenchLetter.A);
		box = new Box(false, token);
	
		action = new Action(ROW, COLUMN, box);
	}

	@Test
	void test_get_row_position() {
		Integer rowPosition = action.row();
		
		assertEquals(ROW, rowPosition);
	}

	@Test
	void test_get_column_position() {
		Integer columnPosition = action.column();
		
		assertEquals(COLUMN, columnPosition);
	}

	@Test
	void test_get_box() {
		Box box = action.box();
		
		assertEquals(box, box);
	}

	@Test
	void test_get_token() {
		Token token = action.token();
		
		assertEquals(token, token);
	}

	@Test
	void should_throw_empty_box_exception_when_token_is_null() {
		Box emptyBox = new Box(false, null);
		
		assertThrows(EmptyBoxException.class, () -> new Action(ROW, COLUMN, emptyBox));
	}
}