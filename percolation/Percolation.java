import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
	private boolean[][] grid;
	private WeightedQuickUnionUF qu;
  private boolean hasPercolated;
  private int topVirtualNode;
  private int botVirtualNode;
	public Percolation(int N)
	{
    if (N <= 0)
      throw new IllegalArgumentException();
    hasPercolated = false;
		grid = new boolean[N][N];
		qu = new WeightedQuickUnionUF(N*N+2);
    topVirtualNode = N*N;
    botVirtualNode = topVirtualNode +1;
	}
	public void open(int i, int j)
	{
		checkIndices(i, j);  
		grid[i-1][j-1] = true;
    if (i == 1)
      qu.union(unionArrayLocation(i,j), topVirtualNode);
    if (i == grid.length)
      qu.union(unionArrayLocation(i,j), botVirtualNode);
		connectToSurroundingOpenSites(i, j);
	}
	private int unionArrayLocation(int i, int j)
	{
		return (i-1)*grid.length+(j-1);
	}
	private void connectToSurroundingOpenSites(int i, int j)
	{
		if (i-1 > 0 && isOpen(i-1, j))
			qu.union(unionArrayLocation(i, j), unionArrayLocation(i-1, j));
		if (i+1 <= grid.length && isOpen(i+1, j))
			qu.union(unionArrayLocation(i, j), unionArrayLocation(i+1, j));
		if (j-1 > 0 && isOpen(i, j-1))
			qu.union(unionArrayLocation(i, j-1), unionArrayLocation(i, j));
		if (j+1 <= grid.length && isOpen((i), j+1))
			qu.union(unionArrayLocation(i, j+1), unionArrayLocation(i, j));			
	}
	public boolean isOpen(int i, int j)
	{
    checkIndices(i, j);
		return grid[i-1][j-1];
	}
	public boolean isFull(int i, int j)
	{
			if (isOpen(i, j) && qu.connected(unionArrayLocation(i, j), topVirtualNode))
				return true;
      return false;
	}
	public boolean percolates() 
	{
		if (qu.connected(topVirtualNode, botVirtualNode))
			return true;
		return false;
	}
  private void checkIndices(int i, int j)
  {
		if (i > grid.length || j > grid.length || i <= 0 || j <= 0)
			throw new IndexOutOfBoundsException();
  }
	public static void main(String[] args)
	{
		Percolation perc = new Percolation(5);
		//perc.open(1,1);
		//perc.open(1,2);
		//perc.open(2,2);
		//perc.open(2,3);
		//perc.open(2,4);
		//perc.open(3,4);
		//perc.open(4,4);
		//perc.open(5,4);
		System.out.println(perc.percolates());
		//System.out.println(perc.isFull(1,1));
		//System.out.println(perc.isFull(2,2));
	}

}