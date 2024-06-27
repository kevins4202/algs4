#include <iostream>
#include <string>
#include <vector>
#include "/Users/kevins/Documents/programming/princeton_algs_class/ds_algo_code/stacks_queues/cpp/Bags.h"
using namespace std;

class Edge{
    private:
        int v,w;
        double weight;
    
    public:
        Edge():v(0), w(0), weight(0){}

        Edge( double w,int from, int to):v(from), w(to), weight(w){

        }

        double wt(){ return weight;}

        int either(){ return v;}
        
        int other(int i){
            if(i == v) return w;
            else if(i == w) return v;

            else throw std::invalid_argument("not an endpoint");
        }

        bool compareTo(Edge that){
            return weight < that.weight;
        }

        string toString(){
            return to_string(v) + " " + to_string(w) + " " + to_string(weight);
        }
};

class EdgeWeightedDigraph{
    private:
        int V;
        vector<vector<Edge> > adj;

    public:
        EdgeWeightedDigraph (int v): V(v){
            adj.resize(V);

            for(int i = 0; i < V; i++){
                adj[i] = vector<Edge>();
            }
        }

        void addEdge(Edge e){
            int v = e.either();
            int w = e.other(v);

            adj[v].push_back(e);
            // adj[w].insert(e);
        }

        vector<Edge> adjs(int v){
            return adj[v];
        }
        
        string toString(){
            string s = "";

            for(int i = 0; i < V; i++){
                s += to_string(i)+"\n";
                for(auto e : adjs(i)){
                    s += e.toString() + "\n";
                }
            }

            return s;
        }
};

class EdgeWeightedGraph{
    private:
        int V;
        vector<vector<Edge> > adj;

    public:
        EdgeWeightedGraph (int v): V(v){
            adj = vector<vector<Edge> >(V);

            for(int i = 0; i < V; i++){
                adj[i] = vector<Edge>();
            }

            // cout<<"created graph"<<endl;    
        }

        int v(){ return V;}

        void addEdge(Edge e){
            // cout<<"s"<<endl;
            int v = e.either();
            int w = e.other(v);

            adj[v].push_back(e);
            adj[w].push_back(e);
            // cout<<"e"<<endl;
        }

        vector<Edge> adjs(int v){
            return adj[v];
        }

        vector<Edge> edges(){
            vector<Edge> b;

            for(int i = 0; i < V; i++){
                for(Edge e : adjs(i)){
                    b.push_back(e);
                }
            }

            return b;
        }
        
        string toString(){
            string s = "";

            for(int i = 0; i < V; i++){
                s += to_string(i)+"\n";
                for(auto e : adjs(i)){
                    s += e.toString() + "\n";
                }
            }

            return s;
        }
};