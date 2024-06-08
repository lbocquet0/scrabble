package scrabble.utils;

import java.util.ArrayList;
import java.util.List;

import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.board.Box;
import scrabble.model.board.action.Action;
import scrabble.model.board.action.ActionHistory;
import scrabble.utils.exceptions.PositionOutOfBoard;
import scrabble.utils.exceptions.WordNotFoundException;

public class ScoreCounter {
	
	private static int SCORE_BONUS_WHEN_ALL_TOKENS_USED = 50;

	public static Integer countWordScore(ArrayList<Box> wordLetters, ActionHistory actionHistory) {
		Integer score = 0;
		Integer wordMultiplicator = 1;

		for (Box box : wordLetters) {
			
			if (actionHistory.boxIsInActions(box)) {

				score += box.getScore() * box.getLetterEffectMultiplicator();
				wordMultiplicator *= box.getWordEffectMultiplicator();
			} else {

				score += box.getScore();
			}
		}
		score *= wordMultiplicator;

		return score;
	}

	public static Integer countScore(Board board, ActionHistory actionHistory, Direction direction) {
		if (actionHistory.isEmpty()) {
			return 0;
		}

		Integer score = 0;

		Action firstAction = actionHistory.getFirstAction();

		ArrayList<Box> wordLetters = null;
		try {
			wordLetters = board.getWord(firstAction.getRowPosition(), firstAction.getColumnPosition(), direction);
		} catch (PositionOutOfBoard e) {
			return 0;
		} catch (WordNotFoundException e) {
			return 0;
		}

		score += countWordScore(wordLetters, actionHistory);

		Direction oppositeDirection;
		
		if (direction == Direction.HORIZONTAL) {
			oppositeDirection = Direction.VERTICAL;
		} else {
			oppositeDirection = Direction.HORIZONTAL;
		}

		List<Action> actions = actionHistory.actions();
		for (Action action : actions) {
			try {
				wordLetters = board.getWord(action.getRowPosition(), action.getColumnPosition(), oppositeDirection);
			} catch (PositionOutOfBoard e) {
				continue;
			} catch (WordNotFoundException e) {
				continue;
			}
			score += countWordScore(wordLetters, actionHistory);
		}

		Integer actionsAmount = actionHistory.amount();
		if (actionsAmount == Rack.MAX_TOKENS_AMOUNT) {
			score += SCORE_BONUS_WHEN_ALL_TOKENS_USED;
		}

		return score;
	}
}