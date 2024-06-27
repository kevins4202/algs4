#include <iostream>
#include <vector>

using namespace std;

template <typename T>
class InsertionSort{
    void swap(vector<T> &a, int i, int j){
        T element = a[i];

        a[i] = a[j];
        a[j] = element;
    }

    public:
        void sort(vector<T> &a){
            for(int i = 1; i < a.size(); i++){
                int j = i;
                T curr = a[i];
                while(j >=1 && a[j-1] > a[j]){
                    swap(a, j, j-1);
                    j--;
                }
            }
        }
};