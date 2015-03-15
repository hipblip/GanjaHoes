import java.util.ArrayList;
import java.util.Stack;

public class Minimax {
	
	private int depthOfCheck;
	
	private Coord bestMove;
	
	private ArrayList<PlayablePair> simulatedPlays = new ArrayList<PlayablePair>();
	
	public Minimax(int depth) 
	{
		depthOfCheck = depth;
	}
	
	boolean isValidCoord(Coord c, boolean maximizingPlayer, Gameboard board)
	{
		//Gameboard board = Gameboard.getInstance();
		
		if (maximizingPlayer)
		{
			try
			{
				boolean valid = (board.getCharAt(c.getX() - 1, c.getY()) == ' ') || (board.getCharAt(c.getX() + 1, c.getY()) == ' ');
				
				return valid;
			}
			catch (Exception e){}
		}
		else
		{
			try
			{
				boolean valid = (board.getCharAt(c.getX(), c.getY() - 1) == ' ') || (board.getCharAt(c.getX(), c.getY() + 1) == ' ');
				
				return valid;
			}
			catch (Exception e){}
		}
		
		return false;
	}
	
	PlayablePair getPlayablePair(Coord c, boolean maximizingPlayer, Gameboard board)
	{
		try
		{
			if (maximizingPlayer)
			{
				if (board.getCharAt(c.getX() + 1, c.getY()) == ' ')
				{
					return new PlayablePair(c, new Coord(c.getX() + 1, c.getY()));
				}
				else 
				{
					return new PlayablePair(c, new Coord(c.getX() - 1, c.getY()));
				}
			}
			else
			{
				if (board.getCharAt(c.getX(), c.getY() - 1) == ' ')
				{
					return new PlayablePair(c, new Coord(c.getX(), c.getY() - 1));
				}
				else 
				{
					return new PlayablePair(c, new Coord(c.getX(), c.getY() + 1));
				}
			}
		}
		catch (Exception e){ return null; }
	}
	
	public int alphaBeta(Node node, int alpha, int beta, int depth, boolean maximizingPlayer) // Max should always be O's when playing against a person
	{
		int v = -1;
		
		if (depth == 0 || node.children.size() == 0) 
		{
			
			return node.heuristicValue;
		}
		
		if (maximizingPlayer) // Playing O's
		{
			v = Integer.MIN_VALUE;
			for (Node child : node.children)
			{
				//Check if child will produce a playable pair
				//If it does, remove both coordinates from list of available moves and make recursive call
				//Otherwise continue with loop
				
				
				
				v = Math.max(v, alphaBeta(child, alpha, beta, depth - 1, false));
				alpha = Math.max(alpha, v);
				if (beta <= alpha)
				{
					break;
				}
			}
			return v;
		}
		else // Playing X's
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
