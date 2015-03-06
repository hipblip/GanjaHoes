import java.util.*;

/*
 * TODO FUCKING REDO EVERYTHING WITH MVC
 */

public class Driver {
	private enum gameType {PVP, PVE};
	
	private static int _BoardX = 6;
	private static int _BoardY = 6;
	private static char[][] gameBoard;
	
	// When turn is even it's white's turn, when turn is odd it's black's turn
	private static int turn = 0;
	private static gameType playMode;
	private static Scanner sc = new Scanner(System.in);
	
	// White is X
	// Black is O
	
	public class Vector2
	{
		public int x;
		public int y;
		
		public Vector2(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return x + ", " + y;
		}
	}
	
	public class PlayablePair
	{
		public Vector2 spot1;
		public Vector2 spot2;
		
		public PlayablePair(Vector2 v1, Vector2 v2)
		{
			spot1 = v1;
			spot2 = v2;
		}
		
		public boolean containsVector(Vector2 vec)
		{
			return (spot1 == vec || spot2 == vec);
		}
		
		public String toString()
		{
			return spot1.x + ", " + spot1.y + " and " + spot2.x + ", " + spot2.y;
		}
	}
	
	public Driver()
	{
		initBoard();		
		intro();
		//gamePause();
	}
	
	public static void main(String[] args) 
	{
		Driver d = new Driver();
		
		while (d.checkIfCanPlay(turn+1))
		{
			d.printBoard();
			d.makeMove(turn);

		}
		
		d.printBoard();
		
		if (turn % 2 == 0) 
		{
			System.out.println("O's win!!");
		}
		else
		{
			System.out.println("X's win!!");
		}
		d.exit();
	}
	
	void checkAddNewPlayable(PlayablePair p, ArrayList pairs)
	{
		for (int i = 0; i < pairs.size(); i++)
		{
			PlayablePair cur = (PlayablePair) pairs.get(i);
			
			if (cur.containsVector(p.spot1) && cur.containsVector(p.spot2)) //duplicate spot, don't add it
			{
				return;
			}
		}
		
		pairs.add(p);
	}
	
	boolean checkIfCanPlay(int specTurn)
	{
		ArrayList list = new ArrayList<Vector2>();
		
		for (int i = 0; i < _BoardX; i++) // gather all the empty spots
		{
			for (int j = 0; j < _BoardY; j++) 
			{
				Vector2 vec = new Vector2(i,j);

				if (gameBoard[i][j] == ' ' && !list.contains(vec))
				{
					list.add(vec);
				}
			}
		}
		
		ArrayList pairs = new ArrayList<PlayablePair>();
		
		if (specTurn % 2 == 0) // white's turn, check all spots
		{
			for (int i = 0; i < list.size(); i++)
			{
				for (int j = i; j < list.size(); j++)
				{
					Vector2 sp1 = (Vector2) list.get(i);
					Vector2 sp2 = (Vector2) list.get(j);
					
					if ((sp2.y == sp1.y - 1 || sp2.y == sp1.y + 1) && sp1.x ==sp2.x)
					{
						checkAddNewPlayable(new PlayablePair(new Vector2(sp1.x, sp1.y), new Vector2(sp2.x, sp2.y)), pairs);
					}
				}
			}
		}
		else
		{
			for (int i = 0; i < list.size(); i++)
			{
				for (int j = i; j < list.size(); j++)
				{
					Vector2 sp1 = (Vector2) list.get(i);
					Vector2 sp2 = (Vector2) list.get(j);
					
					if ((sp2.x == sp1.x - 1 || sp2.x == sp1.x + 1)&& sp1.y == sp2.y)
					{
						checkAddNewPlayable(new PlayablePair(new Vector2(sp1.x, sp1.y), new Vector2(sp2.x, sp2.y)), pairs);
					}
				}
			}
		}
		
		
		//printPlays(pairs);
		
		return pairs.size() > 0;
	}
	
	void printPlays(ArrayList plays)
	{
		for (int i = 0; i < plays.size(); i++)
		{
			System.out.println((PlayablePair) plays.get(i));
		}
	}
	
	void intro()
	{
		
		int response;
		
		System.out.println("Welcome!!\n");
		
		System.out.println("Would you like to:\n1. Play against another person\n2. Play against the computer");
		System.out.print("Response: ");
		response = sc.nextInt();
		sc.nextLine();
		while (response != 1 && response != 2) 
		{
			System.out.println("Please enter a valid option...");
			System.out.println("1. Play against another person\n2. Play against the computer");
			System.out.print("Response: ");
			response = sc.nextInt();
			sc.nextLine();
		}
		
		playMode = gameType.values()[response - 1];
		
		System.out.println("Playmode: " + playMode);
		
	}
		
	void exit() 
	{
		sc.close();
		System.out.println("Exiting game without error");
		System.exit(0);
	}
	
	void gamePause() 
	{
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}
	
	void makeMove(int turn) 
	{
		String pos1;
		String pos2;

		if (turn%2 == 0) // White's turn
		{	
			do {
				System.out.print("X's turn\nPlease enter your next move\n1: ");
				pos1 = sc.nextLine();
			} while (!legalWhiteMove(pos1));
			
			char pos1Let = pos1.charAt(0);
			//char pos2Let = pos2.charAt(0);
			int x1 = pos1Let - 65;
			//int x2 = pos2Let - 65;
			int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
			//int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
			gameBoard[x1][y1] = 'X';
			if (x1-1 > 0 && gameBoard[x1-1][y1] == ' ')
			{
				gameBoard[x1-1][y1] = 'X';
			}
			else if (x1+1 < _BoardX && gameBoard[x1+1][y1] == ' ')
			{
				gameBoard[x1+1][y1] = 'X';
			}
			else
			{
				System.err.println("ERROR: illegal move");
			}
			
			
		}
		else // Black's turn
		{
			do {
				System.out.print("O's turn\nPlease enter your next move:\n1: ");
				pos1 = sc.nextLine();;
			} while (!legalBlackMove(pos1));
			
			char pos1Let = pos1.charAt(0);
			int x1 = pos1Let - 65;
			int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;

			gameBoard[x1][y1] = 'O';
			if (y1+1 < _BoardY && gameBoard[x1][y1+1] == ' ')
			{
				gameBoard[x1][y1+1] = 'O';
			}
			else if (y1-1 > 0 && gameBoard[x1][y1-1] == ' ')
			{
				gameBoard[x1][y1-1] = 'O';
			}
			else
			{
				System.err.println("ERROR: illegal move");
			}
			
		}
		
		passTurn();
	}
	
	
	boolean legalWhiteMove(String pos1) 
	{
		if (pos1.length() != 2)
			return false;
		
		char pos1Let = pos1.charAt(0);
		//char pos2Let = pos2.charAt(0);
		int x1 = pos1Let - 65;
		//int x2 = pos2Let - 65;
		int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
		//int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
		
		if (x1 > _BoardX || x1 < 0) // The X value is off the board
		{
			return false;
		}
		if (y1 > _BoardY || y1 < 0) // The Y value is off the board
		{
			return false;
		}
		if (gameBoard[x1][y1] != ' ') // The position isn't open
		{
			return false;
		}
		if (x1 != 0 && x1 != _BoardX - 1) // The positions above and below the invalid
		{
			if (gameBoard[x1+1][y1] != ' ' && gameBoard[x1-1][y1] != ' ')
			{
				return false;
			}
		}
		if (x1 == 0) 
		{
			if (gameBoard[x1 +1][y1] != ' ') 
			{
				return false;
			}
		}
		else if (x1 ==  _BoardX - 1) 
		{
			if (gameBoard[x1-1][y1] != ' ')
			{
				return false;
			}
		}
		
		
		return true;
	}

	
	/*
	 * Holy if-else statements, batman!
	 */
	boolean legalBlackMove(String pos1) 
	{
		if (pos1.length() != 2)
			return false;
		
		char pos1Let = pos1.charAt(0);
		//char pos2Let = pos2.charAt(0);
		int x1 = pos1Let - 65;
		//int x2 = pos2Let - 65;
		int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
		//int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
		
		if (x1 > _BoardX || x1 < 0) // The X value is off the board
		{
			return false;
		}
		if (y1 > _BoardY || y1 < 0) // The Y value is off the board
		{
			return false;
		}
		if (gameBoard[x1][y1] != ' ') // The position isn't open
		{
			return false;
		}
		if (y1 != 0 && y1 != _BoardY - 1) // The positions above and below the invalid
		{
			if (gameBoard[x1][y1+1] != ' ' && gameBoard[x1][y1-1] != ' ')
			{
				return false;
			}
		}
		if (y1 == 0) 
		{
			if (gameBoard[x1][y1+1] != ' ') 
			{
				return false;
			}
		}
		else if (y1 ==  _BoardY - 1) 
		{
			if (gameBoard[x1][y1-1] != ' ')
			{
				return false;
			}
		}
		
		
		return true;
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
	
	static void passTurn() 
	{
		turn++;
	}

	/*
	 *===============
	 * Gets and Sets
	 *===============
	 */
	public int getTurn() 
	{
		return turn;
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

}
