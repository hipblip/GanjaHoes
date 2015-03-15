import java.util.ArrayList;

public class Minimax {
	
	private int depthOfCheck;
	
	private Coord bestMove;
	
	
	
	public Minimax(int depth) 
	{
		depthOfCheck = depth;
	}
	
	public int alphaBeta(Node node, int alpha, int beta, int depth, boolean maximizingPlayer) // Max should always be O's when playing against a person
	{
		int v = -1;
		
		if (depth == 0 || node.children.size() == 0) 
		{
			return node.heuristicValue;
		}
		
		if (maximizingPlayer) 
		{
			v = Integer.MIN_VALUE;
			for (Node child : node.children)
			{
				//Check if child will produce a playable pair
				//If it does, remove both coordinates from list of available moves.
				v = Math.max(v, alphaBeta(child, alpha, beta, depth - 1, false));
				alpha = Math.max(alpha, v);
				if (beta <= alpha)
				{
					break;
				}
			}
			return v;
		}
		else
		{
			v = Integer.MAX_VALUE;
			for (Node child : node.children) 
			{
				v = Math.min(v, alphaBeta(child, alpha, beta, depth - 1, true));
				beta = Math.min(beta, v);
				if (beta <= alpha)
				{
					break;
				}
			}
			return v;
		}
	}
	
	public static ArrayList<Coord> availableMoves(char[][] currentBoard) {
		// getBoard returns a deep copy of the gameboard with O(n^2) so this is done 
		// so it's only evaluated once.
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
	
	public Coord getBestMove() 
	{
		return bestMove;
	}
	
}
