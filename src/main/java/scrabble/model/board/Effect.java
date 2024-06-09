package scrabble.model.board;

public enum Effect {
	DOUBLE_LETTER("Lettre x2", "#FFD700"),
	TRIPLE_LETTER("Lettre x3", "#FFA500"),
	DOUBLE_WORD("Mot x2", "#FF6347"),
	TRIPLE_WORD("Mot x3", "#FF4500"),
	NORMAL;

	private String content;
	private String backgroundColor;

	private Effect(String content, String backgroundColor) {
		this.content = content;
		this.backgroundColor = backgroundColor;
	}

	private Effect() {
		this(null, null);
	}

	public String content() {
		return this.content;
	}

	public String backgroundColor() {
		return this.backgroundColor;
	}
}