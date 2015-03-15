
public class PlayablePair {
	public Coord spot1;
	public Coord spot2;
	
	public PlayablePair(Coord v1, Coord v2)
	{
		spot1 = v1;
		spot2 = v2;
	}
	
	public boolean containsVector(Coord vec)
	{
		return (spot1 == vec || spot2 == vec);
	}
	
	public String toString()
	{
		return spot1 + " and " + spot2;
	}
}
