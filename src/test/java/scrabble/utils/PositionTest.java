package scrabble.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void should_return_correct_row_and_column() {
        Position position = new Position(2, 3);
        assertEquals(2, position.row());
        assertEquals(3, position.column());
    }

    @Test
    public void should_return_true_when_positions_are_equal() {
        Position position1 = new Position(2, 3);
        Position position2 = new Position(2, 3);

        assertEquals(position1, position2);
    }

    @Test
    public void should_return_false_when_positions_are_not_equal() {
        Position position1 = new Position(2, 3);
        Position position2 = new Position(4, 5);

        assertNotEquals(position1, position2);
    }

    @Test
    public void should_return_false_when_compared_to_null() {
        Position position = new Position(2, 3);
        assertFalse(position.equals(null));
    }

    @Test
    public void should_return_false_when_compared_to_different_object() {
        Position position = new Position(2, 3);
        assertFalse(position.equals("some string"));
    }

    @Test
    public void should_return_true_when_compared_to_itself() {
        Position position = new Position(2, 3);
        assertTrue(position.equals(position));
    }
}