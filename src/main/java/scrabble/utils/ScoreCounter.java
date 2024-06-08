package scrabble.utils;

import java.util.ArrayList;

import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.board.Box;
import scrabble.model.board.action.Action;
import scrabble.utils.exceptions.PositionOutOfBoard;
import scrabble.utils.exceptions.WordNotFoundException;

public class ScoreCounter {
	
	private static int SCORE_BONUS_WHEN_ALL_TOKENS_USED = 50;

	public static Integer countWordScore(ArrayList<Box> wordLetters) {
		Integer score = 0;

		for (Box box : wordLetters) {
			score += box.getScore();
		}

		return score;
	}

	public static Integer countScore(Board board, ArrayList<Action> actions, Direction direction) {
		int doneActionsAmount = actions.size();
		if (doneActionsAmount == 0) {
			return 0;
		}
		Integer score = 0;

		Action firstAction = actions.get(0);

		ArrayList<Box> wordLetters = null;
		try {
			wordLetters = board.getWord(firstAction.getRowPosition(), firstAction.getColumnPosition(), direction);
		} catch (PositionOutOfBoard e) {
			return 0;
		} catch (WordNotFoundException e) {
			return 0;
		}

		score += countWordScore(wordLetters);

		Direction oppositeDirection;
		
		if (direction == Direction.HORIZONTAL) {
			oppositeDirection = Direction.VERTICAL;
		} else {
			oppositeDirection = Direction.HORIZONTAL;
		}

		for (Action action : actions) {
			try {
				wordLetters = board.getWord(action.getRowPosition(), action.getColumnPosition(), oppositeDirection);
			} catch (PositionOutOfBoard e) {
				continue;
			} catch (WordNotFoundException e) {
				continue;
			}
			score += countWordScore(wordLetters);
		}

		if (doneActionsAmount == Rack.MAX_TOKENS_AMOUNT) {
			score += SCORE_BONUS_WHEN_ALL_TOKENS_USED;
		}

		return score;
	}
}