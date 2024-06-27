#include <iostream>
#include <queue>
// #include <priority_queue>
#include "Graphs.h"
// #include "EdgeWeightedGraph.h"
#include "/Users/kevins/Documents/programming/princeton_algs_class/ds_algo_code/percolation/cpp/UF.h"

using namespace std;

class edgeCmp {
public:
    bool operator() (Edge e1, Edge e2) {
        return !e1.compareTo(e2);
    }
};

class Kruskal{
    private:
        queue<Edge> mst;
        priority_queue<Edge, vector<Edge>, edgeCmp> pq;

    public:
        Kruskal(EdgeWeightedGraph dg){
            // cout<<"hello"<<endl;
            for(Edge e : dg.edges()){
                pq.push(e);
            }

            // cout<<"hello"<<endl;

            UF uf(dg.v());

            while(!pq.empty() && mst.size() < dg.v()-1){
                Edge e = pq.top();
                pq.pop();

                int v = e.either();
                int w = e.other(v);

                if(!uf.connected(v,w)){
                    uf.link(v,w);
                    mst.push(e);
                }
            }
        }

        queue<Edge> edges(){
            return mst;
        }
};

class Prim{//lazy
    private:
        queue<Edge> mst;
        priority_queue<Edge, vector<Edge>, edgeCmp> pq;
        vector<bool> marked;

        void visit(EdgeWeightedGraph dg, int v){
            marked[v] = true;

            for(Edge e : dg.adjs(v)){
                if(!marked[e.other(v)]){
                    pq.push(e);
                }
            }
        }

    public:
        Prim(EdgeWeightedGraph dg){
            marked.resize(dg.v());

            visit(dg, 0);

            while(!pq.empty() && mst.size() < dg.v()-1){
                Edge e = pq.top();
                pq.pop();

                int v = e.either();
                int w = e.other(v);

                if(marked[v] && marked[w]) continue;

                mst.push(e);

                if(!marked[v]) visit(dg, v);
                if(!marked[w]) visit(dg, w);
            }
        }

        queue<Edge> edges(){
            return mst;
        }
};

//eager version
class PrimEager{
    
};