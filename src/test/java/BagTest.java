import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import scrabble.model.Bag;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.EmptyBagException;


public class BagTest {
    Bag bag;

    @Test
    void ShouldBeFullOnCreate() {
        bag = new Bag();
        assertEquals(102, bag.remainingTokens());
    }

    @Test
    void ShouldBeEmptyAfterClear() {
        bag = new Bag();
        bag.clear();
        
        assertTrue(bag.isEmpty());
    }

    @Test
    void ShouldThrowExceptionWhenEmpty() {
        bag = new Bag();
        bag.clear();

        assertThrows(EmptyBagException.class, () -> bag.pickToken());
    }

    @Test
    void ShouldLoose1TokenAfterPick() {
        bag = new Bag();
        int initialCount = bag.remainingTokens();

        try {
            bag.pickToken();
        } catch (EmptyBagException e) {
            fail();
        }

        assertEquals(initialCount - 1, bag.remainingTokens());
    }

    @Test
    void ShouldGain1TokenAfterPut() {
        bag = new Bag();
        
        try {
            Token token = new Token(FrenchLetter.A);
            bag.putToken(token);
        } catch (Exception e) {
            fail();
        }

        assertEquals(103, bag.remainingTokens());
    }
}
