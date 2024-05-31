
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
	void testGetRowPosition() {
		Integer rowPosition = action.getRowPosition();
		
		assertEquals(ROW, rowPosition);
	}

	@Test
	void testGetColumnPosition() {
		Integer columnPosition = action.getColumnPosition();
		
		assertEquals(COLUMN, columnPosition);
	}

	@Test
	void testGetBox() {
		Box box = action.getBox();
		
		assertEquals(box, box);
	}

	@Test
	void testGetToken() {
		Token token = action.getToken();
		
		assertEquals(token, token);
	}

	@Test
	void shouldThrowEmptyBoxExceptionWhenTokenIsNull() {
		Box emptyBox = new Box(false, null);
		
		assertThrows(EmptyBoxException.class, () -> new Action(ROW, COLUMN, emptyBox));
	}
}