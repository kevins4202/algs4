import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private final TrieSet dict;
    private final int[] dx = { -1, 0, 1, 1, 1, 0, -1, -1 };
    private final int[] dy = { -1, -1, -1, 0, 1, 1, 1, 0 };

    public BoggleSolver(String[] dictionary) {
        dict = new TrieSet();
        for (String s : dictionary) dict.add(s);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> validWords = new SET<>();
        for (int i = 0; i < board.rows() * board.cols(); i++) {
            boolean[] marked = new boolean[board.rows() * board.cols()];

            dfs(board, i, "", marked, validWords);
        }

        return validWords;
    }

    private void dfs(BoggleBoard board, int index, String word, boolean[] marked,
                     SET<String> validWords) {
        marked[index] = true;
        char c = board.getLetter(index / board.cols(), index % board.cols());
        String newWord = word + c;

        if (c == 'Q') newWord += 'U';

        if (dict.contains(newWord) && newWord.length() > 2) validWords.add(newWord);
        else if (((Queue<String>) dict.keysWithPrefix(newWord)).isEmpty()) return;

        for (int i = 0; i < 8; i++) {
            int rr = index / board.cols() + dy[i];
            int cc = index % board.cols() + dx[i];
            if (rr < 0 || rr >= board.rows() || cc < 0 || cc >= board.cols() || marked[
                    rr * board.cols() + cc])
                continue;

            dfs(board, rr * board.cols() + cc, newWord, marked.clone(), validWords);
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int len = word.length();

        if (len <= 2) return 0;
        else if (len <= 4) return 1;
        else if (len == 5) return 2;
        else if (len == 6) return 3;
        else if (len == 7) return 5;
        else return 11;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
