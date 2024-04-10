package scrabble.model;

// Scrabble french letters with points
public enum Letter {
	E(1), A(1), I(1), N(1), O(1),
	R(1), S(1), T(1), U(1), L(1),
	D(2), M(2), G(2), B(3), C(3),
	P(3), F(4), H(4), V(4), J(8),
	Q(8), K(10), W(10), X(10), Y(10),
	Z(10);

	private final int point;

	Letter(int point) {
		this.point = point;
	}

	public int getPoint() {
		return point;
	}
}