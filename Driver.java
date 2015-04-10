import java.util.*;

public class Driver {
	
	// When turn is even it's white's turn, when turn is odd it's black's turn
	private static Scanner sc = new Scanner(System.in);
		
	public static void main(String[] args) 
	{		
		intro();
		while (PlayablePair.checkIfCanPlay(Gameboard.getInstance().getTurn() + 1))
		{			
			Gameboard.getInstance().printBoard();
			Gameboard.getInstance().makeMove(Gameboard.getInstance().getTurn());

		}
		
		Gameboard.getInstance().printBoard();
		
		
		if (Gameboard.getInstance().getTurn() % 2 == 0) 
		{
			System.out.println("O's win!!");
		}
		else
		{
			System.out.println("X's win!!");
		}
		exit();
	}
	

	static void intro()
	{
		
		int response;
		
		System.out.println("Welcome!!\n");
		
		System.out.println("Would you like to:\n1. Play against another person\n2. Play against Idiotbot");
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
		
		Gameboard.getInstance().setGameType(Gameboard.gameType.values()[response - 1]);
		
		int response2 = 0;
		boolean first = true;
		if (response == 2) 
		{
			do 
			{
				System.out.println("Who goes first?\n1. You\n2. Idiotbot");
				System.out.println("Response: ");
				response2 = sc.nextInt();
				sc.nextLine();
			} while (response2 != 1 && response2 != 2);
			
			if (response2 == 1)
			{
				first = true;
			}
			else
			{
				first = false;
			}
		}
	
		
		
		Gameboard.getInstance().setPlayerGoesFirst(first);
	}
		
	static void exit() 
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

}
