import java.util.Random;

public class UnionFind {
    public int[] id;
    public int[] sz;

    public UnionFind(int N) {
        //array for union/find
        // add on 2 extra at the end for the top and bottom roots
        //N is total number of nodes in percolation square

        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            // root of each node, size N
            id[i] = i;
            //size (number of nodes) of tree that the node is in
            sz[i] = 1;
        }

    }

    private boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public int root(int i) {
        while (i != id[i]) {
            int temp = id[i];
            id[i] = id[id[i]];
            i = id[i];
        }

        return i;
    }

    public void union(int p, int q) {
        int i = root(p);
        //System.out.println("i: " + i);
        int j = root(q);
        //System.out.println("j: " + j);
        if (i == j) {
            return;
        }
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }

    public static void main(String[] args) {
        while (true) {
            Random rand = new Random();
            System.out.println(rand.nextInt(9));
        }
    }
}
