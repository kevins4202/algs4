#include <iostream>
#include "heapSort.h"
#include <vector>

using namespace std;

int main(){
    freopen("input.txt", "r", stdin);
    int n; cin>>n;
    vector<int> v(n);
    for(int i = 0; i < n; i++){
        cin>>v[i];
    }
    
    HeapSort<int> hs(v);

    hs.heapify();
    hs.sort(); 
    for(int i : hs.getSorted()){
        cout << i << " ";
    }   
    cout<<endl;
}