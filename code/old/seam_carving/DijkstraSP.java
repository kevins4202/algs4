import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraSP {
    //edgeTo: the last edge on the shortest path from s to v
    private DirectedEdge[] edgeTo;
    //distTo: the length of the shortest path from s to v
    private double[] distTo;
    //pq: the priority queue of vertices with associated distTo[] values
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph G, int s){
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for(int v = 0; v < G.V(); v++){
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.0;

        pq.insert(s, 0.0);

        while(!pq.isEmpty()){
            int v = pq.delMin();   

            for(DirectedEdge e : G.adj(v)) relax(e);
        }
    }

    public void relax(DirectedEdge e){
        int v = e.from(), w = e.to();

        if(distTo[w] > distTo[v] + e.weight()){
            distTo[w] = distTo[v] + e.weight();

            edgeTo[w] = e;

            if(pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
    }

    public double dist(int v){
        return distTo[v];
    }

    public DirectedEdge edge(int v){
        return edgeTo[v];
    }
    
}
