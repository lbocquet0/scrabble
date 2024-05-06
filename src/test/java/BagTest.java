import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import scrabble.model.Bag;
import scrabble.model.Letter;
import scrabble.model.Token;
import scrabble.util.EmptyBagException;


public class BagTest {
    Bag bag;

    @Test
    void ShouldBeFullOnCreate() {
        bag = new Bag();
        assertEquals(102, bag.countTokens());
    }

    @Test
    void ShouldThrowExceptionWhenEmpty() {
        bag = new Bag();
        for (int i = 0; i < 102; i++) {
            try {
                bag.pickToken();
            } catch (EmptyBagException _e) {
            }
        }
        try {
            bag.pickToken();
        } catch (EmptyBagException e) {
            assertEquals("The bag is empty.", e.getMessage());
        }
    }

    @Test
    void ShouldBeEmptyAfter102Picks() {
        bag = new Bag();
        for (int i = 0; i < 102; i++) {
            try {
                bag.pickToken();
            } catch (EmptyBagException _e) {
            }
        }
        assertEquals(0, bag.countTokens());
    }

    @Test
    void CountShouldBe101After1Pick() {
        bag = new Bag();
        try {
            bag.pickToken();
        } catch (EmptyBagException _e) {
        }
        assertEquals(101, bag.countTokens());
    }

    @Test
    void CountShouldBe102After1PicksAnd1Put() {
        bag = new Bag();
        try {
            bag.pickToken();
        } catch (EmptyBagException _e) {
        }
        bag.putToken(new Token(Letter.A));
        assertEquals(102, bag.countTokens());
    }

}
