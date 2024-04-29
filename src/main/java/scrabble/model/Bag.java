package scrabble.model;

import java.util.ArrayList;

public class Bag {
    private ArrayList<Token> tokens;

    public Bag() {
        this.tokens = new ArrayList<>();
        this.createTokens();
    }

    public void shuffle() {
        for (int i = 0; i < this.tokens.size(); i++) {
            int randomIndex = (int) (Math.random() * this.tokens.size());
            Token temp = this.tokens.get(i);
            this.tokens.set(i, this.tokens.get(randomIndex));
            this.tokens.set(randomIndex, temp);
        }
    }
    
    public Token pikeToken() {
		return null;
    }

    private void createTokens() {
        for (int i = 0; i < 15; i++) {
            this.tokens.add(new Token(Letter.E));
        }
        for (int i = 0; i < 9; i++) {
            this.tokens.add(new Token(Letter.A));
        }
        for (int i = 0; i < 8; i++) {
            this.tokens.add(new Token(Letter.I));
        }
        for (int i = 0; i < 6; i++) {
            this.tokens.add(new Token(Letter.N));
            this.tokens.add(new Token(Letter.R));
            this.tokens.add(new Token(Letter.T));
            this.tokens.add(new Token(Letter.S));
            this.tokens.add(new Token(Letter.O));
            this.tokens.add(new Token(Letter.U));
        }
        for (int i = 0; i < 5; i++) {
            this.tokens.add(new Token(Letter.L));
        }
        for (int i = 0; i < 3; i++) {
            this.tokens.add(new Token(Letter.D));
            this.tokens.add(new Token(Letter.M));
        }
        for (int i = 0; i < 2; i++) {
            this.tokens.add(new Token(Letter.G));
            this.tokens.add(new Token(Letter.B));
            this.tokens.add(new Token(Letter.C));
            this.tokens.add(new Token(Letter.P));
            this.tokens.add(new Token(Letter.F));
            this.tokens.add(new Token(Letter.H));
            this.tokens.add(new Token(Letter.V));
        }
        for (int i = 0; i < 1; i++) {
            this.tokens.add(new Token(Letter.J));
            this.tokens.add(new Token(Letter.Q));
            this.tokens.add(new Token(Letter.K));
            this.tokens.add(new Token(Letter.W));
            this.tokens.add(new Token(Letter.X));
            this.tokens.add(new Token(Letter.Y));
            this.tokens.add(new Token(Letter.Z));
        }
    }


}
