package Checkers;

public class Move {
	
	private int newX;
	private int newY;
	private Piece piece;
	
	public Move(int x, int y, Piece piece) {
		this.newX = x;
		this.newY = y;
		this.piece = piece;
	}
	
	public int getX() {
		return newX;
	}
	
	public int getY() {
		return newY;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	
}
