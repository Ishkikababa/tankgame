package shortestPath;

public class Path
{
	private int a, b, d;
	public Path(int a, int b, int d)
	{
		this.a = a;
		this.b = b;
		this.d = d;
	}
	
	public Path(Path path)
	{
		this.a = path.a;
		this.b = path.b;
		this.d = path.d;
	}
	
	public int start()
	{
		return a;
	}
	
	public int end()
	{
		return b;
	}
	
	public int distance()
	{
		return d;
	}
	
	public String toString()
	{
		return this.a + ", " + this.b + ", " + this.d;
	}
}
