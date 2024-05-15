import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import scrabble.model.board.Box;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;

class BoxTest {

    @Test
    void testDescribeWhenTokenIsNull() {
        Box box = new Box(false, null);
        assertEquals(" ", box.describe());
    }

    @Test
    void testDescribeWhenTokenIsNotNull() {
        Token token = new Token(FrenchLetter.A);
        Box box = new Box(false, token);
        assertEquals(token.display(),box.describe());
    }

}