package scrabble.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scrabble.model.Bag;
import scrabble.model.Player;
import scrabble.model.Rack;
import scrabble.model.board.Board;
import scrabble.model.token.Token;
import scrabble.utils.Direction;
import scrabble.utils.exceptions.*;

class GameTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void should_return_empty_board_on_new_object_creation() throws BoxIndexOutOfBoard {
    	Board board = game.getBoard();
    	
    	Boolean boardIsEmpty = board.gameHaveNotStarted();
    	
    	assertTrue(boardIsEmpty);
    }
    
    @Test
    void should_return_full_bag_on_new_object_creation() {
    	Bag bag = game.getBag();
    	
    	Integer bagCount = bag.remainingTokens();
    	
    	assertEquals(102, bagCount);
    }
    
    @Test
    void should_return_a_player_with_empty_rack_on_new_object_creation() {
    	Player player = game.getPlayer();
    	
    	Integer playerRackCount = player.remainingTokenInRack(); 
    	
    	assertEquals(0, playerRackCount);
    }
    
    @Test
    void should_return_a_player_with_full_rack_on_initialize() throws EmptyBagException {
    	game.initialize();
    	Player player = game.getPlayer();
    	
    	Integer playerRackCount = player.remainingTokenInRack();
    	
    	assertEquals(Rack.MAX_TOKENS_AMOUNT, playerRackCount);
    }

	@Test
	void should_return_a_player_with_full_rack_on_fillUpPlayerRack() throws EmptyBagException {
		Player player = game.getPlayer();

		game.fullFillPlayerRack(player);

		Integer playerRackCount = player.remainingTokenInRack();

		assertEquals(Rack.MAX_TOKENS_AMOUNT, playerRackCount);
	}

	@Test
	void should_dont_add_token_to_rack_on_fillUpPlayerRack() throws EmptyBagException {
		Player player = game.getPlayer();

		game.fullFillPlayerRack(player);
		game.fillUpPlayerRack(player);

		Integer playerRackCount = player.remainingTokenInRack();

		assertEquals(Rack.MAX_TOKENS_AMOUNT, playerRackCount);
	}
    
    @Test
    void should_throws_exception_on_playLetter_outside_of_board() throws EmptyBagException {
    	game.initialize();
    	Player player = game.getPlayer();
    	Rack rack = player.rack();
    	
    	Token token = rack.token(0);
    	
    	assertThrows(BoxIndexOutOfBoard.class, () -> {
    		game.playLetter(token, 20, 20);
    	});	
    }
    
    @Test
    void test_playLetter_in_general_condition() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
    	game.initialize();
    	Player player = game.getPlayer();
    	Rack rack = player.rack();
    	
    	Token token = rack.token(0);	
		game.playLetter(token, 8, 8);
		Token placedToken = game.getBoard().getToken(8, 8); 
		
		assertEquals(token, placedToken);
    }
    
    
    @Test
    void should_throws_exception_on_playLetter_on_already_occuped_position() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
    	game.initialize();
    	Player player = game.getPlayer();
    	Rack rack = player.rack();
    	Token token = rack.token(0);
    	Token oToken = rack.token(1);
		game.playLetter(token, 8, 8);		
		
		
    	assertThrows(OccupiedBoxException.class, () -> {
    		game.playLetter(oToken, 8, 8);
    	});	
    }

    @Test
    void should_do_nothing_if_the_same_letter_is_placed() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
    	game.initialize();
    	
		Player player = game.getPlayer();
    	Rack rack = player.rack();
    	Token token = rack.token(0);
    	Token oToken = rack.token(0);

		game.playLetter(token, 8, 8);		
		game.playLetter(oToken, 8, 8);

		Token placedToken = game.getBoard().getToken(8, 8); 
		assertEquals(oToken, placedToken);
    }

	@Test
	void should_add_token_in_rack_on_cancel_world() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
		game.initialize();

		Player player = game.getPlayer();
		Rack rack = player.rack();
		Token token = rack.token(0);

		game.playLetter(token, 8, 8);

		game.cancelLastWord();

		assertThat(rack.tokens()).containsOnlyOnce(token);
	}

	@Test
	void should_remove_token_from_board_on_cancel_world() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
		game.initialize();

		Board board = game.getBoard();
		Player player = game.getPlayer();
		Rack rack = player.rack();
		Token token = rack.token(0);

		game.playLetter(token, 8, 8);
		game.cancelLastWord();

		Token placedToken = board.getToken(8, 8);
		assertEquals(null, placedToken);
	}

	@Test
	void should_clear_round_history() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException {
		game.initialize();

		Player player = game.getPlayer();
		Rack rack = player.rack();
		Token token = rack.token(0);
		Board board = game.getBoard();

		game.playLetter(token, 8, 8);
		game.clearRoundHistory();

		assertThat(board.getActionsHistory()).isEmpty();
	}

	@Test
	void should_add_score_on_validate_word() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException, IllegalMoveException {
		game.initialize();

		Player player = game.getPlayer();
		Rack rack = player.rack();
		Token token = rack.token(0);
		Token token2 = rack.token(1);

		game.playLetter(token, 8, 8);
		game.playLetter(token2, 8, 9);

		game.validateWord(Direction.HORIZONTAL);

		assertThat(player.score()).isGreaterThan(0);
	}

	@Test
	void should_return_score_on_validate_word() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException, IllegalMoveException {
		game.initialize();

		Player player = game.getPlayer();
		Rack rack = player.rack();
		Token token = rack.token(0);
		Token token2 = rack.token(1);

		game.playLetter(token, 8, 8);
		game.playLetter(token2, 8, 9);

		Integer score = game.validateWord(Direction.HORIZONTAL);

		assertThat(score).isGreaterThan(0);
	}

	@Test
	void should_clear_history_on_validate_word() throws EmptyBagException, OccupiedBoxException, BoxIndexOutOfBoard, TokenDoesntExists, EmptyBoxException, IllegalMoveException {
		game.initialize();

		Player player = game.getPlayer();
		Rack rack = player.rack();
		Board board = game.getBoard();
		Token token = rack.token(0);
		Token token2 = rack.token(1);

		game.playLetter(token, 8, 8);
		game.playLetter(token2, 8, 9);

		game.validateWord(Direction.HORIZONTAL);

		assertThat(board.getActionsHistory()).isEmpty();
	}
}