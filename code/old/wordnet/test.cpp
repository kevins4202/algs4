#include <iostream>
#include "Digraph.h"

#include "Wordnet.h"
using namespace std;

int main(){
    // Digraph d("tinyDG.txt");

    // cout<<d.toString()<<endl;

    Wordnet w("synsets.txt", "hypernyms.txt");
    // Digraph d("tinyDG.txt");
    // cout<<d.toString()<<endl;
    // SAP s(d);

    // int a = 3, b = 3;
    
    // cout<<s.length(a,b)<<endl;
    // cout<<s.ancestor(a,b)<<endl;
    // freopen("output.txt", "w", stdout);
    // cout<<w.hypernyms.toString()<<endl;
    freopen("outcast5.txt", "r", stdin);

    vector<string> s;
    string input;
    while(cin>>input){
        s.push_back(input);
    }
    Outcast o(w);
    cout<<o.outcast(s)<<"\n";
}