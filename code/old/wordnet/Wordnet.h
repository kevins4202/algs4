#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <climits>
#include "Digraph.h"
#include <map>
#include <stack>
#include <set>
#include <sstream>
#include <algorithm>


using namespace std;

class SAP{
    public:
        Digraph dg;
        int anc;

        SAP(){}

        SAP(Digraph g){
            dg = g;
        }

        int length(int v, int w){
            vector<int> a = {v}, b = {w};

            return length(a,b);
        }

        int ancestor(int v, int w){
            vector<int> a = {v}, b = {w};

            return ancestor(a,b);
        }

        int length(vector<int> v, vector<int> w){
            vector<int> dists(dg.v(), -1);

            stack<pair<int, int> > s;//current node, distance

            for(int y : v) s.push(make_pair(y,0));

            while(!s.empty()){
                auto curr = s.top();

                s.pop();
                int loc = curr.first;
                dists[loc] = curr.second;

                for(int x : dg.adjs(loc)){
                    if(dists[x] ==-1) s.push(make_pair(x,curr.second+1));
                }
            }

            int minL = INT_MAX;

            stack<pair<int, int> > s2;//current node, distance
            vector<int> vis(dg.v(), 0);

            for(int y : w) s2.push(make_pair(y,0));

            while(!s2.empty()){
                auto curr = s2.top();

                s2.pop();
                int loc = curr.first;
                // cout<<loc<<endl;
                if(dists[loc] != -1) {
                    if(dists[loc] + curr.second < minL){
                        minL = dists[loc]+curr.second;
                        anc = loc;
                    }
                    
                }

                for(int x : dg.adjs(loc)){
                    if(!vis[x]) s2.push(make_pair(x,curr.second+1));
                }
            }

            return minL == INT_MAX ? -1 : minL;
        }

        int ancestor(vector<int> v, vector<int> w){
            if(length(v,w)==-1) return -1;
            return anc;
        }
};

class Wordnet{
    private: 
        
        
    public:
        string syns, hyps;
        int n_count;
        map<int, string> nouns;
        vector<string> all_nouns;
        map<string, set<int> > synsets;
        Digraph hypernyms;
        SAP sap;

        // FILE* s_file, h_file;

        Wordnet(){}

        Wordnet(string s, string h):syns(s), hyps(h), n_count(0){
            // s_file = freopen(syns.c_str(), "r", stdin);
            ifstream s_file( syns.c_str(), ios::in );
            string input;
            while(getline(s_file, input))
            {
                // cout<<input<<endl;
                if (!input.empty()){
                    n_count++;
                    vector<string> tmp;
 
                    stringstream ss(input);
                
                    while (ss.good()) {
                        string substr;
                        getline(ss, substr, ',');
                        // cout<<substr<<endl;
                        tmp.push_back(substr);
                    }

                    // cout<<"h"<<endl;

                    int index = stoi(tmp[0]);

                    // cout<<"h"<<endl;
                    stringstream ss2(tmp[1]);
                
                    while (ss2.good()) {
                        string substr;
                        getline(ss2, substr, ' ');
                        
                        all_nouns.push_back(substr);
                        if(synsets.find(substr) != synsets.end()){
                            synsets[substr].insert(index);
                        } else{
                            synsets[substr] = set<int>();
                            synsets[substr].insert(index);
                        }
                    }

                    
                }
                else
                    break;
            }
            //hypernyms
            hypernyms = Digraph(n_count);
            // cout<<n_count<<endl;

            
            s_file.close();
            // cout<<"HYPERNYMS"<<endl;
            ifstream h_file( hyps.c_str(), ios::in );
            // cin>>input; cout<<input<<endl;
            while(getline(h_file, input))
            {
                // cout<<input<<endl;
                if (!input.empty()){
                    n_count++;
                    vector<string> tmp;
 
                    stringstream ss(input);
                
                    while (ss.good()) {
                        string substr;
                        getline(ss, substr, ',');
                        tmp.push_back(substr);

                        // cout<<"NEW "<<substr<<endl;
                    }

                    int v = stoi(tmp[0]);

                    for(int i = 1; i < tmp.size(); i++){
                        hypernyms.addEdge(v,stoi(tmp[i]));
                    }    
                }
                else
                    break;
            }

            h_file.close();

            sap = SAP(hypernyms);
        }

        vector<string> getNouns(){
            return all_nouns;
        }

        bool isNoun(string word){
            return find(all_nouns.begin(), all_nouns.end(), word) != all_nouns.end();
        }

        int distance(string nounA, string nounB){
            if(!isNoun(nounA) || !isNoun(nounB)) throw invalid_argument("Invalid nouns");

            auto a = synsets[nounA];
            auto b = synsets[nounB];

            vector<int> va (a.begin(), a.end()), vb(b.begin(), b.end());

            return sap.length(va, vb);
        }

        // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
        // in a shortest ancestral path (defined below)
        string sapath(string nounA, string nounB){

        }
};



class Outcast{
public:
    Wordnet w;

    Outcast(){}

    Outcast(Wordnet wn):w(wn){}

    string outcast(vector<string> v){
        int maxD = 0;
        string ret = "";

        for(string s : v){
            if(!w.isNoun(s)) throw invalid_argument("Invalid noun");
            int tmp = 0;

            for(string ss : v){
                tmp += w.distance(s,ss);
            }

            if(tmp > maxD){
                maxD = tmp;
                ret = s;
            }
        }

        return ret;
    }
};