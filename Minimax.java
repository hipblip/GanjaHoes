import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Minimax {
	
	private int depthOfCheck;
	
	private Coord bestMove;
	
	private Stack<PlayablePair> simulatedPlays = new Stack<PlayablePair>();
	
	public Minimax(int depth) 
	{
		depthOfCheck = depth;
	}
	
	static boolean isValidCoord(Coord c, boolean maximizingPlayer, char[][] board)
	{
		//Gameboard board = Gameboard.getInstance();
		
		if (maximizingPlayer)
		{
			try
			{
				//boolean valid = (Gameboard.getInstance().getCharAt(c.getX() - 1, c.getY()) == ' ') || (Gameboard.getInstance().getCharAt(c.getX() + 1, c.getY()) == ' ');
				boolean valid = (board[c.getX() - 1][c.getY()] == ' ' || board[c.getX() + 1][c.getY()] == ' ');
				
				return valid;
			}
			catch (Exception e) // If x is out of bounds catch the exception and check if the other direction is valid
			{
				if (c.getX() - 1 < 0 && board[c.getX() + 1][c.getY()] != ' ')
				{
					return false; 
				}
				else if (c.getX() + 1 >= Gameboard.getInstance().getBoardX() && board[c.getX() - 1][c.getY()] != ' ') 
				{
					return false;
				}
				else 
				{
					return true;
				}
			}
		}
		else
		{
			try
			{
				//boolean valid = (Gameboard.getInstance().getCharAt(c.getX(), c.getY() - 1) == ' ') || (Gameboard.getInstance().getCharAt(c.getX(), c.getY() + 1) == ' ');
				boolean valid = (board[c.getX()][c.getY() - 1] == ' ' || board[c.getX()][c.getY() + 1] == ' ');
				
				return valid;
			}
			catch (Exception e)
			{
				if (c.getY() - 1 < 0 && board[c.getX()][c.getY() + 1] != ' ')
				{
					return false;
				}
				else if (c.getY() + 1 >= Gameboard.getInstance().getBoardY() && board[c.getX()][c.getY() - 1] != ' ')
				{
					return false;
				}
				else
				{
					return true;
				}
			}
		}
		
		//return false;
	}
	
	PlayablePair getPlayablePair(Coord c, boolean maximizingPlayer, Gameboard board)
	{
		try
		{
			if (maximizingPlayer)
			{
				if (Gameboard.getInstance().getCharAt(c.getX() + 1, c.getY()) == ' ')
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
				if (Gameboard.getInstance().getCharAt(c.getX(), c.getY() - 1) == ' ')
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
		//TODO get all the children of the current node
				
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
				
				if (Minimax.isValidCoord(child.getCoord(), maximizingPlayer, Gameboard.getInstance().getBoard()))
				{
					child.simulatedPairs = node.duplicateSimPairs();
					child.simulatedPairs.add(getPlayablePair(child.getCoord(), maximizingPlayer, Gameboard.getInstance()));
				} 
				else 
				{
					continue;
				}

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
				
				if (Minimax.isValidCoord(child.getCoord(),  !maximizingPlayer, Gameboard.getInstance().getBoard()))
				{
					child.simulatedPairs = node.duplicateSimPairs();
					child.simulatedPairs.add(getPlayablePair(child.getCoord(), !maximizingPlayer, Gameboard.getInstance()));
				}
				else
				{
					continue;
				}
				
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
	
	public static ArrayList<PlayablePair> availableMoves(char[][] currentBoard, Node node) {
		// getBoard returns a deep copy of the gameboard with O(n^2) so this is done 
		// so it's only evaluated once.
		ArrayList<PlayablePair> moves = new ArrayList<PlayablePair>();
		
		for (int i = 0; i < currentBoard.length; i++) 
		{
			for (int j = 0; j < currentBoard[i].length; j++) 
			{
				if (currentBoard[i][j] == ' ')
				{
					//moves.add(new PlayablePair(i, j));
				}
			}
		}
		
		for (int i = 0; i < node.simulatedPairs.size(); i++) 
		{
			moves.removeAll(Collections.singleton(node.simulatedPairs.get(i)));
		}
		
		return moves;
	}
	
	public Coord getBestMove() 
	{
		return bestMove;
	}
	
}
