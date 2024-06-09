package scrabble.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.EmptyBagException;


public class BagTest {
    Bag bag;

    @Test
    void should_be_full_on_create() {
        bag = new Bag();
        assertEquals(102, bag.remainingTokens());
    }

    @Test
    void should_be_empty_after_clear() {
        bag = new Bag();
        bag.clear();
        
        assertTrue(bag.isEmpty());
    }

    @Test
    void should_throw_exception_when_empty() {
        bag = new Bag();
        bag.clear();

        assertThrows(EmptyBagException.class, () -> bag.pickToken());
    }

    @Test
    void should_loose_token_after_one_pick() {
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
    void should_gain_one_token_after_put() {
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
