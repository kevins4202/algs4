import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final boolean[][] open;
    private final WeightedQuickUnionUF UF;
    private final int[] dx = {1, -1, 0, 0};
    private final int[] dy = {0, 0, 1, -1};
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException(n + " out of range");
        this.n = n;
        openSites = 0;
        open = new boolean[n + 1][n + 1];
        UF = new WeightedQuickUnionUF((n + 1) * (n + 1) + 1);
    }

    public static void main(String[] args) {
        int n = 4;
        Percolation percolation = new Percolation(n);

        percolation.open(1,1);
        percolation.open(2,1);
        percolation.open(3,1);
        percolation.open(4,4);

        System.out.println(percolation.percolates());
        System.out.println(percolation.isFull(1,2));

//        while (!percolation.percolates()) {
//            int newRow, newCol;
//            do {
//                newRow = StdRandom.uniformInt(n) + 1;
//                newCol = StdRandom.uniformInt(n) + 1;
//            } while (percolation.isOpen(newRow, newCol));
//
////            System.out.println(newRow + ", " + newCol);
//            percolation.open(newRow, newCol);
//
////            for(boolean a[] : percolation.open){
////                for(boolean b : a) System.out.print(b ? 1: 0);
////                System.out.println();
////            }
//        }

//        System.out.println(percolation.isFull(1, 1));
    }

    private boolean isValid(int row, int col) {
        return !(row <= 0 || row > n || col <= 0 || col > n);
    }

    public void open(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException(row + ", " + col + " not in range");

        if (isOpen(row, col)) return;

        open[row][col] = true;
        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];

            if (isValid(newRow, newCol) && isOpen(newRow, newCol)) {
                UF.union(toIndex(row, col), toIndex(newRow, newCol));
            }
        }

//        System.out.println(toIndex(row, col));

        if (row == 1) {
            int root = toIndex(0, 0);
            UF.union(root, toIndex(row, col));
        }


        if (row == n) {
            int sink = toIndex(0, 1);
            UF.union(sink, toIndex(row, col));
        }
        openSites++;
    }

    public boolean isOpen(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException(row + ", " + col + " not in range");
        return open[row][col];
    }

    public boolean isFull(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException(row + ", " + col + " not in range");

        return UF.find(toIndex(0, 0)) == UF.find(toIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {

        return UF.find(toIndex(0, 1)) == UF.find(toIndex(0,0));
    }

    private int toIndex(int row, int col) {
        // [1,n]
        return row * (n + 1) + col;
    }

}
