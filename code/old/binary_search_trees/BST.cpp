#include <iostream>
// #include "BST.h"
#include "RBBST.h"

using namespace std;

int main(){
    // BST<int, int> bst;
    // freopen("in.txt", "r", stdin);
    // int n; cin>>n;
    // for(int i = 0; i < n; i++){
    //     int a, b; cin>>a>>b;
    //     bst.put(a,b);
    // }
    // // bst.remove(0);
    // cout<<bst.totalSize()<<endl;
    // cout<<"r"<<bst.rank(3)<<endl;
    // bst.inOrder();

    RBBST<int, int> bst;

    bst.put(1, 1);

    bst.put(2, 4);
    bst.put(5,3);
    bst.put(3, 2);

    cout<<bst.rank(4)<<endl;
}