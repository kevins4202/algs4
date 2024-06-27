#include <iostream>
#include <string>
#include <vector>
#include <set>

using namespace std;

class KnuthMorrisPratt{
    //Deterministic Finite automaton
    private:
        string toSearch;
        vector<vector<int> > states;
        const int R = 256;
        int M = 0;

    public:
        KnuthMorrisPratt(string s):toSearch(s){
            M = s.size();
            states.resize(R, vector<int> (M, 0));

            int X = 0;//state if you simulated from the second character
            states[(int) s[0]][0] = 1;

            for(int i = 1; i < M; i++){
                int at = (int) toSearch[i];

                for(int j = 0; j < R; j++){
                    states[j][i] = states[j][X];
                }

                states[at][i] = i+1; //if in state i, and and character matches, go to next state
                
                 // for other characters: simulate 1 up to j-1 on DF (state X) and take transition c (as if backed up)
                // maintain a state X- where would it be if we started the partern shifted one over? 

                    
                X = states[at][X];
                
            }
           
    
        }

        int search(string key){
            int i = 0, j = 0, N = key.size();

            for(; i < N && j < M; i++){
                //get the current state
                j = states[key[i]][j];
            }

            if(j==M) return i-M;//found
            else return N; //not found
        }
};

class BoyerMoore{//mistmatched character heuristic
    //precompute rightmost occurrence of each letter
    private:
        string txt;
        vector<int> right;
        const int R = 256;

    public:
        BoyerMoore(string s):txt(s){
            right = vector<int> (R, -1);

            for(int i = 0; i < s.size(); i++){
                right[(int) s[i]] = i;
            }
        }

        int search(string key){
            int N = txt.size();
            int M = key.size();

            int skip;

            for(int i = 0; i <= N-M; i+=skip){
                skip = 0; 
                for(int j = M-1; j >=0; j--){
                    if(key[j] != txt[i+j]){
                        // Case 1: character not in pattern, shift j+1
                        // Case 2a: mismatched character in pattern -> move to that character
                        // Case 2b: mismatched character in pattern but we have to move backwards to line up with that -> move forward 1
                        skip = max(1, j - right[txt[i+j]]);
                        break;
                    }
                }

                if(skip == 0) return i;
            }

            return N;
        }


};

class RabinKarp{//modular hashing
    
    private:
        string pat;
        const int R = 256;
        long Q;//mod
        long RM;//R^(M-1) % Q
        long patHash; //pattern hash
        int N;
        int M;

        long hash(string key, int k){
            long h = 0;

            for(int i = 0; i < k; i++){
                h = (R * h + key[i]) % Q;
            }

            return h;
        }

    public:
        RabinKarp(string s):pat(s), M(s.size()){
            Q = 997;

            RM = 1;
            for(int i = 1; i < M; i++)
                RM = (R * RM) % Q;
            
            patHash = hash(pat, M);
        }

        int search(string txt){
            // cout<<"PATTERN "<<patHash<<endl;
            long txtHash = hash(txt, M);
            N = txt.size();
            if(patHash == txtHash) return 0;
            // cout<<N<<M<<endl;
            for(int i = M; i < N; i++){
                txtHash = (txtHash + Q - RM * txt[i-M] % Q) % Q;
                txtHash = (txtHash * R + txt[i]) % Q;

                // cout<<i<<" "<<txtHash<<endl;

                if(patHash == txtHash) return i - M + 1;
            }


            return N;
        }


};
