package Checkers;

import java.util.ArrayList;

public class SmartAI implements AI {

	public Move chooseMove(ArrayList<Move> possibleMoves) {
		
			// Move with largest y difference will have jumped the most pieces
			int yDifference = Math.abs(possibleMoves.get(0).getPiece().getY() - possibleMoves.get(0).getY());
			
			Move moveAI = possibleMoves.get(0);
			for(int i = 1; i < possibleMoves.size(); i++) {
				if(Math.abs((possibleMoves.get(i).getY() - possibleMoves.get(i).getPiece().getY())) > yDifference) {
					moveAI = possibleMoves.get(i);
				}
			}
			return moveAI;
	}

}
