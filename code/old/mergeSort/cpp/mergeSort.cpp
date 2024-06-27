#include <iostream>
#include <vector>

using namespace std;

void merge(vector<int> &arr, int lo, int mid, int hi);

void mergeSort(vector<int> &arr, int lo, int hi){
    // cout<<"MERGESORT ";
    // for(int i = lo; i <= hi; i++) cout<<arr[i]<<" ";
    // cout<<endl;

    if(lo ==hi){
        return;
    }
    mergeSort(arr, lo, (lo+hi)/2);
    mergeSort(arr, (lo+hi)/2+1, hi);
    merge(arr, lo, (lo+hi)/2, hi);
}

void merge(vector<int> &arr, int lo, int mid, int hi){
    int i = lo, j = mid+1, index = lo;
    vector<int> aux(arr.size());
    for(int k = lo; k <=hi; k++){
        aux[k] = arr[k];
    }
    
    // cout<<"two arrays: "<<endl;
    // for(int x = i; x <=mid; x++) cout<<arr[x]<<" ";
    // cout<<endl;
    // for(int x = mid+1; x <=hi; x++) cout<<arr[x]<<" ";
    // cout<<endl;

    while(i <=mid && j <=hi){
        if(aux[i] < aux[j]){
            arr[index] = aux[i];
            i++;
        } else{
            arr[index] = aux[j];
            j++;
        }
        // cout<<"pushed "<<arr[index]<<endl;
        index++;
    }

    // cout<<"1st Merging: ";
    // for(int x = lo; x <= hi; x++) cout<<aux[x]<<" ";
    // cout<<endl;
    for(int I = i; I <=mid; I++){
        arr[index] = aux[I];
        index++;
    }

    for(int I = j; I <=hi; I++){
        arr[index] = aux[I];
        index++;
    }
    // cout<<"After Merging: ";
    // for(int i = lo; i <= hi; i++) cout<<arr[i]<<" ";
    // cout<<endl;
}

int main(){
    // freopen("arr.in", "r", stdin);
    int n; cin>>n;
    srand(100);
    vector<int> array;
    for(int i = 0; i < n; i++){
        int a
         = (int) (rand() %1000)
        // ;cin>>a
        ;array.push_back(a);
    }
    int start = 0, end = n-1, mid = (start+end)/2;

    mergeSort(array, start, end);

    for(int i : array) cout<<i<<" ";
    cout<<endl;
}