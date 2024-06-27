#include <iostream>
#include <vector>
#include "/Users/kevins/Documents/programming/princeton_algs_class/ds_algo_code/8puzzle/PQ.h"

using namespace std;

template <class T>
class HeapSort {
    private:
        MaxPQ<T> pq;
        vector<T> sorted;

    public:
        HeapSort(vector<T> &v) {
            // //method 1: construct heap 1 by 1
            // for (int i = 0; i < v.size(); i++) {
            //     pq.insert(v[i]);
            // }
            // while (!pq.isEmpty()) {
            //     sorted.push_back(pq.delMax());
            // }

            //method 2: construct pq and then bottom up heapify
            pq = MaxPQ<T>(v);
            // pq.printPQ();
        }

        void heapify(){
            int n = pq.num();
            for (int k = n/2; k >= 1; k--) {
                pq.sink(k, n);
            }
            // pq.printPQ();
            // cout<<"heapified^^"<<endl;
        }

        void sort(){
            int n = pq.num();
            while(n>1){
                pq.swap(1, n--);
                // pq.delLast();
                pq.sink(1, n);
                // pq.printPQ();
            }

            // pq.updateNum(save);

            for(int i = 1; i <= pq.num(); i++){
                sorted.push_back(pq.getItem(i));
            }
        }

        vector<T> getSorted() {
            return sorted;
        }
};