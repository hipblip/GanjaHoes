import java.util.ArrayList;

public class Node {
	Coord coord;
	
	Node parent;
	ArrayList<Node> children;
	ArrayList<PlayablePair> simulatedPairs;
	
	int heuristicValue = -1;
	
	public Node(Coord c)
	{
		coord = c;
		simulatedPairs = new ArrayList<PlayablePair>();
		children = new ArrayList<Node>();
	}
	
	public Node() 
	{
		coord = null;
		parent = null;
		simulatedPairs = new ArrayList<PlayablePair>();
		children = new ArrayList<Node>();
		//for (int i = 0; i < Minimax.availableMoves(Gameboard.getInstance().getBoard()).size(); i++)
		//{
		//	children.add(new Node(Minimax.availableMoves(Gameboard.getInstance().getBoard()).get(i)));
		//}
	}
	
	public void printChildren()
	{
		for (int i = 0; i < children.size(); i++) 
		{
			System.out.println(children.get(i));
		}
	}
	
	public void removeNullPairs() 
	{
		for (int i = 0; i < simulatedPairs.size(); i++)
		{
			if (simulatedPairs.get(i) == null)
			{
				simulatedPairs.remove(i);
			}
		}
	}
	
	public ArrayList<PlayablePair> duplicateSimPairs() 
	{
		ArrayList<PlayablePair> p = new ArrayList<PlayablePair>();
		for (int i = 0; i < simulatedPairs.size(); i++)
		{
			p.add(simulatedPairs.get(i));
		}
		return p;
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
