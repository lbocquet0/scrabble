package scrabble.model.board;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import scrabble.model.board.action.ActionHistory;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.Position;
import scrabble.utils.exceptions.EmptyBoxException;
import scrabble.utils.exceptions.IllegalMoveException;
import scrabble.utils.exceptions.PositionOutOfBoard;
import scrabble.utils.exceptions.WordNotFoundException;

class BoardTest {
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
			board.placeToken(token, 8, 8);
			board.clearHistory();

			box2_1 = board.getBox(2, 1);
			box2_2 = board.getBox(2, 2);
			box2_3 = board.getBox(2, 3);

			board.placeToken(token, 2, 1);
			board.placeToken(token, 2, 2);
			board.placeToken(token, 2, 3);

			box1_2 = board.getBox(1, 2);
			box3_2 = board.getBox(3, 2);

			board.placeToken(token, 1, 2);
			board.placeToken(token, 3, 2);

			box5_5 = board.getBox(5, 5);

			board.placeToken(token, 5, 5);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_contains_boxes_at_initialize() {
		ArrayList<ArrayList<Box>> boxes = board.boxes();

		assertThat(boxes).hasSize(Board.SIZE);

		for (ArrayList<Box> line : boxes) {
			assertThat(line).hasSize(Board.SIZE);
		}
	}

	@Test
	void should_get_box() {
		try {
			Box box = board.getBox(1, 1);

			assertThat(box).isNotNull();
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_throw_exception_when_row_is_out_of_board() {

		assertThrows(PositionOutOfBoard.class, () -> {
			board.getBox(Board.SIZE + 1, 1);
		});

		assertThrows(PositionOutOfBoard.class, () -> {
			board.getBox(0, 1);
		});
	}

	@Test
	void should_throw_exception_when_column_is_out_of_board() {

		assertThrows(PositionOutOfBoard.class, () -> {
			board.getBox(1, Board.SIZE + 1);
		});

		assertThrows(PositionOutOfBoard.class, () -> {
			board.getBox(1, 0);
		});
	}

	@Test
	void should_return_token_when_box_is_not_empty() {
		try {
			Token boxToken = board.getToken(2, 2);

			assertEquals(token, boxToken);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_return_null_when_box_is_empty() {

		try {
			Token boxToken = board.getToken(6, 6);

			assertEquals(null, boxToken);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void test_no_word_found_if_box_is_empty() {

		assertThrows(WordNotFoundException.class, () -> {
			board.getWord(3, 3, Direction.HORIZONTAL);
		});
	}

	@Test
	void should_detect_left_word() {
		try {
			assertThat(board.getWord(2, 3, Direction.HORIZONTAL)).containsExactlyInAnyOrder(box2_1, box2_2, box2_3);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_detect_right_word() {
		try {
			assertThat(board.getWord(2, 1, Direction.HORIZONTAL)).containsExactlyInAnyOrder(box2_1, box2_2, box2_3);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_detect_left_and_right_word() {
		try {
			assertThat(board.getWord(2, 2, Direction.HORIZONTAL)).containsExactlyInAnyOrder(box2_1, box2_2, box2_3);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_detect_top_word() {
		try {
			assertThat(board.getWord(3, 2, Direction.VERTICAL)).containsExactlyInAnyOrder(box3_2, box2_2, box1_2);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_detect_bottom_word() {
		try {
			assertThat(board.getWord(1, 2, Direction.VERTICAL)).containsExactlyInAnyOrder(box1_2, box2_2, box3_2);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_detect_top_and_bottom_word() {
		try {
			assertThat(board.getWord(2, 2, Direction.VERTICAL)).containsExactlyInAnyOrder(box1_2, box2_2, box3_2);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void should_throw_error_when_single_letter_word() {
		assertThrows(WordNotFoundException.class, () -> {
			board.getWord(5, 5, Direction.HORIZONTAL);
		});
	}

	@Test
	void should_clear_history() {
		Token token = new Token(FrenchLetter.A);

		try {
			board.placeToken(token, 6, 6);
		} catch (Exception e) {
			fail(e);
		}

		board.clearHistory();

		assertTrue(board.getActionsHistory().isEmpty());
	}

	@Test
	void should_cancel_last_word() {

		ArrayList<Token> tokens = board.cancelLastWord();

		assertThat(tokens).hasSize(6);
		assertThat(tokens.get(0)).isEqualTo(token);
	}

	@Test
	void test_has_already_played_letter_around_left() throws PositionOutOfBoard, EmptyBoxException, IllegalMoveException {

		ActionHistory actionHistory = board.getActionsHistory();

		board.placeToken(token, 4, 4);
		actionHistory.clear();

		assertTrue(board.hasAlreadyPlayedLetterAround(4, 5));
	}

	@Test
	void test_has_already_played_letter_around_right() throws PositionOutOfBoard, EmptyBoxException, IllegalMoveException {

		ActionHistory actionHistory = board.getActionsHistory();

		board.placeToken(token, 4, 4);
		actionHistory.clear();

		assertTrue(board.hasAlreadyPlayedLetterAround(4, 3));
	}

	@Test
	void test_has_already_played_letter_around_top() throws PositionOutOfBoard, EmptyBoxException, IllegalMoveException {

		ActionHistory actionHistory = board.getActionsHistory();

		board.placeToken(token, 4, 4);
		actionHistory.clear();

		assertTrue(board.hasAlreadyPlayedLetterAround(5, 4));
	}

	@Test
	void test_has_already_played_letter_around_bottom() throws PositionOutOfBoard, EmptyBoxException, IllegalMoveException {

		ActionHistory actionHistory = board.getActionsHistory();

		board.placeToken(token, 4, 4);
		actionHistory.clear();

		assertTrue(board.hasAlreadyPlayedLetterAround(3, 4));
	}

	@Test
	void should_get_word_throw_position_out_of_board_exception() {

		assertThrows(PositionOutOfBoard.class, () -> {
			board.getWord(0, 0, Direction.HORIZONTAL);
		});
	}

	@Test
	void test_is_already_played_letter_around_actions() throws PositionOutOfBoard, EmptyBoxException, IllegalMoveException {

		ActionHistory actionHistory = board.getActionsHistory();

		board.placeToken(token, 4, 4);
		actionHistory.clear();

		board.placeToken(token, 4, 5);

		assertTrue(board.isAlreadyPlayedLetterAroundActions());
	}

	@Test
	void should_get_box_position() throws PositionOutOfBoard {
		Board board = new Board();
		Box box = board.getBox(2, 3);
		Position position = board.getBoxPosition(box);

		assertEquals(new Position(2, 3), position);
	}

	@Test
	void should_throw_exception_when_box_not_found() {
		Board board = new Board();
		Box box = new Box(false, null, Effect.NORMAL);

		assertThrows(IllegalArgumentException.class, () -> {
			board.getBoxPosition(box);
		});
	}
}