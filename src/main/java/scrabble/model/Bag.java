package scrabble.model;

import java.util.ArrayList;
import java.util.Collections;

import scrabble.model.token.FrenchLetter;
import scrabble.model.token.Joker;
import scrabble.model.token.Token;
import scrabble.utils.EmptyBagException;

public class Bag {
    private ArrayList<Token> tokens;

    public Bag() {
        this.tokens = new ArrayList<>();
        this.fillTokens();
    }

    public void shuffle() {
        Collections.shuffle(this.tokens);
    }

    public Token pickToken() throws EmptyBagException {
        if (this.tokens.isEmpty()) {
            throw new EmptyBagException();
        }

        this.shuffle();
        return this.tokens.remove(0);
    }

    public void putToken(Token token) {
        this.tokens.add(token);
    }

    public int remainingTokens() {
        return this.tokens.size();
    }

    public Boolean isEmpty() {
        return this.tokens.isEmpty();
    }

    public void clear() {
        this.tokens.clear();
    }

    private void fillTokens() {
        // Default letters
        for (FrenchLetter letter : FrenchLetter.values()) {
            for (int i = 0; i < letter.getOccurencesAmount(); i++) {
                this.tokens.add(new Token(letter));
            }
        }

        // Joker
        for (int i = 0; i < Joker.OCCURRENCES_AMOUNT; i++) {
            this.tokens.add(new Joker());
        }
    }
}