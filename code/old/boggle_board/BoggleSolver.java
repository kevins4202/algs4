import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private TrieSet dictionary;
    private int dx[] = {-1,-1,-1,0,1,1,1, 0};
    private int dy[] = {-1, 0, 1,1,1,0,-1,-1};

    private class BoggleNode{
        public String prefix;
        public int x,y;
        public boolean[][] vis;

        public BoggleNode(int x, int y, String prefix, boolean[][] v){
            this.x = x;
            this.y = y;
            this.prefix = prefix;
            vis = v;
        }
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dict){
        dictionary = new TrieSet();

        for(String s : dict){
            dictionary.add(s);
            // System.out.println(s);
        }
    }

    private void dfs(BoggleBoard board, int x, int y, boolean[][] marked, SET<String> q){
        String prefix = "" + board.getLetter(x, y);
        if(prefix.equals("Q")) prefix += "U";

        marked[x][y] = true;

        Stack<BoggleNode> todo = new Stack<>();

        todo.push(new BoggleNode(x, y, prefix, marked));
        
        while(!todo.isEmpty()){
            BoggleNode n = todo.pop();

            int cx = n.x;
            int cy = n.y;
            String pf = n.prefix;
            boolean[][] m = n.vis;

            // StdOut.println("CURRENT PREFIX: "+pf);

            // for(int i = 0; i < 4; i++){
            //     for(int j = 0; j < 4; j++){
            //         StdOut.print(" "+ (m[i][j] ? 1 : 0));
            //     }
            //     StdOut.println();
            // }

            for(int i = 0; i < 8; i++){
                int xx = cx + dx[i];
                int yy = cy + dy[i];

                if(xx < 0 || xx >= board.rows() || yy < 0 || yy >= board.cols() || m[xx][yy]) continue;
                
                String tmp_pf = pf + ((board.getLetter(xx,yy) == 'Q') ? "QU" : board.getLetter(xx,yy));

                if(tmp_pf.length() > 2){
                    if(dictionary.contains(tmp_pf)){
                        q.add(tmp_pf);
                        // System.out.println("FOUND "+tmp_pf);
                    } else if(!dictionary.keysWithPrefix(tmp_pf).iterator().hasNext()){
                        continue;
                    }
                } 

                // System.out.println(tmp_pf);

                boolean[][] tm = new boolean[m.length][m[0].length];

                for(int j = 0; j < m.length; j++)
                    tm[j] = m[j].clone();

                tm[xx][yy] = true;

                todo.push(new BoggleNode(xx, yy, tmp_pf, tm));
                
            }
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        SET<String> q = new SET<String>();

        // boolean[][] marked = new boolean[board.rows()][board.cols()];
                
        // dfs(board, 1, 2, marked, q);

        for(int i = 0; i < board.rows(); i++){
            for(int j = 0; j < board.cols(); j++){
                // System.out.println("LETTER: "+board.getLetter(i, j));
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                
                dfs(board, i, j, marked, q);
            }
        }
        return q;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        int len = word.length();
        if(len <= 2) return 0;
        else if(len <= 4) return 1;
        else if(len == 5) return 2;
        else if(len == 6) return 3;
        else if(len == 7) return 5;
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
