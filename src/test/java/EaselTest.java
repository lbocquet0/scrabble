import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import scrabble.model.Rack;
import scrabble.controller.Game;
import scrabble.model.Player;

class EaselTest {
    Game game;
    Player player;
    Rack easel;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = new Player(game);
        easel = new Rack(player);
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