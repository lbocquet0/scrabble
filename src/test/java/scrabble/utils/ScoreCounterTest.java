package scrabble.utils;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scrabble.model.board.Board;
import scrabble.model.board.Box;
import scrabble.model.board.action.Action;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
import scrabble.utils.exceptions.EmptyBoxException;

public class ScoreCounterTest {

	private Token tokenA;
	private Board board;

	@BeforeEach
	public void initialize() {
		board = new Board();
		tokenA = new Token(FrenchLetter.A);
	}

	@Test
	public void shouldCountWordScore() {
		Box box = new Box(false, tokenA);
		Box box2 = new Box(false, tokenA);

		ArrayList<Box> wordLetters = new ArrayList<>();
		wordLetters.add(box);
		wordLetters.add(box2);

		Integer score = ScoreCounter.countWordScore(wordLetters);

		assertEquals(FrenchLetter.A.getPoint() * 2, score);
	}

	@Test
	public void should_return_0_when_no_action() {
		ArrayList<Action> wordLetters = new ArrayList<Action>();
		Integer score = ScoreCounter.countScore(board, wordLetters, Direction.HORIZONTAL);
		assertEquals(0, score);
	}

	@Test
	public void should_return_0_when_no_direction() {
		ArrayList<Action> wordLetters = new ArrayList<Action>();
		Integer score = ScoreCounter.countScore(board, wordLetters, null);
		assertEquals(0, score);
	}

	@Test
	public void should_return_3_when_word_is_aaa() throws EmptyBoxException, BoxIndexOutOfBoard {
		Box box = new Box(false, tokenA);
		Box box2 = new Box(false, tokenA);
		Box box3 = new Box(false, tokenA);

		board.setToken(tokenA,7, 7);
		board.setToken(tokenA,7, 8);
		board.setToken(tokenA,7, 9);

		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new Action(7, 7, box));
		actions.add(new Action(7, 8, box2));
		actions.add(new Action(7, 9, box3));

		Integer score = ScoreCounter.countScore(board, actions, Direction.HORIZONTAL);
		assertEquals(3, score);
	}

	@Test
	public void should_return_7_when_word_is_aaaa_and_completing_aa_vertically() throws EmptyBoxException, BoxIndexOutOfBoard {
		Box box = new Box(false, tokenA);
		Box box2 = new Box(false, tokenA);
		Box box3 = new Box(false, tokenA);
		Box box4 = new Box(false, tokenA);
		Box box5 = new Box(false, tokenA);

		board.setToken(tokenA, 6, 9);

		board.setToken(tokenA, 7, 7);
		board.setToken(tokenA, 7, 8);
		board.setToken(tokenA, 7, 9);
		board.setToken(tokenA, 7, 10);
		board.setToken(tokenA, 7, 11);

		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new Action(7, 7, box));
		actions.add(new Action(7, 8, box2));
		actions.add(new Action(7, 9, box3));
		actions.add(new Action(7, 10, box4));
		actions.add(new Action(7, 11, box5));

		Integer score = ScoreCounter.countScore(board, actions, Direction.HORIZONTAL);
		assertEquals(7, score);
	}

	@Test
	public void should_return_57_when_word_is_aaaaaaa() throws EmptyBoxException, BoxIndexOutOfBoard {
		Box box = new Box(false, tokenA);
		Box box2 = new Box(false, tokenA);
		Box box3 = new Box(false, tokenA);
		Box box4 = new Box(false, tokenA);
		Box box5 = new Box(false, tokenA);
		Box box6 = new Box(false, tokenA);
		Box box7 = new Box(false, tokenA);

		board.setToken(tokenA, 7, 7);
		board.setToken(tokenA, 7, 8);
		board.setToken(tokenA, 7, 9);
		board.setToken(tokenA, 7, 10);
		board.setToken(tokenA, 7, 11);
		board.setToken(tokenA, 7, 12);
		board.setToken(tokenA, 7, 13);

		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new Action(7, 7, box));
		actions.add(new Action(7, 8, box2));
		actions.add(new Action(7, 9, box3));
		actions.add(new Action(7, 10, box4));
		actions.add(new Action(7, 11, box5));
		actions.add(new Action(7, 12, box6));
		actions.add(new Action(7, 13, box7));

		Integer score = ScoreCounter.countScore(board, actions, Direction.HORIZONTAL);
		assertEquals(57, score);
	}

}