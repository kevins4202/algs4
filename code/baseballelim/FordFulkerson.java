import edu.princeton.cs.algs4.*;

public class FordFulkerson {
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;
    private FlowNetwork flowNetwork;

    public FordFulkerson(FlowNetwork G, int s, int t){
        flowNetwork = G;
        value = 0.0;

        while(hasAugmentingPath(G, s, t)){
            double bottle = Double.POSITIVE_INFINITY;
            for(int v = t; v != s; v = edgeTo[v].other(v)){
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }
            
            for(int v = t; v != s; v = edgeTo[v].other(v)){
                edgeTo[v].addResidualFlow(v, bottle);
            }

            value += bottle;
        }
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t){
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(s);
        marked[s] = true;
        
        while(!q.isEmpty()){
            int v = q.dequeue();

            for(FlowEdge e : G.adj(v)){
                int w = e.other(v);

                if(e.residualCapacityTo(w) > 0 && !marked[w]){
                    edgeTo[w] = e;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }

        return marked[t];
    }

    public FlowNetwork flowNetwork (){ return flowNetwork;}

    public double value(){ return value; }

    public boolean inCut(int v){ return marked[v]; }
}
