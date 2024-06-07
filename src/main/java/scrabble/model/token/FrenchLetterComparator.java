package scrabble.model.token;

import java.util.Comparator;

public class FrenchLetterComparator implements Comparator<FrenchLetter> {

	@Override
	public int compare(FrenchLetter l1, FrenchLetter l2) {
		return l1.name().compareTo(l2.name());
	}
}