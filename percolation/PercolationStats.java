import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats
{
	private double[] allOpenSites;
	private double mean, stddev, confidenceLo, confidenceHi;
  public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
  {
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException();
    allOpenSites = new double[T];
  	int count = 0;
		double numOpenSites = 0;
		int totalSites = N*N;
		mean = 0;
		int i, j;
		Percolation perc;
		while (count < T)
		{
			numOpenSites = 0;
			perc = new Percolation(N);
			while (!perc.percolates())
			{
				i = StdRandom.uniform(N)+1;
				j = StdRandom.uniform(N)+1;
				if (!perc.isOpen(i, j))
				{
					perc.open(i, j);
					numOpenSites++;
				}
			}
			mean = (mean*count + numOpenSites) / (count+1);
			allOpenSites[count] = numOpenSites/totalSites;
			count++;
			//System.out.println(numOpenSites);
		}
		mean = StdStats.mean(allOpenSites);
		stddev = StdStats.stddev(allOpenSites);
		confidenceLo = mean - ((1.96*stddev)/Math.sqrt(T));
		confidenceHi = mean + ((1.96*stddev)/Math.sqrt(T));
  }
	public double mean()                      // sample mean of percolation threshold
  {
		return mean;	
  }
	public double stddev()                    // sample standard deviation of percolation threshold
  {
		return stddev;  	
  }
	public double confidenceLo()              // low  endpoint of 95% confidence interval
  {
		return confidenceLo;  	
  }
	public double confidenceHi()              // high endpoint of 95% confidence interval
	{
		return confidenceHi;
	}
  public static void main(String[] args)
	{
		PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		StdOut.printf("mean                     = %.10f\n", ps.mean());
		StdOut.printf("stddev                   = %.10f\n", ps.stddev());
		StdOut.printf("95%% confidence interval = %.10f, %.10f\n", ps.confidenceLo(), ps.confidenceHi());
	}
}