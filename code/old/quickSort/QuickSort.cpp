#include <iostream>
#include <vector>
#include "QuickSort.h"
using namespace std;

int main(){
    freopen("input2.txt", "r", stdin);
    ThreeWayQuickSort<int> qs;
    int n;
    cin>>n;
    vector<int> arr(n);
    for(int i = 0; i < n; i++){
        cin>>arr[i];
    }

    qs.sort(arr);

    for(int i : arr) cout<<i<<" ";
    cout<<endl;

    
}