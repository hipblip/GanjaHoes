import java.util.*;

/*
 * TODO FUCKING REDO EVERYTHING WITH MVC, MOTHERFUCKAZZZZZZ
 */

public class Driver {
	private enum gameType {PVP, PVE};
	
	private static int _BoardX = 8;
	private static int _BoardY = 8;
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
		
		while (d.checkIfCanPlay())
		{
			d.printBoard();
			d.makeMove(turn);
		}
		d.exit();
	}
	
	void checkAddNewPlayable(PlayablePair p, ArrayList pairs)
	{
		for (int i = 0; i < pairs.size(); i++)
		{
			PlayablePair cur = (PlayablePair) pairs.get(i);
			
			if (cur.containsVector(p.spot1) || cur.containsVector(p.spot2)) //duplicate spot, don't add it
			{
				return;
			}
		}
		
		pairs.add(p);
	}
	
	boolean checkIfCanPlay()
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
		
		if (turn % 2 == 0) // white's turn, check all spots
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
					
					if ((sp2.x == sp1.x - 1 || sp2.x == sp1.x + 1)&& sp1.x == sp2.x)
					{
						checkAddNewPlayable(new PlayablePair(new Vector2(sp1.x, sp1.y), new Vector2(sp2.x, sp2.y)), pairs);
					}
				}
			}
		}
		
		printPlays(pairs);
		
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
		
		System.out.println("Welcome to Ganja-Hoes!!\n");
		
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
				System.out.print("X's turn\nPlease enter 2 consecutive vertical positions:\n1: ");
				pos1 = sc.nextLine();
				System.out.print("2: ");
				pos2 = sc.nextLine();
			} while (!legalWhiteMove(pos1, pos2));
			
			char pos1Let = pos1.charAt(0);
			char pos2Let = pos2.charAt(0);
			int x1 = pos1Let - 65;
			int x2 = pos2Let - 65;
			int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
			int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
			gameBoard[x1][y1] = 'X';
			gameBoard[x2][y2] = 'X';
			
			//check;
//			if (legalWhiteMove(pos1, pos2)) 
//			{
//				char pos1Let = pos1.charAt(0);
//				char pos2Let = pos2.charAt(0);
//				int x1 = pos1Let - 65;
//				int x2 = pos2Let - 65;
//				int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
//				int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
//				gameBoard[x1][y1] = 'X';
//				gameBoard[x2][y2] = 'X';
//			}
			
		}
		else // Black's turn
		{
			do {
				System.out.print("O's turn\nPlease enter 2 consecutive horizontal positions:\n1: ");
				pos1 = sc.nextLine();
				System.out.print("2: ");
				pos2 = sc.nextLine();
			} while (!legalBlackMove(pos1, pos2));
			
			char pos1Let = pos1.charAt(0);
			char pos2Let = pos2.charAt(0);
			int x1 = pos1Let - 65;
			int x2 = pos2Let - 65;
			int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
			int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
			gameBoard[x1][y1] = 'X';
			gameBoard[x2][y2] = 'X';
			
//			if (legalBlackMove(pos1, pos2)) 
//			{
//				char pos1Let = pos1.charAt(0);
//				char pos2Let = pos2.charAt(0);
//				int x1 = pos1Let - 65;
//				int x2 = pos2Let - 65;
//				int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
//				int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
//				gameBoard[x1][y1] = 'O';
//				gameBoard[x2][y2] = 'O';
//			}
		}
		
		passTurn();
	}
	
	boolean legalBlackMove(String pos1, String pos2) 
	{
		if (pos1.length() != 2 || pos2.length() != 2)
			return false;
		
		char pos1Let = pos1.charAt(0);
		char pos2Let = pos2.charAt(0);
		int x1 = pos1Let - 65;
		int x2 = pos2Let - 65;
		int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
		int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
		
		if (x1 > _BoardX || x1 < 0 || x2 > _BoardX || x2 < 0)
		{
			return false;
		}
		if (y1 > _BoardY || y1 < 0 || y2 > _BoardY || y2 < 0)
		{
			return false;
		}
		
		if (gameBoard[x1][y1] != ' ' || gameBoard[x2][y2] != ' ' || x2 != x1) 
		{
			return false;
		}
		else 
		{
			if ((y1 == 0 && y2 != 1) || (y2 == 0 && y1 != 1)) 
			{
				return false;
			}
			else if ((y1 == _BoardY && y2 != _BoardY - 1) || (y2 == _BoardY && y1 != _BoardY - 1))
			{
				return false;
			}			
			else if (Math.abs(y1 - y2) > 1) 
			{
				return false;
			}
			else 
			{
				return true;
			}
		}
	}
	
	/*
	 * Holy if-else statements, batman!
	 */
	boolean legalWhiteMove(String pos1, String pos2) 
	{
		if (pos1.length() != 2 || pos2.length() != 2)
			return false;
		
		char pos1Let = pos1.charAt(0);
		char pos2Let = pos2.charAt(0);
		int x1 = pos1Let - 65;
		int x2 = pos2Let - 65;
		int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;
		int y2 = Integer.parseInt(pos2.charAt(1) + "") - 1;
		
		if (x1 > _BoardX || x1 < 0 || x2 > _BoardX || x2 < 0)
		{
			return false;
		}
		if (y1 > _BoardY || y1 < 0 || y2 > _BoardY || y2 < 0)
		{
			return false;
		}
		
		if (gameBoard[x1][y1] != ' ' || gameBoard[x2][y2] != ' ' || y2 != y1) 
		{
			return false;
		}
		else 
		{
			if ((x1 == 0 && x2 != 1) || (x2 == 0 && x1 != 1)) 
			{
				return false;
			}
			else if ((x1 == _BoardX && x2 != _BoardX - 1) || (x2 == _BoardX && x1 != _BoardX - 1))
			{
				return false;
			}			
			else if (Math.abs(x1 - x2) > 1) 
			{
				return false;
			}
			else 
			{
				return true;
			}
		}
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
		System.out.printf("%-5c%-5d%-5d%-5d%-5d%-5d%-5d%-5d%-5d%n", ' ', 1, 2, 3, 4, 5, 6, 7, 8);
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
