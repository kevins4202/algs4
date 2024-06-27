#include </Users/kevins/stdc++.h>
#include "/Users/kevins/Documents/programming/princeton_algs_class/ds_algo_code/wordnet/Digraph.h"
using namespace std;

// supports ( ), ., *, |
class Regex
{
private:
    int N;
    string pat;
    stack<int> ops;
    Digraph G;

public:
    Regex(string s) : pat(s), N(s.size())
    {
        G = Digraph(N + 1);
        // construct the NFA
        for (int i = 0; i < N; i++)
        {
            int lp = i;
            if (pat[i] == '(' || pat[i] == '|')
                ops.push(i);
            else if (pat[i] == ')')
            {
                int OR = ops.top(); // location of or symbol
                ops.pop();

                if (pat[OR] == '|')
                {
                    lp = ops.top();
                    ops.pop();
                    G.addEdge(lp, OR + 1);
                    G.addEdge(OR, i);
                }
                else
                    lp = OR;
            }

            if (i < N - 1 && pat[i + 1] == '*')
            { // closure
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }

            if (pat[i] == '(' || pat[i] == '*' || pat[i] == ')')
                G.addEdge(i, i + 1); //metasymbols
        }
    }

    bool check(string s){
        vector<int> pc;

        DirectedDFS dfs(G, 0);

        for(int v = 0; v < G.v(); v++){
            if(dfs.marked(v)) pc.push_back(v);
        }
        
        for(int i = 0; i < s.size(); i++){
            vector<int> match;// states reachable after scanning

            for(int v : pc){
                if(v==N) continue;

                if(pat[v] == s[i] || pat[v] == '.'){
                    match.push_back(v+1);
                }
            }

            //follow epsilon transitions

            dfs = DirectedDFS(G, match);

            pc = vector<int> ();

            for(int v = 0; v < G.v(); v++){
                if(dfs.marked(v)) pc.push_back(v);
            }
        }
        //if reached end
        for(int v : pc){
            if(v == N) return true;
        }

        return false;
    }
};