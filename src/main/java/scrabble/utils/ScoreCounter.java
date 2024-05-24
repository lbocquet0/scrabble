package scrabble.utils;

import java.util.ArrayList;

import scrabble.model.board.Action;
import scrabble.model.board.Board;
import scrabble.model.board.Box;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
import scrabble.utils.exceptions.WordNotFoundException;

public class ScoreCounter {

	// TODO: JUnit test
	public static Integer countWordScore(ArrayList<Box> wordLetters) {
		Integer score = 0;

		for (Box box : wordLetters) {
			score += box.getScore();
		}

		return score;
	}

	// TODO: JUnit test
	public static Integer countScore(Board board, ArrayList<Action> actions, Direction direction) {
		if (actions.size() == 0) {
			return 0;
		}
		Integer score = 0;

		Action firstAction = actions.get(0);

		ArrayList<Box> wordLetters = null;
		try {
			wordLetters = board.getWord(firstAction.getRowPosition(), firstAction.getColumnPosition(), direction);
		} catch (BoxIndexOutOfBoard e) {
			return 0;
		} catch (WordNotFoundException e) {
			return 0;
		}

		score += countWordScore(wordLetters);

		Direction oppositeDirection = direction == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
		for (Action action : actions) {
			try {
				wordLetters = board.getWord(action.getRowPosition(), action.getColumnPosition(), oppositeDirection);
			} catch (BoxIndexOutOfBoard e) {
				continue;
			} catch (WordNotFoundException e) {
				continue;
			}

			score += countWordScore(wordLetters);
		}

		return score;
	}
}