import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int n;
    private int[] board;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            board[i] = tiles[i / n][i % n];
        }

        manhattan = manhattanHelper();
    }

    // sum of Manhattan distances between tiles and goal
    private int manhattanHelper() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int curr = board[i * n + j];
                if (curr == 0) continue;
                curr--;
                sum += Math.abs(curr / n - i) + Math.abs(curr % n - j);
            }
        }

        return sum;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] arr = { { 1, 3, 5 }, { 4, 2, 0 }, { 7, 8, 6 } };

        Board myBoard = new Board(arr);
        StdOut.println(myBoard);
        for (Board b : myBoard.neighbors()) {
            StdOut.println(b);
        }
        // System.out.println(myBoard.hamming());
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] arr = new int[n * n];
        System.arraycopy(board, 0, arr, 0, n * n);
        if (arr[0] == 0 || arr[1] == 0) return new Board(swap(arr, 1, 0, 1, 1));
        else return new Board(swap(arr, 0, 0, 0, 1));
    }

    // number of tiles out of place
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < n * n; i++) {
            if (board[i] != i + 1 && board[i] != 0) sum++;
        }

        return sum;
    }

    private int[][] swap(int[] arr, int i1, int j1, int i2, int j2) {
        int tmp = arr[i1 * n + j1];
        arr[i1 * n + j1] = arr[i2 * n + j2];
        arr[i2 * n + j2] = tmp;

        int[][] ret = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ret[i][j] = arr[i * n + j];
            }
        }
        return ret;
    }

    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;

        for (int i = 0; i < n * n; i++) {
            if (board[i] != that.board[i]) return false;

        }

        return true;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i * n + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int x = -1, y = -1;
        for (int i = 0; i < n * n; i++) {
            if (board[i] == 0) {
                x = i / n;
                y = i % n;
            }
        }

        int[] dx = { 0, 0, 1, -1 };
        int[] dy = { 1, -1, 0, 0 };

        ArrayList<Board> arrayList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int xx = x + dx[i], yy = y + dy[i];
            if (xx < 0 || xx >= n || yy < 0 || yy >= n) continue;

            int[] cpy = new int[board.length];
            System.arraycopy(board, 0, cpy, 0, n * n);

            arrayList.add(new Board(swap(cpy, x, y, xx, yy)));
        }

        return arrayList;
    }
}
