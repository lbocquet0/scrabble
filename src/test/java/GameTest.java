import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scrabble.controller.Game;
import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.token.Token;
import scrabble.utils.exceptions.BoxIndexOutOfBoard;
import scrabble.utils.exceptions.EmptyBagException;
import scrabble.utils.exceptions.EmptyBoxException;
import scrabble.utils.exceptions.OccupiedBoxException;
import scrabble.utils.exceptions.TokenDoesntExists;

class GameTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void should_return_empty_board_on_new_object_creation() {
    	Board board = game.getBoard();
    	
    	Boolean boardIsEmpty = true; //board.gameHaveNotStarted();
    	
    	assertTrue(boardIsEmpty);
    }
    
    @Test
    void should_return_full_bag_on_new_object_creation() {
    	Bag bag = game.getBag();
    	
    	Integer bagCount = bag.remainingTokens();
    	
    	assertEquals(102, bagCount);
    }
    
    @Test
    void shoul_return_a_player_with_empty_rack_on_new_object_creation() {
    	Player player = game.getPlayer();
    	
    	Integer playerRackCount = player.remainingTokenInRack(); 
    	
    	assertEquals(0, playerRackCount);
    }
    
    @Test
    void should_return_a_player_with_full_rack_on_initialize() throws EmptyBagException {
    	game.initialize();
    	Player player = game.getPlayer();
    	
    	Integer playerRackCount = player.remainingTokenInRack();
    	
    	assertEquals(7, playerRackCount);
    }
    
    @Test
    void should_throws_exception_on_playLetter_outside_of_board() throws EmptyBagException {
    	game.initialize();
    	Player player = game.getPlayer();
    	Rack rack = player.getRack();
    	
    	Token token = rack.getToken(0);
    	
    	assertThrows(BoxIndexOutOfBoard.class, () -> {
    		game.playLetter(token, 20, 20);
    	});	
    }
    
    @Test
    void test_playLetter_in_general_condition() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
    	game.initialize();
    	Player player = game.getPlayer();
    	Rack rack = player.getRack();
    	
    	Token token = rack.getToken(0);	
		game.playLetter(token, 8, 8);
		Token placedToken = game.getBoard().getToken(8, 8); 
		
		assertEquals(token, placedToken);
    }
    
    
    @Test
    void should_throws_exception_on_playLetter_on_already_occuped_position() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
    	game.initialize();
    	Player player = game.getPlayer();
    	Rack rack = player.getRack();
    	Token token = rack.getToken(0);
    	Token oToken = rack.getToken(1);
		game.playLetter(token, 8, 8);		
		
		
    	assertThrows(OccupiedBoxException.class, () -> {
    		game.playLetter(oToken, 8, 8);
    	});	
    }
    
    @Test
    void should_do_nothing_if_the_same_letter_is_placed() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
    	game.initialize();
    	Player player = game.getPlayer();
    	Rack rack = player.getRack();
    	Token token = rack.getToken(0);
    	Token oToken = rack.getToken(0);
		game.playLetter(token, 8, 8);		
		
		game.playLetter(oToken, 8, 8);
		Token placedToken = game.getBoard().getToken(8, 8); 
		
		assertEquals(oToken, placedToken);
    }
}