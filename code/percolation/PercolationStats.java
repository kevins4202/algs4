import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int T;
    private final double[] trials;

    public PercolationStats(int n, int T) {
        if(n <= 0 || T <= 0) throw new IllegalArgumentException("invalid arguments");
        trials = new double[T];
        this.T = T;
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int newRow, newCol;
                do {
                    newRow = StdRandom.uniformInt(n) + 1;
                    newCol = StdRandom.uniformInt(n) + 1;
                } while (percolation.isOpen(newRow, newCol));

                percolation.open(newRow, newCol);
            }
            trials[i] = ((double) percolation.numberOfOpenSites()) / (n * n);
        }
    }

    public static void main(String[] args) {
        PercolationStats pstats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println("mean\t = " + pstats.mean());
        System.out.println("stddev\t = " + pstats.stddev());
        System.out.println("95% confidence interval\t = [" + pstats.confidenceLo() + ", " + pstats.confidenceHi() + "]");
    }

    public double mean() {
        return StdStats.mean(trials);
    }

    public double stddev() {
        return StdStats.stddev(trials);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
