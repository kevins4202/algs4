#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <sstream>
#include <stack>

#pragma once

using namespace std;

class Digraph{
    private:
        

    public:
        vector<vector<int> > adj;
        int V, E;
        vector<int> indegree;

        Digraph(){
            V=0; E=0;
            indegree = vector<int>();
            adj = vector<vector<int> >();
        }

        Digraph(string f){
            freopen(f.c_str(), "r", stdin);

            cin>>V>>E;
            adj = vector<vector<int> > (V);
            indegree = vector<int> (V);

            for(int i = 0; i < E; i++){
                int v, w; cin>>v>>w;

                addEdge(v,w);
            }
        }

        Digraph(int V) {
            this->V = V;
            E = 0;
            indegree = vector<int> (V);
            adj = vector<vector<int> > (V);
        }

        Digraph(Digraph &G) {
            this->V = G.V;
            this->E = G.E;

            // update indegrees
            indegree = vector<int> (V);
            for (int v = 0; v < V; v++)
                this->indegree[v] = G.indegree[v];

            // update adjacency lists
            adj = vector<vector<int> >(V);

            for (int v = 0; v < G.V; v++) {
                // reverse so that adjacency list is in same order as original
                vector<int> reverse;
                for (int w : G.adj[v]) {
                    reverse.push_back(w);
                }
                for (int w : reverse) {
                    adj[v].push_back(w);
                }
            }
        }

        int v(){ return V;}
        int e(){ return E;}

        Digraph reverse() {
            Digraph reverse(V);
            for (int v = 0; v < V; v++) {
                for (int w : adj[v]) {
                    reverse.addEdge(w, v);
                }
            }
            return reverse;
        }

        string toString() {
            string s = to_string(V) + " vertices, " + to_string(E) + " edges\n";
            for (int v = 0; v < V; v++) {
                s += to_string(v)+": ";
                for (int w : adj[v]) {
                    s+= to_string(w)+" ";
                }
                s+= "\n";
            }
            return s;
        }

        void addEdge(int v, int w) {
            adj[v].push_back(w);
            indegree[w]++;
            E++;
        }

        vector<int> adjs(int v){
            return adj[v];
        }

        int outd(int v){
            return adj[v].size();
        }

        int ind(int v){
            return indegree[v];
        }
};

class DirectedDFS{
    private:
        vector<int> vis;
        int cnt; //# vertices reachable from source

        void dfs(Digraph G, int v){
            cnt++;
            vis[v] = true;
            for(int w : G.adj[v]) {
                if(!vis[w]) dfs(G, w);
            }
        }

        void validateVertex(int v){
            int V = vis.size();

            if(v < 0 || v >= V) throw invalid_argument("not valid");
        }

        void validateVertices(vector<int> v){
            int V = vis.size();

            int vertexCount = 0;
            for(int w : v){
                vertexCount++;
                validateVertex(w);
            }

            if(vertexCount ==0) throw invalid_argument("Not valid");
        }
    public:
        DirectedDFS(Digraph G, int s){
            cnt = 0;
            vis = vector<int> (G.v(), 0);
            validateVertex(s);
            dfs(G,s);
        }

        DirectedDFS(Digraph G, vector<int> sources){
            cnt = 0;
            vis.resize(G.v());
            validateVertices(sources);
            for(int v : sources){
                if(!vis[v]) dfs(G,v);
            }
        }
        
        bool marked(int v){
            validateVertex(v);
            return vis[v];
        }

        int count(){
            return cnt;
        }
};