import java.util.ArrayList;

public class Minimax {
	
	private int depthOfCheck;
	
	public Minimax() 
	{
		
	}
	
	public void expand(int depth) 
	{
		
	}
	
	private ArrayList<Coord> movesAtDepth(int depth) {
		// getBoard returns a deep copy of the gameboard with O(n^2) so this is done 
		// so it's only evaluated once.
		char[][] currentBoard = Gameboard.getInstance().getBoard(); 
		ArrayList<Coord> moves = new ArrayList<Coord>();
		
		for (int i = 0; i < currentBoard.length; i++) 
		{
			for (int j = 0; j < currentBoard[i].length; j++) 
			{
				if (currentBoard[i][j] == ' ')
				{
					moves.add(new Coord(i, j));
				}
			}
		}
		return moves;
	}
	
}
