package scrabble.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scrabble.model.board.Board;
import scrabble.model.board.Box;
import scrabble.model.board.action.Action;
import scrabble.model.board.action.ActionHistory;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Joker;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.PositionOutOfBoard;
import scrabble.utils.exceptions.EmptyBoxException;

public class ScoreCounterTest {

	private Token tokenA;
	private Board board;
	private ActionHistory actionHistory;

	@BeforeEach
	public void initialize() {
		board = new Board();
		tokenA = new Token(FrenchLetter.A);
		actionHistory = board.getActionsHistory();
	}

	@Test
	public void should_return_0_when_no_action() {
		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(0, score);
	}

	@Test
	public void should_count_score_when_word_is_aaa_horizontal() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 8, 7);
		board.setToken(tokenA, 8, 8);
		board.setToken(tokenA, 8, 9);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(3, score);
	}

	@Test
	public void should_count_score_when_word_is_aaa_vertical() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 7, 8);
		board.setToken(tokenA, 8, 8);
		board.setToken(tokenA, 9, 8);

		Integer score = ScoreCounter.countScore(board, Direction.VERTICAL);
		assertEquals(3, score);
	}

	@Test
	public void should_count_score_when_complete_word_horizontal() throws PositionOutOfBoard, EmptyBoxException {
		Box box3 = new Box(false, tokenA);

		board.setToken(tokenA, 7, 8);
		board.setToken(tokenA, 8, 8);
		board.setToken(tokenA, 9, 8);
		actionHistory.clear();

		actionHistory.add(new Action(9, 8, box3));

		Integer score = ScoreCounter.countScore(board, Direction.VERTICAL);
		assertEquals(3, score);
	}

	@Test
	public void should_count_score_when_complete_word_vertical() throws PositionOutOfBoard, EmptyBoxException {
		Box box3 = new Box(false, tokenA);

		board.setToken(tokenA, 8, 7);
		board.setToken(tokenA, 8, 8);
		board.setToken(tokenA, 8, 9);
		actionHistory.clear();

		actionHistory.add(new Action(8, 9, box3));

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(3, score);
	}

	@Test
	public void should_count_score_when_add_horizontal_word_across_vertical_word()
			throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 7, 7);
		board.setToken(tokenA, 8, 7);
		board.setToken(tokenA, 9, 7);
		actionHistory.clear();

		board.setToken(tokenA, 8, 6);
		board.setToken(tokenA, 8, 8);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(3, score);
	}

	@Test
	public void should_count_score_when_add_vertical_word_across_horizontal_word()
			throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 7, 7);
		board.setToken(tokenA, 7, 8);
		board.setToken(tokenA, 7, 9);
		actionHistory.clear();

		board.setToken(tokenA, 6, 8);
		board.setToken(tokenA, 8, 8);

		Integer score = ScoreCounter.countScore(board, Direction.VERTICAL);
		assertEquals(3, score);
	}

	@Test
	public void should_count_score_when_create_new_words_with_vertical_and_horizontal_words()
			throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 6, 8);
		board.setToken(tokenA, 7, 8);

		board.setToken(tokenA, 8, 6);
		board.setToken(tokenA, 8, 7);

		actionHistory.clear();

		board.setToken(tokenA, 8, 8);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(6, score);
	}

	@Test
	public void should_count_score_for_two_words_both_horizontal() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 8, 6);
		board.setToken(tokenA, 8, 7);
		actionHistory.clear();

		board.setToken(tokenA, 8, 9);
		board.setToken(tokenA, 8, 10);
		actionHistory.clear();

		board.setToken(tokenA, 8, 8);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(5, score);
	}

	@Test
	public void should_count_score_for_two_words_both_vertical() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 6, 8);
		board.setToken(tokenA, 7, 8);
		actionHistory.clear();

		board.setToken(tokenA, 9, 8);
		board.setToken(tokenA, 10, 8);
		actionHistory.clear();

		board.setToken(tokenA, 8, 8);

		Integer score = ScoreCounter.countScore(board, Direction.VERTICAL);
		assertEquals(5, score);
	}

	@Test
	public void should_count_score_with_double_letter_score() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 1, 4);
		board.setToken(tokenA, 1, 5);
		board.setToken(tokenA, 1, 6);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(4, score);
	}

	@Test
	public void should_count_score_with_triple_letter_score() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 2, 6);
		board.setToken(tokenA, 2, 7);
		board.setToken(tokenA, 2, 8);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(5, score);
	}

	@Test
	public void should_count_score_with_double_word_score() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 2, 2);
		board.setToken(tokenA, 2, 3);
		board.setToken(tokenA, 2, 4);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(6, score);
	}

	@Test
	public void should_count_score_with_triple_word_score() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 1, 1);
		board.setToken(tokenA, 1, 2);
		board.setToken(tokenA, 1, 3);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(9, score);
	}

	@Test
	public void should_count_score_with_double_letter_and_double_word_score()
			throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 1, 4);
		board.setToken(tokenA, 2, 4);
		board.setToken(tokenA, 3, 4);
		board.setToken(tokenA, 4, 4);

		Integer score = ScoreCounter.countScore(board, Direction.VERTICAL);
		assertEquals(10, score);
	}

	@Test
	public void should_count_score_when_complete_word_with_double_letter_effect()
			throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 7, 7);
		board.setToken(tokenA, 8, 7);
		board.setToken(tokenA, 9, 7);
		actionHistory.clear();

		board.setToken(tokenA, 10, 7);

		Integer score = ScoreCounter.countScore(board, Direction.VERTICAL);
		assertEquals(4, score);
	}

	@Test
	public void should_count_score_when_complete_word_with_double_word_effect()
			throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 1, 1);
		board.setToken(tokenA, 2, 1);

		actionHistory.clear();

		board.setToken(tokenA, 3, 1);

		Integer score = ScoreCounter.countScore(board, Direction.VERTICAL);
		assertEquals(3, score);
	}

	@Test
	public void should_count_score_when_complete_two_words_with_double_letter_effect()
			throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 1, 1);
		board.setToken(tokenA, 2, 1);
		board.setToken(tokenA, 3, 1);
		actionHistory.clear();

		board.setToken(tokenA, 4, 2);
		board.setToken(tokenA, 4, 3);
		actionHistory.clear();

		board.setToken(tokenA, 4, 1);

		Integer score = ScoreCounter.countScore(board, Direction.VERTICAL);
		assertEquals(9, score);
	}

	@Test
	public void should_count_score_when_complete_two_words_with_double_word_effect()
			throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 4, 1);
		board.setToken(tokenA, 4, 2);
		board.setToken(tokenA, 4, 3);
		actionHistory.clear();

		board.setToken(tokenA, 1, 4);
		board.setToken(tokenA, 2, 4);
		board.setToken(tokenA, 3, 4);
		actionHistory.clear();

		board.setToken(tokenA, 4, 4);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(16, score);
	}

	@Test
	public void should_count_score_when_play_joker() throws PositionOutOfBoard, EmptyBoxException {
		Joker joker = new Joker();
		joker.setLetter(FrenchLetter.A);

		board.setToken(joker, 8, 8);
		board.setToken(tokenA, 8, 7);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(1, score);
	}

	@Test
	public void should_count_score_when_play_all_rack_letters() throws PositionOutOfBoard, EmptyBoxException {
		board.setToken(tokenA, 8, 8);
		board.setToken(tokenA, 8, 7);
		board.setToken(tokenA, 8, 5);
		board.setToken(tokenA, 8, 6);
		board.setToken(tokenA, 8, 9);
		board.setToken(tokenA, 8, 10);
		board.setToken(tokenA, 8, 11);

		Integer score = ScoreCounter.countScore(board, Direction.HORIZONTAL);
		assertEquals(57, score);
	}
}