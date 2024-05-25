import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import scrabble.model.board.Board;
import scrabble.model.board.Box;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.exceptions.WordNotFoundException;

public class BoardTest {
	Board board;
	Token token;

	Box box2_1;
	Box box2_2;
	Box box2_3;

	Box box1_2;
	Box box3_2;

	Box box5_5;

	@BeforeEach
	public void init() {
		board = new Board();
		token = new Token(FrenchLetter.A);

		try {
			box2_1 = board.getBox(2, 1);
			box2_2 = board.getBox(2, 2);
			box2_3 = board.getBox(2, 3);

			board.setToken(token, 2, 1);
			board.setToken(token, 2, 2);
			board.setToken(token, 2, 3);
	
			box1_2 = board.getBox(1, 2);
			box3_2 = board.getBox(3, 2);

			board.setToken(token, 1, 2);
			board.setToken(token, 3, 2);

			box5_5 = board.getBox(5, 5);

			board.setToken(token, 5, 5);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	public void testNoWordFoundIfBoxIsEmpty() {

		assertThrows(WordNotFoundException.class, () -> {
			board.getWord(3, 3, Direction.HORIZONTAL);
		});
	}

	@Test
	public void shouldDetectLeftWord() {
		try {
			assertThat(board.getWord(2, 3, Direction.HORIZONTAL)).containsExactlyInAnyOrder(box2_1, box2_2, box2_3);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	public void shouldDetectRightWord() {
		try {
			assertThat(board.getWord(2, 1, Direction.HORIZONTAL)).containsExactlyInAnyOrder(box2_1, box2_2, box2_3);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	public void shouldDetectLeftAndRightWord() {
		try {
			assertThat(board.getWord(2, 2, Direction.HORIZONTAL)).containsExactlyInAnyOrder(box2_1, box2_2, box2_3);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	public void shouldDetectTopWord() {
		try {
			assertThat(board.getWord(3, 2, Direction.VERTICAL)).containsExactlyInAnyOrder(box3_2, box2_2, box1_2);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	public void shouldDetectBottomWord() {
		try {
			assertThat(board.getWord(1, 2, Direction.VERTICAL)).containsExactlyInAnyOrder(box1_2, box2_2, box3_2);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	public void shouldDetectTopAndBottomWord() {
		try {
			assertThat(board.getWord(2, 2, Direction.VERTICAL)).containsExactlyInAnyOrder(box1_2, box2_2, box3_2);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	public void shouldThrowErrorWhenSingleLetterWord() {
		assertThrows(WordNotFoundException.class, () -> {
			board.getWord(5, 5, Direction.HORIZONTAL);
		});
	}
}