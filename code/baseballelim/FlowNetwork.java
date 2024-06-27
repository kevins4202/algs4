import java.util.ArrayList;

public class FlowNetwork {
    int V, E;
    ArrayList<FlowEdge>[] adj;

    public FlowNetwork(int V){
        this.V = V;
        adj = (ArrayList<FlowEdge>[]) new ArrayList[V];

        for(int i = 0; i < V; i++) adj[i] = new ArrayList<FlowEdge>();
    }

    public ArrayList<FlowEdge> adj(int v){
        return adj[v];
    }

    public void addEdge(FlowEdge e){
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);

        System.out.println("connect: " + v + " " + w);
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }
}
