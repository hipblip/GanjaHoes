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
	
	
	public static void main(String[] args) {
		
		initBoard();		
		intro();
		gamePause();
		// Game Loop
		while (true)
		{
			break;
		}
		
		exit();
	}
	
	static void intro()
	{
		Scanner sc = new Scanner(System.in);
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
		
		sc.close();
	}
		
	static void exit() 
	{
		System.out.println("Exiting game without error");
		System.exit(0);
	}
	
	static void gamePause() 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Press ENTER to continue...");
		int whatthefuck = sc.nextInt();
		sc.close();
	}
	
	static void makeMove(int turn) 
	{
		Scanner sc = new Scanner(System.in);
		if (turn%2 == 0) // White's turn
		{
			
		}
		else // Black's turn
		{
			
		}
		sc.close();
	}
	
	/*
	 * Initializes the game board to the given dimensions
	*/
	static void initBoard() 
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
	static void printBoard()
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
