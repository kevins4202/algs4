import java.util.Random;

public class RunPercolation {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Random rand = new Random();

        double[] probabilities = new double[trials];


        for (int i = 0; i < trials; i++) {
            Percolation myPerc = new Percolation(N);

            while (!myPerc.percolates()) {
                int newNode = rand.nextInt(N * N);
                int row = myPerc.getCoordinates(newNode, N)[0];
                int col = myPerc.getCoordinates(newNode, N)[1];
                while (myPerc.isOpen(row, col)) {
                    newNode = rand.nextInt(N * N);
                    row = myPerc.getCoordinates(newNode, N)[0];
                    col = myPerc.getCoordinates(newNode, N)[1];
                }

                myPerc.open(newNode);

            }

            probabilities[i] = myPerc.numberOfOpenSites() / (N * N);
        }

        PercolationStats myPercStats = new PercolationStats(N, trials);
        double mean;
        double stddev;
        double lo;
        double hi;

        mean = myPercStats.mean(probabilities);
        stddev = myPercStats.stddev(mean, probabilities);
        lo = myPercStats.confidenceLo(mean, stddev, trials);
        hi = myPercStats.confidenceHi(mean, stddev, trials);

        System.out.println("mean: \t" + mean);
        System.out.println("stddev: \t" + stddev);
        System.out.println("95% confidence interval: \t[" + lo + ", " + hi + "]");

    }
}
