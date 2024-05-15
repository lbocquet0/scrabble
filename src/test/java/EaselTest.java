import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import scrabble.model.Rack;
import scrabble.model.token.Token;
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
        int remainingTokens = easel.remainingTokens();

        assertEquals(7, remainingTokens);
    }

    @Test
    void testSwapShouldNotChangeAmount() {
        Token token = easel.getToken(0);

        easel.swapTokens(token);
        int remainingTokens = easel.remainingTokens();
        
        assertEquals(7, remainingTokens);
    }
}