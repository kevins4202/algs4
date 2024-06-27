public class Percolation extends UnionFind {
    //number for width of N * N array
    private int N;
    // N*N array for percolation
    public int[][] arr;

    public Percolation(int n) {
        super(n * n + 2);

        //array for union/find
        // add on 2 extra at the end for the top and bottom roots
        N = n;

        // percolating square
        // creates n-by-n grid, with all sites initially blocked
        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // All closed
                arr[i][j] = 0;
            }
        }

        //root top N
        for (int i = 0; i < N; i++) {
            super.union(N * N, i);
        }

        //root bottom N
        for (int i = N * N - 1; i > N * N - N - 1; i--) {
            super.union(N * N + 1, i);
        }
    }

    public void open(int index) {
        int row = getCoordinates(index, N)[0];
        int col = getCoordinates(index, N)[1];

        //change to 1
        if (index < 0 || index > N * N - 1) {
            throw new IllegalArgumentException("Node not in range");
        }
        arr[row][col] = 1;

        //Node above
        if (row != 0) {
            if (isOpen(row - 1, col)) {
                super.union(getNumber(row - 1, col, N), getNumber(row, col, N));
            }
        }
        //Node to the right
        if (col != N - 1) {
            if (isOpen(row, col + 1)) {
                super.union(getNumber(row, col + 1, N), getNumber(row, col, N));
            }
        }

        //Node below
        if (row != N - 1) {
            if (isOpen(row + 1, col)) {
                super.union(getNumber(row + 1, col, N), getNumber(row, col, N));
            }
        }

        //Node to the left
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                super.union(getNumber(row, col - 1, N), getNumber(row, col, N));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return arr[row][col] == 1;
    }

    public double numberOfOpenSites() {
        double count = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean percolates() {
        return super.connected(N * N, N * N + 1);
    }

    public int[] getCoordinates(int number, int N) {
        // number is node number
        // N is width of N * N array
        // returns array with i, j of N * N array
        // first number i is row index, second number j is column index
        int[] coords = new int[2];
        coords[0] = (number - number % N) / N;
        coords[1] = number % N;

        return coords;
    }

    public int getNumber(int i, int j, int number) {
        // N is width of N * N array
        // i is row index of node, j is column index of node
        return i * number + j;
    }

    public static void main(String[] args) {

    }
}
