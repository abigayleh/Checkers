package Checkers;

public class Piece {
	
	private int xPos;
	private int yPos;
	private char player;
	
	public Piece(int xPos, int yPos, char player) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.player = player;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public char getPlayer() {
		return player;
	}
	
	// Update x and y position of piece
	public void Move(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	public void becomeKing() {
		if(player == 'o')
			player = 'O';
		else if(player == 'x')
			player = 'X';
	}

}
