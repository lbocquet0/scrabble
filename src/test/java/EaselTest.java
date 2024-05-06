import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import scrabble.model.Easel;
import scrabble.model.Game;
import scrabble.model.Player;

class EaselTest {
    Game game;
    Player player;
    Easel easel;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = new Player(game);
        easel = new Easel(player);
    }

    @Test
    void testShouldBeFullOnCreation() {
        assertEquals(7, easel.getTokensAmount());
    }

    @Test
    void testSwapShouldNotChangeAmount() {
        easel.swapTokens(easel.getToken(0));
        assertEquals(7, easel.getTokensAmount());
    }
}