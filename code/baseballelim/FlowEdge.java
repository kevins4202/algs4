public class FlowEdge {
    private final int from, to;
    private final double capacity;
    private double flow;

    public FlowEdge(int from, int to, double capacity){
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    int other(int v){
        if(v == from) return to;
        else if(v == to) return from;
        else throw new IllegalArgumentException("node is not part of edge");
    }

    public int from(){ return from; }

    public int to(){ return to; }

    public double capacity(){ return capacity; }

    public double flow(){ return flow; }

    public boolean isFull(){ return flow() == capacity(); }

    public double residualCapacityTo(int v){
        if(v == from) return flow;
        else if(v == to) return capacity - flow;
        else throw new IllegalArgumentException();
    }

    public void addResidualFlow(int v, double delta){
        if(v == from) flow -= delta;
        else if(v == to) flow += delta;
        else throw new IllegalArgumentException();
    }
}
