package Checkers;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleRunner {
	
	// For user input
	private Scanner scanner = new Scanner(System.in);
	private Scanner scan = new Scanner(System.in);
	private Scanner scan1 = new Scanner(System.in);
	
	// Move holders
	private int currentX;
	private int currentY;
	private int moveX;
	private int moveY;
	Move xCurrent;
	Move xMove;
	private Piece currentPiece;
	
	boolean invalidInput;
	boolean AI;
	boolean hard;
	boolean showMoves;
	AI ai;
	
	// List of possible moves for each piece
	ArrayList<Move> oMoves = new ArrayList<Move>();
	ArrayList<Move> xMoves = new ArrayList<Move>();
	
	public ConsoleRunner() {
	}
	
	public void mainLoop() {
		
		// Pieces on board
		ArrayList<Piece> oPieces = new ArrayList<Piece>();
		ArrayList<Piece> xPieces = new ArrayList<Piece>();
		
		//O pieces
		oPieces.add(new Piece(2, 1, 'o'));
		oPieces.add(new Piece(4, 1, 'o'));
		oPieces.add(new Piece(6, 1, 'o'));
		oPieces.add(new Piece(8, 1, 'o'));
		oPieces.add(new Piece(1, 2, 'o'));
		oPieces.add(new Piece(3, 2, 'o'));
		oPieces.add(new Piece(5, 2, 'o'));
		oPieces.add(new Piece(7, 2, 'o'));
		oPieces.add(new Piece(2, 3, 'o'));
		oPieces.add(new Piece(4, 3, 'o'));
		oPieces.add(new Piece(6, 3, 'o'));
		oPieces.add(new Piece(8, 3, 'o'));
		
		//X pieces
		xPieces.add(new Piece(1, 6, 'x'));
		xPieces.add(new Piece(3, 6, 'x'));
		xPieces.add(new Piece(5, 6, 'x'));
		xPieces.add(new Piece(7, 6, 'x'));
		xPieces.add(new Piece(2, 7, 'x'));
		xPieces.add(new Piece(4, 7, 'x'));
		xPieces.add(new Piece(6, 7, 'x'));
		xPieces.add(new Piece(8, 7, 'x'));
		xPieces.add(new Piece(1, 8, 'x'));
		xPieces.add(new Piece(3, 8, 'x'));
		xPieces.add(new Piece(5, 8, 'x'));
		xPieces.add(new Piece(7, 8, 'x'));
		
		System.out.println("Welcome to Checkers!");
		System.out.println("Would you like to play as 1 or 2 players [1/2]");
		if(scanner.nextInt() == 1) 
			AI = true;
		else 
			AI = false;
		
		if(AI) {
			System.out.println("Would you like to play on hard difficulty?[Y/N]");
			hard = scan.hasNext("Y");
			hard = scan.hasNext("y");
			if(hard) 
				ai = new SmartAI();
			else 
				ai = new DumbAI();
		}
		
		System.out.println("Would you like the possible moves to be shown for you? [Y/N]");
		showMoves = scan1.hasNext("Y");
		showMoves = scan1.hasNext("y");
		
		Game game = new Game(xPieces, oPieces, "inProgress");
		game.printGameBoard();
		
		while(game.gameStatus() == "inProgress") {
			System.out.println();
			//Player O turn			
			System.out.println("Player O turn: ");
			
			// Collect all possible turns for o pieces
			oMoves.clear();
			for(int i = 0; i < oPieces.size(); i++) {
				oMoves.addAll(game.possibleMoves(oPieces.get(i), 0, null));
			}
			
			if(oMoves.size() == 0) 
				System.out.println("No possible turns!");
			
			else {
				System.out.println();
				
				// Show possible moves
				if(showMoves) {
					System.out.println("Possible moves: ");
					for(int i = 0; i < oMoves.size(); i++) {
						System.out.println("Piece " + "[" + oMoves.get(i).getPiece().getX() + "," + oMoves.get(i).getPiece().getY() + "]" 
								+ " to location " + "[" + oMoves.get(i).getX() + "," + oMoves.get(i).getY() + "]");
					}
				}
				
				// Get location of piece to be moved from user
				System.out.println("Input the x of the piece you will be moving");
				currentX = scanner.nextInt();
				invalidInput = true;
				while(invalidInput) {
					for(int i = 0; i < oMoves.size(); i++) {
						if(oMoves.get(i).getPiece().getX() == currentX) {
							invalidInput = false;
							break;
						}
					}
					if(invalidInput) {
						System.out.println("Incorrect input. Enter the x value of the piece to be moved");
						currentX = scanner.nextInt();
					}
				}
				
				System.out.println("Input the y of the piece you will be moving");
				currentY = scanner.nextInt();
				invalidInput = true;
				while(invalidInput) {
					for(int i = 0; i < oMoves.size(); i++) {
						if(oMoves.get(i).getPiece().getY() == currentY) {
							invalidInput = false;
							break;
						}
					}
					if(invalidInput) {
						System.out.println("Incorrect input. Enter the y value of the piece to be moved");
						currentY = scanner.nextInt();
					}
				}
				
				// Get location of where piece will be moved from user
				System.out.println("Input the x of where you would like to move");
				moveX = scanner.nextInt();
				invalidInput = true;
				while(invalidInput) {
					for(int i = 0; i < oMoves.size(); i++) {
						if(oMoves.get(i).getX() == moveX) {
							invalidInput = false;
							break;
						}
					}
					if(invalidInput) {
						System.out.println("Incorrect input. Enter the x value of where the piece will move");
						moveX = scanner.nextInt();
					}
				}
				
				System.out.println("Input the y of where you would like to move");
				moveY = scanner.nextInt();
				invalidInput = true;
				while(invalidInput) {
					for(int i = 0; i < oMoves.size(); i++) {
						if(oMoves.get(i).getY() == moveY) {
							invalidInput = false;
							break;
						}
					}
					if(invalidInput) {
						System.out.println("Incorrect input. Enter the y value of where the piece will move");
						moveY = scanner.nextInt();
					}
				}
				
				// Update pieces in array
				currentPiece = game.findPiece(currentX, currentY);
				Move current = new Move(currentX, currentY, currentPiece);
				Move move = new Move(moveX, moveY, currentPiece);
				game.updatePieces(current, move);

				// Update board and print it
				game.getBoard().updateBoard(current, move);
				game.printGameBoard();
				currentPiece.Move(moveX, moveY);
				
				// Check if game is over
				game.updateGameStatus();
				if(game.gameStatus() != "inProgress")
					break;
			}
			
			// Player X turn
			System.out.println();
			System.out.println("Player X turn: ");
			System.out.println();
			
			// Collect all possible moves for x peices
			xMoves.clear();
			for(int i = 0; i < xPieces.size(); i++) {
				xMoves.addAll(game.possibleMoves(xPieces.get(i), 0, null));
			}
			if(xMoves.size() == 0) 
				System.out.println("No possible turns!");
			
			else {
				if(AI) {
					xMove = ai.chooseMove(xMoves);
					currentPiece = xMove.getPiece();
					xCurrent = new Move(currentPiece.getX(), currentPiece.getY(), currentPiece);
				}
				else {
					if(showMoves) {
						System.out.println("Possible moves: ");
						for(int i = 0; i < xMoves.size(); i++) {
							System.out.println("Piece " + "[" + xMoves.get(i).getPiece().getX() + "," + xMoves.get(i).getPiece().getY() + "]" 
									+ " to location " + "[" + xMoves.get(i).getX() + "," + xMoves.get(i).getY() + "]");
						}
					}
					
					System.out.println();
					System.out.println();
					
					//Get location of piece to be moved from user
					System.out.println("Input the x of the piece you will be moving");
					currentX = scanner.nextInt();
					invalidInput = true;
					while(invalidInput) {
						for(int i = 0; i < xMoves.size(); i++) {
							if(xMoves.get(i).getPiece().getX() == currentX) {
								invalidInput = false;
								break;
							}
						}
						if(invalidInput) {
							System.out.println("Incorrect input. Enter the x value of the piece you will be moving");
							currentX = scanner.nextInt();
						}
					}
					
					System.out.println("Input the y of the piece you will be moving");
					currentY = scanner.nextInt();
					invalidInput = true;
					while(invalidInput) {
						for(int i = 0; i < xMoves.size(); i++) {
							if(xMoves.get(i).getPiece().getY() == currentY) {
								invalidInput = false;
								break;
							}
						}
						if(invalidInput) {
							System.out.println("Incorrect input. Enter the y value of the piece you will be moving");
							currentY = scanner.nextInt();
						}
					}
					
					// Get location of where to move piece from user
					System.out.println("Input the x of where you would like to move");
					moveX = scanner.nextInt();
					invalidInput = true;
					while(invalidInput) {
						for(int i = 0; i < xMoves.size(); i++) {
							if(xMoves.get(i).getX() == moveX) {
								invalidInput = false;
								break;
							}
						}
						if(invalidInput) {
							System.out.println("Incorrect input. Enter the x value of where the piece will move");
							moveX = scanner.nextInt();
						}
					}
					
					System.out.println("Input the y of where you would like to move");
					moveY = scanner.nextInt();
					invalidInput = true;
					while(invalidInput) {
						for(int i = 0; i < xMoves.size(); i++) {
							if(xMoves.get(i).getY() == moveY) {
								invalidInput = false;
								break;
							}
						}
						if(invalidInput) {
							System.out.println("Incorrect input. Enter the y value of where the piece will move");
							moveY = scanner.nextInt();
						}
					}
					
					// Moves of chosen piece
					currentPiece = game.findPiece(currentX, currentY);
					xCurrent = new Move(currentX, currentY, currentPiece);
					xMove = new Move(moveX, moveY, currentPiece);
				}
				
				// Update game board and pieces array
				game.updatePieces(xCurrent, xMove);
				game.getBoard().updateBoard(xCurrent, xMove);
				game.printGameBoard();
				currentPiece.Move(xMove.getX(), xMove.getY());
			}
			// Check game status
			game.updateGameStatus();
		}
		
		if(game.gameStatus() == "xWinner")
			System.out.println("Congratulation player X, you have won!");
		else 
			System.out.println("Congratulation player O, you have won!");
	}
}
