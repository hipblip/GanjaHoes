
public class Coord {
	private int x;
	private int y;
	
	public Coord(int xx, int yy)
	{
		x = xx;
		y = yy;
	}
	
	/*public Coord() 
	{
		x = -1;
		y = -1;
	}*/
	
	public int getX() 
	{
		return x;
	}
	
	public void setX(int xx) 
	{
		x = xx;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int yy)
	{
		y = yy;
	}
	
	public static Coord convertToCoord(String coord) throws Exception
	{
		int x1;
		int y1;
		
		if (coord.length() != 2) 
		{
			throw new Exception("Invalid coord string length");
		}
			
		char xChar = coord.charAt(0);
		if (xChar < 65 || xChar > Gameboard.getInstance().getBoardX() + 65) 
		{
			throw new Exception("Invalid Coordinate String");
		}
		x1 = xChar - 65;
		y1 = Integer.parseInt(coord.charAt(1) + "") - 1;
		if (y1 < 0 || y1 >= Gameboard.getInstance().getBoardY())
		{
			throw new Exception("Invalid Coordinate String");
		}
		
		Coord newCoord = new Coord(x1, y1);
		//newCoord.x = x1;
		//newCoord.y = y1;
		
		return newCoord;
	}
	
	public String toString() 
	{
		char letter = (char)(x + 65);
		int num = y + 1;
		return letter + "" + num;
	}
	
	public boolean equals(Coord c)
	{
		return this.x == c.getX() && this.y == c.getY();
	}
	
}
