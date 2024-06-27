import java.util.Random;

public class RunPercolation {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Random rand = new Random();

        double[] probabilities = new double[trials];


        for (int i = 0; i < trials; i++) {
//            int[] randomNodes = new int[N * N];
//            for (int j = 0; j < N * N; j++) {
//                randomNodes[j] = rand.nextInt(N * N);
//            }
            //System.out.println("This is the " + i + " loop");
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
                //System.out.println(newNode);
                //System.out.println(myPerc.percolates());
//                k++;
            }

//            myPerc.open(1);
//            myPerc.open(4);
//            myPerc.open(7);
            probabilities[i] = myPerc.numberOfOpenSites() / (N * N);
        }

        PercolationStats myPercStats = new PercolationStats(N, trials);
        double mean = 0.0;
        double stddev = 0.0;
        double lo = 0.0;
        double hi = 0.0;

        mean = myPercStats.mean(probabilities);
        stddev = myPercStats.stddev(mean, probabilities);
        lo = myPercStats.confidenceLo(mean, stddev, trials);
        hi = myPercStats.confidenceHi(mean, stddev, trials);

        System.out.println("mean: \t" + mean);
        System.out.println("stddev: \t" + stddev);
        System.out.println("95% confidence interval: \t[" + lo + ", " + hi + "]");


    }
}
