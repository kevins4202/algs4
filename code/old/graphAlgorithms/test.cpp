#include "Algos.h"

#include <iostream>

using namespace std;

int main(){
    // EdgeWeightedDigraph dg (5);
    EdgeWeightedGraph g(9);

    //create and add above edges to graph
    Edge e1(1,7,6);
    Edge e2(2,8,2);
    Edge e3(2,6,5);
    Edge e4(4,0,1);
    Edge e5(4,2,5);
    Edge e6(6,8,6);
    Edge e7(7,2,3);
    Edge e8(7,7,8);
    Edge e9(8,0,7);
    Edge e10(8,1,2);
    Edge e11(9,3,4);
    Edge e12(10,5,4);
    Edge e13(11,1,7);
    Edge e14(14,3,5);

    // cout<<"created edges"<<endl;

    g.addEdge(e1);
    g.addEdge(e2);
    g.addEdge(e3);
    g.addEdge(e4);
    g.addEdge(e5);
    g.addEdge(e6);
    g.addEdge(e7);
    g.addEdge(e8);
    g.addEdge(e9);
    g.addEdge(e10);
    g.addEdge(e11);
    g.addEdge(e12);
    g.addEdge(e13);
    g.addEdge(e14);   

    

    Kruskal k(g);

    Prim p(g);
    
    vector<Edge> v;
    auto q = k.edges();
    while (!q.empty())
    {
        v.push_back(q.front());
        q.pop();
    }

    for(Edge e : v){
        cout<<e.toString()<<endl;
    }

    cout<<"------------------"<<endl;   

    vector<Edge> v2;
    auto q2 = p.edges();
    while (!q2.empty())
    {
        v2.push_back(q2.front());
        q2.pop();
    }

    for(Edge e : v2){
        cout<<e.toString()<<endl;
    }
    
}