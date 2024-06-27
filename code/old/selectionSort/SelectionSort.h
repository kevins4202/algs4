#include <iostream>
#include <vector>

using namespace std;

template <typename T>
class SelectionSort{
    void swap(vector<T> &a, int i, int j){
        T element = a[i];

        a[i] = a[j];
        a[j] = element;
    }

    public:
        void sort(vector<T> &a){
            for(int i = 0; i < a.size(); i++){
                int minI = i;
                for(int j = i; j < a.size(); j++){
                    if(a[j] < a[minI]) minI = j;
                }

                swap(a,i, minI);
            }
        }
};