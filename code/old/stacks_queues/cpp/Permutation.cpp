#include <iostream>
#include "Bags.h"

using namespace std;

int main(){
    freopen("input.txt", "r", stdin);
    int n, k; cin>>n>>k;
    BagArr<int> bag;

    for(int i = 0; i < n; i++){
        int a; cin>>a;
        bag.insert(a);
    }

    for(int i = 0; i < n - k-1; i++){
        bag.dequeue();
    }

    bag.print();
}