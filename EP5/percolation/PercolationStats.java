import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private double trialData[];
    private double trialNumber;
    public PercolationStats(int n, int trials) {
    // perform trials independent experiments on an n-by-n grid
   	    if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException("PercolationStats(int n, int trials): n or trials invalid.");
        trialData = new double[trials];
        trialNumber = trials;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int randomRow = StdRandom.uniform(n);
            int randomCol = StdRandom.uniform(n);
            while (!perc.percolates()) {
                while (perc.isOpen(randomRow, randomCol)) {
                    randomCol = StdRandom.uniform(n);
                    randomRow = StdRandom.uniform(n);
                }
                perc.open(randomRow, randomCol);
            }
            trialData[i] = (double)perc.numberOfOpenSites()/(double)(n*n);
        }
    }

    public double mean() {
    // sample mean of percolation threshold
        return StdStats.mean(trialData);
    }

    public double stddev() {          
    // sample standard deviation of percolation threshold
        return StdStats.stddev(trialData);
    }

    public double confidenceLow() {               
    // low endpoint of 95% confidence interval
        double numerator = 1.96 * stddev();
        double denominator = Math.sqrt(trialNumber);
        return (mean() - numerator/denominator);
    }

    public double confidenceHigh(){    
    // high endpoint of 95% confidence interval
        double numerator = 1.96 * stddev();
        double denominator = Math.sqrt(trialNumber);
        return (mean() + numerator/denominator);
    }
     public static void main(String[] args) {
    // unit testing (required)
        PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("n: " + Integer.parseInt(args[0]) + " trials: " + Integer.parseInt(args[1]) );
        System.out.println("mean: " + percStats.mean());
        System.out.println("stddev: " + percStats.stddev());
        System.out.println("confidenceLow: " + percStats.confidenceLow());
        System.out.println("confidenceHigh: " + percStats.confidenceHigh());
    }
}