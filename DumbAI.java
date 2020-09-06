package Checkers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DumbAI implements AI {

	public Move chooseMove(ArrayList<Move> possibleMoves) {
		
		// Randomly selects a move from the possibleMoves array
		int index = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
		return possibleMoves.get(index);
	}
}
