import java.util.ArrayList;


public class PlayablePair {
	public Coord spot1;
	public Coord spot2;
	
	public PlayablePair(Coord v1, Coord v2)
	{
		spot1 = v1;
		spot2 = v2;
	}
	
	/*public PlayablePair()
	{
		spot1 = new Coord();
		spot2 = new Coord();
	}*/
	
	public boolean containsVector(Coord vec)
	{
		return (spot1 == vec || spot2 == vec);
	}
	

	
	public static void printPlays(ArrayList<PlayablePair> plays)
	{
		for (int i = 0; i < plays.size(); i++)
		{
			System.out.println((PlayablePair) plays.get(i));
		}
	}
	

	
	public static Node[] pairToNode(PlayablePair p)
	{
		Node[] nodes = new Node[2];
		
		nodes[0] = new Node(new Coord(p.spot1.getX(), p.spot1.getY()));
		nodes[1] = new Node(new Coord(p.spot2.getX(), p.spot2.getY()));
		
		return nodes;
	}
	
	public static ArrayList<PlayablePair> availableMoves(int specTurn, char[][] board)
	{
		ArrayList<Coord> list = new ArrayList<Coord>();
		
		for (int i = 0; i < Gameboard.getInstance().getBoardX(); i++) // gather all the empty spots
		{
			for (int j = 0; j < Gameboard.getInstance().getBoardY(); j++) 
			{
				Coord vec = new Coord(i,j);

				if (board[i][j] == ' ' && !list.contains(vec))
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
		

		
		return pairs;
	}
	
	public static void checkAddNewPlayable(PlayablePair p, ArrayList<PlayablePair> pairs)
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
	
	public static boolean checkIfCanPlay(int specTurn)
	{
		ArrayList<PlayablePair> pairs = availableMoves(specTurn, Gameboard.getInstance().getBoard());

		//printPlays(pairs);
		
		return pairs.size() > 0;
	}
	
	public boolean isVertical()
	{
		return (spot1.getX() == spot2.getX());
	}
	
	public String toString()
	{
		return spot1 + " and " + spot2;
	}
}
