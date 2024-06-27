import java.util.Random;

public class PercolationStats {
    private int T;
    private int N;

    public PercolationStats(int n, int trials) {
        T = trials;
        N = n;
    }

    public double mean(double[] percents) {
        double sum = 0.0;
        for (int i = 0; i < percents.length; i++) {
            sum += percents[i];
        }

        return sum / percents.length;
    }

    public double stddev(double mean, double[] percents) {
        double dev = 0.0;
        double sum = 0.0;
        for (int i = 0; i < percents.length; i++) {
            sum += Math.pow(percents[i] - mean, 2);
        }

        double over = sum / (percents.length - 1);

        return Math.sqrt(over);
    }

    public double confidenceLo(double mean, double stdd, double trials) {
        return mean - (1.96 * stdd) / Math.sqrt(trials);
    }

    public double confidenceHi(double mean, double stdd, double trials) {
        return mean + (1.96 * stdd) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int width = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats PercStats = new PercolationStats(width, trials);
        Percolation myPerc = new Percolation(width);
        UnionFind myUF = new UnionFind(width);

        Random rand = new Random();

        //for (int i = 0; i < trials; i++) {
        //    int x = rand.nextInt(width);
        //    int y = rand.nextInt(width);
        //   while (myPerc.isOpen(x, y)) {
        //        x = rand.nextInt(width);
        //        y = rand.nextInt(width);
        //
        //
        //   }
        //    myPerc.open(x, y);
        //}


        //myPerc.open(0, 1);
        //System.out.println(myPerc.percolates());
        //myPerc.open(1, 1);
        //System.out.println(myPerc.percolates());
        //myPerc.open(2, 1);
        //System.out.println(myPerc.percolates());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(myPerc.arr[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < width * width + 2; i++) {
            // root of each node, size N
            System.out.print(myUF.id[i]);
            //size (number of nodes) of tree that the node is in

            System.out.print(myUF.sz[i]);
            System.out.println();
        }
    }
}
