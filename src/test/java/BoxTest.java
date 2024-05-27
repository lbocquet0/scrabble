import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import scrabble.model.board.Box;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;

class BoxTest {

    @Test
    void testDescribeWhenIsEmpty() {
        Box box = new Box(false, null);

        String describeResult = box.describe();

        assertEquals(" ", describeResult);
    }

    @Test
    void testDescribeWhenIsMiddle() {
        Box box = new Box(true, null);

        String describeResult = box.describe();

        assertEquals("⭐", describeResult);
    }

    @Test
    void testDescribeWhenTokenIsNotNull() {
        Token token = new Token(FrenchLetter.A);
        Box box = new Box(false, token);
        
        String exceptedResult = token.display();
        String describeResult = box.describe();

        assertEquals(exceptedResult, describeResult);
    }

    @Test
    void shouldReturnTrueWhenBoxIsEmpty() {
        Box box = new Box(false, null);

        boolean isEmptyResult = box.isEmpty();

        assertEquals(true, isEmptyResult);
    }

    @Test
    void shouldReturnFalseWhenBoxIsNotEmpty() {
        Token token = new Token(FrenchLetter.A);
        Box box = new Box(false, token);

        boolean isEmptyResult = box.isEmpty();

        assertEquals(false, isEmptyResult);
    }

    @Test
    void shouldReturn0WhenBoxIsEmpty() {
        Box box = new Box(false, null);

        Integer scoreResult = box.getScore();

        assertEquals(0, scoreResult);
    }

    @Test
    void shouldReturnTokenScoreWhenBoxIsNotEmpty() {
        Token token = new Token(FrenchLetter.A);
        Box box = new Box(false, token);

        Integer scoreResult = box.getScore();
        Integer exceptedScore = token.getScore();

        assertEquals(exceptedScore, scoreResult);
    }

    @Test
    void shouldReturnTrueWhenBoxIsMiddle() {
        Box box = new Box(true, null);

        boolean isMiddleResult = box.isMiddle();

        assertEquals(true, isMiddleResult);
    }

    @Test
    void shouldReturnFalseWhenBoxIsNotMiddle() {
        Box box = new Box(false, null);

        boolean isMiddleResult = box.isMiddle();

        assertEquals(false, isMiddleResult);
    }
}