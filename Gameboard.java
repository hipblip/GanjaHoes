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
	
	private Gameboard() 
	{		
		initBoard();
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
			
			char pos1Let = pos1.charAt(0);
			int x1 = pos1Let - 65;
			int y1 = Integer.parseInt(pos1.charAt(1) + "") - 1;

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
			if (playMode == gameType.PVP) {
				do {
					System.out.print("O's turn\nPlease enter your next move:\n1: ");
					pos1 = sc.nextLine();
				} while (!legalBlackMove(pos1, gameBoard));
			}
			else if (playMode == gameType.PVE)
			{
				System.out.println("heuristic");
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
	
	public boolean legalWhiteMove(String pos1, char[][] gb) 
	{
		Coord c = new Coord();
		try {
			c = Coord.convertToCoord(pos1);
		} 
		catch(Exception e)
		{
			System.err.println(e);
		}
		
		int x1 = c.getX();
		int y1 = c.getY();
		
		if (x1 > _BoardX || x1 < 0) // The X value is off the board
		{
			return false;
		}
		if (y1 > _BoardY || y1 < 0) // The Y value is off the board
		{
			return false;
		}
		if (gb[x1][y1] != ' ') // The position isn't open
		{
			return false;
		}
		if (x1 != 0 && x1 != _BoardX - 1) // The positions above and below the invalid
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
		else if (x1 ==  _BoardX - 1) 
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
	public boolean legalBlackMove(String pos1, char[][] gb) 
	{
		Coord c = new Coord();
		
		try {
			c = Coord.convertToCoord(pos1);
		} 
		catch(Exception e)
		{
			System.err.println(e);
		}
		
		int x1 = c.getX();
		int y1 = c.getY();
		
		if (x1 > _BoardX || x1 < 0) // The X value is off the board
		{
			return false;
		}
		if (y1 > _BoardY || y1 < 0) // The Y value is off the board
		{
			return false;
		}
		if (gb[x1][y1] != ' ') // The position isn't open
		{
			return false;
		}
		if (y1 != 0 && y1 != _BoardY - 1) // The positions above and below the invalid
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
		else if (y1 ==  _BoardY - 1) 
		{
			if (gb[x1][y1-1] != ' ')
			{
				return false;
			}
		}
				
		return true;
	}
	
	void printPlays(ArrayList<PlayablePair> plays)
	{
		for (int i = 0; i < plays.size(); i++)
		{
			System.out.println((PlayablePair) plays.get(i));
		}
	}
	
	void checkAddNewPlayable(PlayablePair p, ArrayList<PlayablePair> pairs)
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
		ArrayList<Coord> list = new ArrayList<Coord>();
		
		for (int i = 0; i < _BoardX; i++) // gather all the empty spots
		{
			for (int j = 0; j < _BoardY; j++) 
			{
				Coord vec = new Coord(i,j);

				if (gameBoard[i][j] == ' ' && !list.contains(vec))
				{
					list.add(vec);
				}
			}
		}
		
		ArrayList<PlayablePair> pairs = new ArrayList<PlayablePair>();
		
		if (specTurn % 2 == 0) // white's turn, check all spots
		{
			for (int i = 0; i < list.size(); i++)
			{
				for (int j = i; j < list.size(); j++)
				{
					Coord sp1 = (Coord) list.get(i);
					Coord sp2 = (Coord) list.get(j);
					
					if ((sp2.getY() == sp1.getY() - 1 || sp2.getY() == sp1.getY() + 1) && sp1.getX() ==sp2.getX())
					{
						checkAddNewPlayable(new PlayablePair(new Coord(sp1.getX(), sp1.getY()), new Coord(sp2.getX(), sp2.getY())), pairs);
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
					Coord sp1 = (Coord) list.get(i);
					Coord sp2 = (Coord) list.get(j);
					
					if ((sp2.getX() == sp1.getX() - 1 || sp2.getX() == sp1.getX() + 1)&& sp1.getY() == sp2.getY())
					{
						checkAddNewPlayable(new PlayablePair(new Coord(sp1.getX(), sp1.getY()), new Coord(sp2.getX(), sp2.getY())), pairs);
					}
				}
			}
		}
		
		
		//printPlays(pairs);
		
		return pairs.size() > 0;
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
	
	public char[][] getBoard() 
	{
		char[][] gb = new char[_BoardX][_BoardY];
		for (int i = 0; i < gb.length; i++) 
		{
			for (int j = 0; j < gb[i].length; i++) 
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

//class Vector2
//{
//	public int x;
//	public int y;
//	
//	public Vector2(int x, int y)
//	{
//		this.x = x;
//		this.y = y;
//	}
//	
//	public String toString() {
//		return x + ", " + y;
//	}
//}

class PlayablePair
{
	public Coord spot1;
	public Coord spot2;
	
	public PlayablePair(Coord v1, Coord v2)
	{
		spot1 = v1;
		spot2 = v2;
	}
	
	public boolean containsVector(Coord vec)
	{
		return (spot1 == vec || spot2 == vec);
	}
	
	public String toString()
	{
		return spot1.getX() + ", " + spot1.getY() + " and " + spot2.getX() + ", " + spot2.getY();
	}
}
