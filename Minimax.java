import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Minimax {
	
	private int depthOfCheck;
	
	private Node bestMove;
	
	private static Stack<PlayablePair> simulatedPlays = new Stack<PlayablePair>();
	
	public Minimax(int depth) 
	{
		depthOfCheck = depth;
	}
	
	static boolean isValidCoord(Coord c, boolean maximizingPlayer, char[][] board)
	{	
		if (!maximizingPlayer)
		{
			try
			{
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
				boolean valid = (board[c.getX()][c.getY() + 1] == ' ' || board[c.getX()][c.getY() - 1] == ' ');
				
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
		
	//TODO FIGURE OUT WHERE TO RETURN BEST MOVE RATHER THAN JUST THE  VALUE OF THE NODE
	public int alphaBeta(Node node, int alpha, int beta, int depth, boolean maximizingPlayer) // Max should always be O's when playing against a person
	{
		// get all the children of the current node
		ArrayList<PlayablePair> childPairs = new ArrayList<PlayablePair>();

		childPairs = Minimax.availableMovesForNode(Gameboard.getInstance().getBoard(), node, !maximizingPlayer);
				
		for (PlayablePair pair : childPairs)
		{
			
			node.children.add(PlayablePair.pairToNode(pair)[0]);
			node.children.add(PlayablePair.pairToNode(pair)[1]);
		}
		
		int v = -1;		
		if (depth == 0 || node.children.size() == 0) 
		{
			//return an actual heuristic, brotendo

			//return Minimax.heuristicFunction(node);
			return (int)(Math.random() * 101);
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
					child.removeNullPairs();
				} 
				else 
				{
					continue;
				}
				
				int h = alphaBeta(child, alpha, beta, depth - 1, false);
				
				v = Math.max(v, h);
				node.heuristicValue = v;
				//System.out.println(v);
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
					child.removeNullPairs();
				}
				else
				{
					continue;
				}
				
				//node.heuristicValue = v;
				v = Math.min(v, alphaBeta(child, alpha, beta, depth - 1, true));
				//System.out.println(v);
				node.heuristicValue = v;
				beta = Math.min(beta, v);
				
				if (beta <= alpha)
				{
					break;
				}
				
			}
			
			return v;
		}
	}
	
	public static ArrayList<PlayablePair> availableMovesForNode(char[][] currentBoard, Node node, boolean whiteTurn) 
	{
		int turn = 1;
		
		if (whiteTurn)
		{
			turn = 0;
		}
		
		ArrayList<PlayablePair> pairs = PlayablePair.availableMoves(turn, currentBoard);
		
		// Remove invalid coordinates
		for (int i = 0; i < node.simulatedPairs.size(); i++)
		{

			for (int j = 0; j < pairs.size(); j++)
			{
				// If one of the pair coordinates overlaps with a simulatedPlays coordinate, remove it
				if (node.simulatedPairs.get(i).spot1.equals(pairs.get(j).spot1) || node.simulatedPairs.get(i).spot1.equals(pairs.get(j).spot2) ||
						node.simulatedPairs.get(i).spot2.equals(pairs.get(j).spot1) || node.simulatedPairs.get(i).spot2.equals(pairs.get(j).spot2))
				{
					pairs.remove(pairs.get(j));
				}
			}	

		}
		
		return pairs;
	}
	
	private static int heuristicFunction(Node node) 
	{
		// Count every position such that the positions above and below the cell are unavailable
		// Take into consideration the simPlays of the node.
		int h = 0;
		int horzCount = 0;
		int vertCount = 0;
		
		char[][] boardForNode = Gameboard.getInstance().getBoard();
		
		for (PlayablePair p : node.simulatedPairs)
		{
			if (p.isVertical())
			{
				vertCount++;
			}
			else
			{
				horzCount++;
			}
			
			boardForNode[p.spot1.getX()][p.spot1.getY()] = 'B';
			boardForNode[p.spot2.getX()][p.spot2.getY()] = 'B';
		}
		
		ArrayList<PlayablePair> moves = PlayablePair.availableMoves(1, boardForNode);
		
		int count = 0;
		
		for (int i = 0; i < Gameboard.getInstance().getBoardX(); i++)
		{
			for (int j = 0; j < Gameboard.getInstance().getBoardY(); j++)
			{
				try
				{
					if (Gameboard.getInstance().getCharAt(i, j) == ' ')
						{
						if (Gameboard.getInstance().getCharAt(i+1, j) != ' ' && Gameboard.getInstance().getCharAt(i-1, j) != ' ')
						{
							count++;
						}
						else // Check against the simulated pairs
						{
							for (PlayablePair p : node.simulatedPairs)
							{
								if (Gameboard.getInstance().getCharAt(i+1,  j) != ' ' && 
										(Gameboard.getInstance().getCharAt(i-1, j) == Gameboard.getInstance().getCharAt(p.spot1.getX(),  p.spot1.getY()) ||
										 Gameboard.getInstance().getCharAt(i - 1, j) == Gameboard.getInstance().getCharAt(p.spot2.getX(),  p.spot2.getY()))) 
								{
									count++;
								}
								else if (Gameboard.getInstance().getCharAt(i -1,  j) != ' ' && 
										(Gameboard.getInstance().getCharAt(i + 1, j) == Gameboard.getInstance().getCharAt(p.spot1.getX(),  p.spot1.getY()) ||
										 Gameboard.getInstance().getCharAt(i + 1, j) == Gameboard.getInstance().getCharAt(p.spot2.getX(),  p.spot2.getY())))
								{
									count++;
								}
									
							}
						}
					}
				}
				catch (Exception e) // index was out of bounds
				{
					//System.out.println(e);
					if (i + 1 > Gameboard.getInstance().getBoardY()) // Upper was out of bounds
					{
						try 
						{
							if (Gameboard.getInstance().getCharAt(i - 1,  j) != ' ')
							{
								count++;
							}
							else // Check sim pairs
							{
								for (PlayablePair p : node.simulatedPairs)
								{
									if (Gameboard.getInstance().getCharAt(p.spot1.getX(), p.spot1.getY()) == Gameboard.getInstance().getCharAt(i - 1,  j) ||
											Gameboard.getInstance().getCharAt(p.spot2.getX(), p.spot2.getY()) == Gameboard.getInstance().getCharAt(i - 1, j))
									{
										count++;
									}
								}
							}
							
						}
						catch (Exception ex)
						{
							System.out.println("If you got here, there's something wrong with the heuristic function :: Upper bound");
						}
					} 
					else if (i - 1 < 0) //Lower bound
					{
						try 
						{
							if (Gameboard.getInstance().getCharAt(i + 1,  j) != ' ')
							{
								count++;
							}
							else // Check sim pairs
							{
								for (PlayablePair p : node.simulatedPairs)
								{
									if (Gameboard.getInstance().getCharAt(p.spot1.getX(), p.spot1.getY()) == Gameboard.getInstance().getCharAt(i + 1,  j) ||
											Gameboard.getInstance().getCharAt(p.spot2.getX(), p.spot2.getY()) == Gameboard.getInstance().getCharAt(i + 1, j))
									{
										count++;
									}
								}
							}
							
						}
						catch (Exception ex)
						{
							System.out.println("If you got here, there's something wrong with the heuristic function :: Lower Bound");
						}
					}
				}
			}
		}
		
		return count;
	}
	
	public static ArrayList<PlayablePair> getSimPlays() 
	{
		ArrayList<PlayablePair> simPlays = new ArrayList<PlayablePair>();
		
		for (int i = 0; i < simulatedPlays.size(); i++)
		{
			simPlays.add(simulatedPlays.get(i));
		}
		
		return simPlays;
	}
	
	public Node getBestMove() 
	{
		return bestMove;
	}
	
	public int getDepth() 
	{
		return depthOfCheck;
	}
	
}