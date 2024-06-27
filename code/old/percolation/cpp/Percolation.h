#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

class Percolation{
    private:
        // int n, numSites;
        // Node top, bottom;
        // vector<vector<bool> > opened;
        // vector<vector<Node> > id;
        // vector<vector<int> > sz;

    public:
        int n, numSites;

        vector<bool> opened;
        vector<int> id,sz;
        Percolation(int N) : n(N){
            //initialize all squares to be not opened
            opened.resize(N*N, false);
            // all nodes are trees with size 1
            // add a row at the end to account for the top and bottom nodes
            sz.resize(N*N +2, 1);
            //no sites open
            numSites = 0;
            //all nodes are roots of themselves
            id.resize(N*N+2);

            for(int i = 0; i < N*N+2; i++){
                id[i] = i;
            }
            //connect the top to the top row, bottom to the bottom row
            for(int i = 0; i < N; i++){
                //top row
                join(n*n,i);
                // opened[i] = true;
                //bottom row
                join( n*n+1, n*n-1-i);
                // opened[n*n -1-i] = true;
            }
        }

        void open(int node){
            if(opened[node]) return;
            opened[node] = true;
            numSites++;

            int dx[]= {1,-1,0,0}, dy[] ={0,0,1,-1};
            pair<int, int> curr = toCoords(node);

            for(int i = 0; i < 4; i++){
                int temp = toIndex(make_pair(curr.first + dx[i], curr.second + dy[i]));
                if(isOpen(temp)){
                    join(node,temp);
                }
            }
        }

        pair<int, int> toCoords(int index) {
            int r = (index)/n, c = (index)%n;
            return make_pair(r, c);
        }

        int toIndex(pair<int, int> coord){
            return coord.first * n + coord.second;
        }

        void join(int p, int q){
            int pRoot = root(p);
            int qRoot = root(q);

            if(pRoot == qRoot) return;
            //if sizes are equal, the first one becomes the root

            if(sz[pRoot] < sz[qRoot]){
                //change the root of p to q
                id[pRoot] = qRoot;
                //add the size of p's tree to q's tree
                sz[qRoot] += sz[pRoot];
            } else{
                //change the root of q to p
                id[qRoot] = pRoot;
                //add the size of q's tree to p's tree
                sz[pRoot] += sz[qRoot];
            }
        }

        int root(int p){
            while(!(p == id[p])){
                id[p] = id[id[p]];
                p = id[p];
            }

            return p;
        }

        bool connected(int p, int q){
            return root(p) == root(q);
        }

        bool isFull(int p){
            return connected(p, n*n);
        }
        

        bool percolates(){
            return isFull(n*n+1);
        }
        

        bool isOpen(int p){
            if(p < 0 || p >= n*n) return false;
            return opened[p];
        }
        
        int numOpenSites(){ return numSites;}

};

class PercolationStats{
    private:
        

    public:

        int n,numTrials;
        //the number of open sites after every trial
        vector<int> openSites;
        PercolationStats(int N, int trials): n(N), numTrials(trials) {}

        double mean(){
            double total = 0;
            for(int s : openSites){
                total +=s;
            }

            return total / (n * n * numTrials);
        }

        double stddev(){
            double total = 0;

            for(int s : openSites){
                double frac = (double) s / (n * n);
                double m = mean();
                total += (frac - m) * (frac - m);
            }

            total /= (numTrials -1);
            return sqrt(total);
        }

        double confidenceLo(){
            return mean() - (1.96 * stddev())/sqrt(numTrials);
        }
        
        double confidenceHi(){
            return mean() + (1.96 * stddev())/sqrt(numTrials);
        }

        void addTrial(int t){
            openSites.push_back(t);
        }
};