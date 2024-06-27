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

    }
}
