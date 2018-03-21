import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean grid[][];
    private WeightedQuickUnionUF myUF;
    private int n;
    private int openSites;

    public Percolation(int n) {
    // create n-by-n grid, with all sites initially blocked
        if (n <= 0) throw new java.lang.IllegalArgumentException("Percolation(int n): argument n must be greater than zero");
        grid = new boolean[n][n];
        myUF = new WeightedQuickUnionUF(n*n + 2);
        openSites = 0;
        this.n = n;
    }

    public void open(int row, int col) {
    // open the site (row, col) if it is not open already
   	    if (row < 0 || row > n - 1 || col < 0 || col > n - 1) throw new java.lang.IndexOutOfBoundsException("open():row or col out of bounds");
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites++;
            int comp1 = n*row + col;
            if (row == 0) myUF.union(comp1, n*n);
            if (row == n - 1 ) myUF.union(comp1, n*n + 1);
            if (row + 1 < n && grid[row+1][col]) myUF.union(comp1, comp1 + n);
            if (row - 1 >= 0 && grid[row-1][col]) myUF.union(comp1, comp1 - n);
            if (col + 1 < n && grid[row][col+1]) myUF.union(comp1, comp1 + 1);
            if (col - 1 >= 0 && grid[row][col-1]) myUF.union(comp1, comp1 - 1);
        } 
    }

    public boolean isOpen(int row, int col) {
    // is the site (row, col) open?
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1)  throw new java.lang.IndexOutOfBoundsException("isOpen():row or col out of bounds");
        return grid[row][col];
    }

    public boolean isFull(int row, int col) { 
    // is the site (row, col) full?
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) throw new java.lang.IndexOutOfBoundsException("isFull():row or col out of bounds");
            if (isOpen(row, col) && myUF.connected(n*n, row*n + col))
                return true;
        return false;

    }

    public int numberOfOpenSites() { 
    // number of open sites
        return openSites;
    }

    public boolean percolates() {
    // does the system percolate?
        return myUF.connected(n*n, n*n + 1);
    }

    public static void main(String[] args) {
    // unit testing (required)
        Percolation perc = new Percolation(Integer.parseInt(args[0]));
        System.out.println("Digite novas coordenadas:");
        int i = StdIn.readInt();
        int j = StdIn.readInt();
        while(!perc.percolates()) {
            while(perc.isOpen(i, j)){
                System.out.println("Digite novas coordenadas:");
                i = StdIn.readInt();
                j = StdIn.readInt();
            }
            perc.open(i, j);
        }
    }
}