package Checkers;

import java.util.ArrayList;

public class Game {
	
	private Board board = new Board();
	private String gameStatus;
	private AI ai;
	private ArrayList<Piece> xPieces;
	private ArrayList<Piece> oPieces;
	
	public Game(ArrayList<Piece> xPieces, ArrayList<Piece> oPieces, String gameStatus) {
		this.xPieces = xPieces;
		this.oPieces = oPieces;
		this.gameStatus = gameStatus;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public String gameStatus() {
		return gameStatus;
	}
	
	// Prints board onto console
	public void printGameBoard() {
		char[][] charBoard = board.getCharBoard();
		for(int x = 0; x < 18; x++) {
			for(int y = 0; y < 34; y++) {
				System.out.print(charBoard[x][y]);
			}
			System.out.println();
		}
	}
	
	// Searches pieces array for piece with given x and y position
	public Piece findPiece(int x, int y) {
		for(int i = 0; i < oPieces.size(); i++) {
			if(oPieces.get(i).getX() == x && oPieces.get(i).getY() == y)
				return oPieces.get(i);
		}
		
		for(int i = 0; i < xPieces.size(); i++) {
			if(xPieces.get(i).getX() == x && xPieces.get(i).getY() == y)
				return xPieces.get(i);
		}
		
		return null;
	}
	
	// Deletes pieces that have been jumped over
	public void updatePieces(Move current, Move move) {
		
		int jumpPieceX;
		int jumpPieceY;
		
		// Delete checkers that are jumped over
		int jumpCurrentX = current.getX();
		int jumpCurrentY = current.getY();
		
		if(Math.abs(move.getY() - current.getY()) > 1) {
			while(move.getY() != jumpCurrentY) {
				
				// Get x value
				if(jumpCurrentX > move.getX()) {
					jumpPieceX = jumpCurrentX - 1;
					jumpCurrentX = jumpPieceX - 1;
				}
				else {
					jumpPieceX = jumpCurrentX + 1;
					jumpCurrentX = jumpPieceX + 1;
				}
					
				// Get y value
				if(jumpCurrentY > move.getY()) {
					jumpPieceY = jumpCurrentY - 1;
					jumpCurrentY = jumpPieceY - 1;
				}
				else {
					jumpPieceY = jumpCurrentY + 1;
					jumpCurrentY = jumpPieceY + 1;
				}
				
				if(current.getPiece().getPlayer() == 'o' || current.getPiece().getPlayer() == 'O')
					xPieces.remove(findPiece(jumpPieceX, jumpPieceY));
				else
					oPieces.remove(findPiece(jumpPieceX, jumpPieceY));
			}
		}
	}
	
	// Finds piece that is placed on board at location x, y
	public char isEmpty(int x, int y) {
		for(int i = 0; i < oPieces.size(); i++) {
			if(oPieces.get(i).getX() == x && oPieces.get(i).getY() == y)
				return 'O';
		}
		
		for(int i = 0; i < xPieces.size(); i++) {
			if(xPieces.get(i).getX() == x && xPieces.get(i).getY() == y)
				return 'X';
		}
		
		return 'E';
	}
	
	// When either O or X pieces are all gone, game will be won
	public void updateGameStatus() {
		if(oPieces.size() == 0)
			gameStatus = "xWinner";
		
		else if(xPieces.size() == 0) 
			gameStatus = "oWinner";
		
		else
			gameStatus = "inProgress";
	}
	
	// Collects all possible moves of a given piece
	public ArrayList<Move> possibleMoves(Piece piece, int jump, Move lastMove){
		
		char name = piece.getPlayer();
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		int x = piece.getX();
		int y = piece.getY();
		
		if(jump == 0) {
			// Move left
			if(x > 1) {
				x--;
				// Move down
				if(name != 'x') {
					if(y < 8) {
						y++;
						if(isEmpty(x,y) == 'E')
							possibleMoves.add(new Move(x, y, piece));
						else if(isEmpty(x,y) == 'X' && name == 'X' || isEmpty(x,y) == 'O' && name == 'X') 
							possibleMoves.addAll(possibleMoves(piece, jump+1, null));
						y--;
					}
				}		
				// Move up
				if(name != 'o') {
					if(y > 1) {
						y--;
						if(isEmpty(x,y) == 'E')
							possibleMoves.add(new Move(x, y, piece));
						else if(isEmpty(x,y) == 'O' && name == 'O' || isEmpty(x,y) == 'X' && name == 'O') 
							possibleMoves.addAll(possibleMoves(piece, jump+1, null));
						y++;
					}
				}
				x++;
			}
			// Move right
			if(x < 8) {
				x++;
				// Move down
				if(name != 'x') {
					if(y < 8) {
						y++;
						if(isEmpty(x,y) == 'E')
							possibleMoves.add(new Move(x, y, piece));
						else if(isEmpty(x,y) == 'X' && name != 'X' || isEmpty(x,y) == 'O' && name == 'X') 
							possibleMoves.addAll(possibleMoves(piece, jump+1, null));
						y--;
					}
				}
				// Move up
				if(name != 'o') {
					if(y > 1) {
						y--;
						if(isEmpty(x,y) == 'E')
							possibleMoves.add(new Move(x, y, piece));
						else if(isEmpty(x,y) == 'O' && name != 'O' || isEmpty(x,y) == 'X' && name == 'O') 
							possibleMoves.addAll(possibleMoves(piece, jump+1, null));
					}
				}
			}
		}
		
		// Jump a piece
		else if(jump < 4) {
			// Move down
			if(name != 'x') {
				y = y + (jump*2);
				// Ensure it is in bounds
				if(y < 9) {
					
					// Move left
					x = x - (jump*2);
					// Ensure it is in bounds
					if(x > 0) {
						if(isEmpty(x,y) == 'E') {
							if(isEmpty(x+1,y-1) == 'X' && piece.getPlayer() != 'X') {
								possibleMoves.add(new Move(x, y, piece));
								possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
							}
							else if(isEmpty(x+1,y-1) == 'O' && piece.getPlayer() == 'X'){
								possibleMoves.add(new Move(x, y, piece));
								possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
							}
						}
					}
					
					x = piece.getX();
					
					// Move right
					x = x + (jump*2);
					// Ensure it is in bounds
					if(x < 9) {
						if(isEmpty(x,y) == 'E') {
							if(isEmpty(x-1,y-1) == 'X' && piece.getPlayer() != 'X') {
								possibleMoves.add(new Move(x, y, piece));
								possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
							}
							else if(isEmpty(x-1,y-1) == 'O' && piece.getPlayer() == 'X') {
								possibleMoves.add(new Move(x, y, piece));
								possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
							}
						}
					}
					
					x = piece.getX();
					
					if(jump == 2) {
						if(isEmpty(x,y) == 'E') {
							if(lastMove.getX() > piece.getX()) {
								if(isEmpty(x-1,y-1) == 'X' && piece.getPlayer() != 'X') {
									possibleMoves.add(new Move(x, y, piece));
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
								else if(isEmpty(x-1,y-1) == 'O' && piece.getPlayer() == 'X') {
									possibleMoves.add(new Move(x, y, piece));
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
							}
							else {
								if(isEmpty(x+1,y-1) == 'X' && piece.getPlayer() != 'X') {
									possibleMoves.add(new Move(x, y, piece));
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
								else if(isEmpty(x+1,y-1) == 'O' && piece.getPlayer() == 'X') {
									possibleMoves.add(new Move(x, y, piece));
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
							}
						}
					}

					else if(jump == 3) {
						x = x + 2;
						if(x < 9) {
							if(isEmpty(x,y) == 'E') {
								if(isEmpty(x-1,y-1) == 'X' && piece.getPlayer() != 'X') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
								else if(isEmpty(x-1,y-1) == 'O' && piece.getPlayer() == 'X') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
							}
						}
						
						x = x - 4;
						if(x > 0) {
							if(isEmpty(x,y) == 'E') {
								if(isEmpty(x+1,y-1) == 'X' && piece.getPlayer() != 'X') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
								else if(isEmpty(x+1,y-1) == 'O' && piece.getPlayer() == 'X') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
									
							}
						}
					}
				}
			}
			
			y = piece.getY();
			x = piece.getX();
			
			// Move up
			if(name != 'o') {
				y = y - (jump*2);
				// Ensure it is in bounds
				if(y > 0) {
					
					// Move left
					x = x - (jump*2);
					// Ensure it is in bounds
					if(x > 0) {
						if(isEmpty(x,y) == 'E') {
							if(isEmpty(x+1,y+1) == 'X' && piece.getPlayer() == 'O') {
								possibleMoves.add(new Move(x, y, piece));
								possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
							}
							else if(isEmpty(x+1,y+1) == 'O' && piece.getPlayer() != 'O'){
								possibleMoves.add(new Move(x, y, piece));
								possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
							}
						}
					}
					
					x = piece.getX();
					
					// Move right
					x = x + (jump*2);
					// Ensure it is in bounds
					if(x < 9) {
						if(isEmpty(x,y) == 'E') {
							if(isEmpty(x-1,y+1) == 'X' && piece.getPlayer() == 'O') {
								possibleMoves.add(new Move(x, y, piece)); 
								possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
							}
							else if(isEmpty(x-1,y+1) == 'O' && piece.getPlayer() != 'O') {
								possibleMoves.add(new Move(x, y, piece)); 
								possibleMoves.addAll(possibleMoves(piece, jump+1,possibleMoves.get(possibleMoves.size()-1)));
							}
						}
					}
					
					x = piece.getX();
					
					if(jump == 2) {
						if(isEmpty(x,y) == 'E') {
							if(lastMove.getX() > piece.getX()) {
								if(isEmpty(x-1,y+1) == 'X' && piece.getPlayer() == 'O') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
								else if(isEmpty(x-1,y+1) == 'O' && piece.getPlayer() != 'O') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
							}
							else {
								if(isEmpty(x+1,y+1) == 'X' && piece.getPlayer() == 'O') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
								else if(isEmpty(x+1,y+1) == 'O' && piece.getPlayer() != 'O') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
							}
						}
					}
					else if(jump == 3) {
						x = x + 2;
						if(x < 9) {
							if(isEmpty(x,y) == 'E') {
								if(isEmpty(x-1,y+1) == 'X' && piece.getPlayer() == 'O') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
								else if(isEmpty(x-1,y+1) == 'O' && piece.getPlayer() != 'O') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
							}
						}
						
						x = x - 4;
						if(x > 0) {
							if(isEmpty(x,y) == 'E') {
								if(isEmpty(x+1,y+1) == 'X' && piece.getPlayer() == 'O') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
								else if(isEmpty(x+1,y+1) == 'O' && piece.getPlayer() != 'O') {
									possibleMoves.add(new Move(x, y, piece)); 
									possibleMoves.addAll(possibleMoves(piece, jump+1, possibleMoves.get(possibleMoves.size()-1)));
								}
							}
						}
					}
				}
			}
		}	
		
		return possibleMoves;
	}
}
