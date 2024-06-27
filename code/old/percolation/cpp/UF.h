#include <iostream>

using namespace std;

class UF{
    private:
        vector<int> parent;
        vector<int> size;
        int count;
 
    public:
        UF(int size) : parent(size), size(size, 1), count(size) {
            for (int i = 0; i < size; i++) { 
                parent[i] = i;
            }
        }

        int cnt(){ return count;}
    
        int get_top(int n) {
            return parent[n] == n ? n : (parent[n] = get_top(parent[n]));
        }

        bool connected(int p, int q){
            return get_top(p)==get_top(q);
        }
    
        bool link(int n1, int n2) {
            n1 = get_top(n1);
            n2 = get_top(n2);
            if (n1 == n2) { return false; }
            if (size[n2] > size[n1]) { return link(n2, n1); }
            parent[n2] = n1;
            size[n1] += size[n2];
            return true;
        }
};