#include <iostream>
#include <vector>
#include "InsertionSort.h"

using namespace std;

int main(){
    freopen("input.txt", "r", stdin);
    int n;
    cin>>n;

    vector<int> arr(n);

    for(int i = 0; i < n; i++){
        cin>>arr[i];
    }

    InsertionSort<int> insertion;

    insertion.sort(arr);

    for(int i : arr) cout<<i<<" ";
    cout<<endl;
}
