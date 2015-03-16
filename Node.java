import java.util.ArrayList;

public class Node {
	Coord coord;
	
	Node parent;
	ArrayList<Node> children;
	ArrayList<PlayablePair> simulatedPairs;
	
	int heuristicValue;
	
	public Node(Coord c)
	{
		coord = c;
	}
	
	public Node() 
	{
		coord = null;
		parent = null;
		for (int i = 0; i < Minimax.availableMoves(Gameboard.getInstance().getBoard()).size(); i++)
		{
			children.add(new Node(Minimax.availableMoves(Gameboard.getInstance().getBoard()).get(i)));
		}
	}
	
	
	
	public void setParent(Node p)
	{
		parent = p;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public void setHeuristic(int h) 
	{
		heuristicValue = h;
	}
	
	public int getHeuristic() 
	{
		return heuristicValue;
	}
	
	public Coord getCoord() 
	{
		return coord;
	}
	
	public void setCoord (Coord c) 
	{
		coord = c;
	}
}
