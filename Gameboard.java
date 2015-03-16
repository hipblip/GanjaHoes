import java.util.ArrayList;
import java.util.Scanner;

public class Gameboard {
	public enum gameType {PVP, PVE};
	
	private static Gameboard instance = null;
	private char[][] gameBoard;
	
	private int _BoardX = 8;
	private int _BoardY = 8;
	
	private int turn;
	
	private gameType playMode;
	
	private Scanner sc = new Scanner(System.in);
	
	private Coord lastMove;
	private Minimax ai;
	private int minMaxDepth = 3;
	
	private Gameboard() 
	{		
		initBoard();
		ai = new Minimax(minMaxDepth);
	}
	
	/*
	 * Initializes the game board to the given dimensions
	*/
	void initBoard() 
	{
		gameBoard = new char[_BoardX][_BoardY];
		for (int i = 0; i < _BoardX; i++) 
		{
			for (int j = 0; j < _BoardY; j++) 
			{
				gameBoard[i][j] = ' ';
			}
		}
	}
	
	/*
	 * Prints the current game board.
	 */
	void printBoard()
	{
		System.out.printf("%-5c", ' ');
		for (int i = 1; i <= _BoardY; i++) {
			System.out.printf("%-5d", i);
		}
		System.out.println();
		for (int i = 0; i < _BoardX; i++)
		{
			System.out.printf("%-5c", (char)(i+65));
			for (int j = 0; j < _BoardY; j ++) {
				System.out.printf("%-5c", gameBoard[i][j]);
			}
			System.out.println();
		}
	}
	
	void makeMove(int turn) 
	{
		String pos1 = "";

		if (turn%2 == 0) // White's turn
		{	
			do {
				System.out.print("X's turn\nPlease enter your next move\n1: ");
				pos1 = sc.nextLine();
			} while (!legalWhiteMove(pos1, gameBoard));
			
			try
			{
				lastMove = Coord.convertToCoord(pos1);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			
			char pos1Let = pos1.charAt(0);
			int x1 = pos1Let - 65;
			int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;

			gameBoard[x1][y1] = 'X';
			if (x1+1 < _BoardX && gameBoard[x1+1][y1] == ' ')
			{
				gameBoard[x1+1][y1] = 'X';
			}
			else if (x1-1 >= 0 && gameBoard[x1-1][y1] == ' ')
			{
				System.out.println("adf");
				gameBoard[x1-1][y1] = 'X';
			}
			else
			{
				System.out.println("ERROR: illegal move");
			}
			
			
		}
		else // Black's turn
		{
			if (playMode == gameType.PVP) {
				do {
					System.out.print("O's turn\nPlease enter your next move:\n1: ");
					pos1 = sc.nextLine();
				} while (!legalBlackMove(pos1, gameBoard));
			}
			else if (playMode == gameType.PVE)
			{
				//System.out.println("heuristic");
				lastMove.setY(lastMove.getY()+2);
				ai.alphaBeta(new Node(lastMove), Integer.MIN_VALUE, Integer.MAX_VALUE, ai.getDepth(), true);
				
				pos1 = ai.getBestMove().getCoord().toString();
				
			}
			else 
			{
				System.out.println("ERROR: Invalid game type.");
			}
			
			char pos1Let = pos1.charAt(0);
			int x1 = pos1Let - 65;
			int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;

			gameBoard[x1][y1] = 'O';
			if (y1+1 < _BoardY && gameBoard[x1][y1+1] == ' ')
			{
				gameBoard[x1][y1+1] = 'O';
			}
			else if (y1-1 >= 0 && gameBoard[x1][y1-1] == ' ')
			{
				gameBoard[x1][y1-1] = 'O';
			}
			else
			{
				//System.out.println(gameBoard[x1][y1-1]);
				System.out.println("ERROR: illegal move");
			}			
		}
		
		passTurn();
	}
	
	public static boolean legalWhiteMove(String pos1, char[][] gb) 
	{
		Coord c = new Coord();
		try {
			c = Coord.convertToCoord(pos1);
		} 
		catch(Exception e)
		{
			System.out.println("Please enter a valid position");
		}
		
		int x1 = c.getX();
		int y1 = c.getY();
		
		if (x1 > instance._BoardX || x1 < 0) // The X value is off the board
		{
			return false;
		}
		if (y1 > instance._BoardY || y1 < 0) // The Y value is off the board
		{
			return false;
		}
		if (gb[x1][y1] != ' ') // The position isn't open
		{
			return false;
		}
		if (x1 != 0 && x1 != instance._BoardX - 1) // The positions above and below the invalid
		{
			if (gb[x1+1][y1] != ' ' && gb[x1-1][y1] != ' ')
			{
				return false;
			}
		}
		if (x1 == 0) 
		{
			if (gb[x1 +1][y1] != ' ') 
			{
				return false;
			}
		}
		else if (x1 ==  instance._BoardX - 1) 
		{
			if (gb[x1-1][y1] != ' ')
			{
				return false;
			}
		}		
		
		return true;
	}

	
	/*
	 * Holy if-else statements, batman!
	 */
	public static boolean legalBlackMove(String pos1, char[][] gb) 
	{
		Coord c = new Coord();
		
		try {
			c = Coord.convertToCoord(pos1);
		} 
		catch(Exception e)
		{
			System.out.println("Please enter a valid position");
		}
		
		int x1 = c.getX();
		int y1 = c.getY();
		
		if (x1 > instance._BoardX || x1 < 0) // The X value is off the board
		{
			return false;
		}
		if (y1 > instance._BoardY || y1 < 0) // The Y value is off the board
		{
			return false;
		}
		if (gb[x1][y1] != ' ') // The position isn't open
		{
			return false;
		}
		if (y1 != 0 && y1 != instance._BoardY - 1) // The positions above and below the invalid
		{
			if (gb[x1][y1+1] != ' ' && gb[x1][y1-1] != ' ')
			{
				return false;
			}
		}
		if (y1 == 0) 
		{
			if (gb[x1][y1+1] != ' ') 
			{
				return false;
			}
		}
		else if (y1 ==  instance._BoardY - 1) 
		{
			if (gb[x1][y1-1] != ' ')
			{
				return false;
			}
		}
				
		return true;
	}
	

	

	

	
	void passTurn() 
	{
		turn++;
	}
	
	/*==============
	 * Gets and Sets
	 * =============
	 */
	
	public static Gameboard getInstance() 
	{
		if (instance == null) 
		{
			instance = new Gameboard();
		}
		return instance;
	}
	
	public int getBoardX() 
	{
		return _BoardX;
	}

	public void setBoardX(int x)
	{
		_BoardX = x;
	}

	public int getBoardY() 
	{
		return _BoardY;
	}

	public void setBoardY(int y)
	{
		_BoardY = y;
	}
	
	public int getTurn() 
	{
		return turn;
	}
	
	public char getCharAt(int x, int y) throws Exception
	{
		if (x < 0 || y < 0 || x > _BoardX || y > _BoardY)
		{
			throw new Exception();
		}
		return gameBoard[x][y];
	}
	
	public char[][] getBoard() 
	{
		char[][] gb = new char[_BoardX][_BoardY];
		for (int i = 0; i < gb.length; i++) 
		{
			for (int j = 0; j < gb[i].length; j++) 
			{
				gb[i][j] = gameBoard[i][j];
			}
		}		
		return gb;
	}
	
	public void setGameType(gameType gt) 
	{
		playMode = gt;
	}
}