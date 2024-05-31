package scrabble.utils.exceptions;

public class IllegalMoveException extends Exception {
    public IllegalMoveException() {
        super("Placement illégal : le mot ne peut pas être placé à cet endroit.");
    }
}
