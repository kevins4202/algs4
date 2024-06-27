import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private boolean reachable = false;
    private ArrayList<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Board is null");
        // System.out.println("BEFORE\n" + initial);
        solution = new ArrayList<>();
        Board twin = initial.twin();

        MinPQ<Node> queue1 = new MinPQ<>();
        MinPQ<Node> queue2 = new MinPQ<>();
        queue1.insert(new Node(null, initial, 0));
        queue2.insert(new Node(null, twin, 0));

        // StdOut.println(initial);
        // StdOut.println(new Board(initial.goal));
        // StdOut.println(initial.isGoal());


        while (true) {
            // StdOut.println("-------");
            Node curr1 = queue1.delMin();
            // StdOut.println("CURRENT: \n" + curr1.getBoard());

            if (curr1.getBoard().isGoal()) {
                reachable = true;
                while (curr1 != null) {
                    solution.add(0, curr1.getBoard());
                    curr1 = curr1.getParent();
                }
                break;
            }
            for (Board b : curr1.getBoard().neighbors()) {
                // StdOut.println(b);
                if (curr1.getParent() == null || !b.equals(curr1.getParent().getBoard())) {
                    queue1.insert(new Node(curr1, b, curr1.getMoves() + 1));
                    // StdOut.println("ADD");
                }
            }

            Node curr2 = queue2.delMin();

            if (curr2.getBoard().isGoal()) {
                break;
            }
            for (Board b : curr2.getBoard().neighbors()) {
                if (curr1.getParent() == null || !b.equals(curr2.getParent().getBoard()))
                    queue2.insert(new Node(curr2, b, curr2.getMoves() + 1));
            }
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // System.out.println("INITIAL\n" + initial);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return reachable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;

        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return solution;
    }

    private class Node implements Comparable<Node> {
        private int priority, moves;
        private Board board;
        private Node parent;

        public Node(Node parent, Board board, int moves) {
            this.parent = parent;
            this.board = board;
            this.moves = moves;
            priority = board.manhattan() + moves;
        }

        public int getPriority() {
            return priority;
        }

        public Board getBoard() {
            return board;
        }

        public Node getParent() {
            return parent;
        }

        public int getMoves() {
            return moves;
        }

        public int compareTo(Node that) {
            if (this.priority == that.priority) return 0;
            return this.priority < that.priority ? -1 : 1;
        }
    }

}
