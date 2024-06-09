package scrabble.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;

class BoxTest {

    @Test
    void test_describe_when_is_empty() {
        Box box = new Box(false, null);

        String describeResult = box.describe();

        assertEquals(" ", describeResult);
    }

    @Test
    void test_describe_when_is_middle() {
        Box box = new Box(true, null);

        String describeResult = box.describe();

        assertEquals("⭐", describeResult);
    }

    @Test
    void test_describe_when_token_is_not_null() {
        Token token = new Token(FrenchLetter.A);
        Box box = new Box(false, token);
        
        String exceptedResult = token.display();
        String describeResult = box.describe();

        assertEquals(exceptedResult, describeResult);
    }

    @Test
    void should_return_true_when_box_is_empty() {
        Box box = new Box(false, null);

        boolean isEmptyResult = box.isEmpty();

        assertEquals(true, isEmptyResult);
    }

    @Test
    void should_return_false_when_box_is_not_empty() {
        Token token = new Token(FrenchLetter.A);
        Box box = new Box(false, token);

        boolean isEmptyResult = box.isEmpty();

        assertEquals(false, isEmptyResult);
    }

    @Test
    void should_return_0_when_box_is_empty() {
        Box box = new Box(false, null);

        Integer scoreResult = box.getScore();

        assertEquals(0, scoreResult);
    }

    @Test
    void should_return_token_score_when_box_is_not_empty() {
        Token token = new Token(FrenchLetter.A);
        Box box = new Box(false, token);

        Integer scoreResult = box.getScore();
        Integer exceptedScore = token.getScore();

        assertEquals(exceptedScore, scoreResult);
    }

    @Test
    void should_return_true_when_box_is_middle() {
        Box box = new Box(true, null);

        boolean isMiddleResult = box.isMiddle();

        assertEquals(true, isMiddleResult);
    }

    @Test
    void should_return_false_when_box_is_not_middle() {
        Box box = new Box(false, null);

        boolean isMiddleResult = box.isMiddle();

        assertEquals(false, isMiddleResult);
    }
}
