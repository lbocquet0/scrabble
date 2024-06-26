package scrabble.model.token;

// Scrabble french letters with points
public enum FrenchLetter {
	E(1, 15), A(1, 9), I(1, 8), N(1, 6), R(1, 6),
	T(1, 6), O(1, 6), U(1, 6), L(1, 5), S(1, 6), 
	D(2, 3), M(2, 3), G(2, 2), B(3, 2), C(3, 2),
	P(3, 2), F(4, 2), H(4, 2), V(4, 2), J(8, 1),
	Q(8, 1), K(10, 1), W(10, 1), X(10, 1), 
	Y(10, 1), Z(10, 1);

	private final int point;
	private final int occurencesAmount;

	FrenchLetter(int point, int occurencesAmount) {
		this.point = point;
		this.occurencesAmount = occurencesAmount;
	}

	public int getPoint() {
		return point;
	}

	public int getOccurencesAmount() {
		return occurencesAmount;
	}
	
	public String display() {
        return this.name() + " " + this.getPoint();
    }
}