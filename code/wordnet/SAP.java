import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("null argument");
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int dist = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++) {
            if (!bfsV.hasPathTo(i) || !bfsW.hasPathTo(i)) continue;

            dist = Math.min(dist, bfsV.distTo(i) + bfsW.distTo(i));
        }

        return dist == Integer.MAX_VALUE ? -1 : dist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int dist = Integer.MAX_VALUE;
        int ancestor = -1;

        for (int i = 0; i < G.V(); i++) {
            if (!bfsV.hasPathTo(i) || !bfsW.hasPathTo(i)) continue;

            int curr = bfsV.distTo(i) + bfsW.distTo(i);

            if (curr < dist) {
                ancestor = i;
                dist = curr;
            }
        }

        return ancestor;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("null argument");
        for (Integer i : v) if (i == null) throw new IllegalArgumentException("null argument");
        for (Integer i : w) if (i == null) throw new IllegalArgumentException("null argument");

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int dist = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++) {
            if (!bfsV.hasPathTo(i) || !bfsW.hasPathTo(i)) continue;

            dist = Math.min(dist, bfsV.distTo(i) + bfsW.distTo(i));
        }

        return dist == Integer.MAX_VALUE ? -1 : dist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("null argument");
        for (Integer i : v) if (i == null) throw new IllegalArgumentException("null argument");
        for (Integer i : w) if (i == null) throw new IllegalArgumentException("null argument");

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int dist = Integer.MAX_VALUE;
        int ancestor = -1;

        for (int i = 0; i < G.V(); i++) {
            if (!bfsV.hasPathTo(i) || !bfsW.hasPathTo(i)) continue;

            int curr = bfsV.distTo(i) + bfsW.distTo(i);

            if (curr < dist) {
                ancestor = i;
                dist = curr;
            }
        }

        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();

            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
