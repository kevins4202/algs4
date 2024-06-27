#include <iostream>
#include <vector>
using namespace std;

template <typename T>
class MinPQ{
    T* pq;
    int numElements, arrSize;
    public:
        MinPQ(){
            pq = new T[2];
            arrSize = 2;
            numElements = 0;
        }

        MinPQ(vector<T> &v){
            int sz = 2;
            while(sz < v.size()){
                sz *= 2;
            }

            pq = new T[sz];
            for(int i = 0; i < v.size(); i++){
                pq[i+1] = v[i];
            }

            arrSize = sz;
            numElements = v.size();
        }

        int num(){ return numElements;}

        bool isEmpty(){ return numElements ==0;}

        T &minimum(){ return pq[1];}

        void resize(int newSz){
            T* newArr = new T[newSz];

            for(int i = 0; i < arrSize; i++){
                newArr[i] = pq[i];
            }

            arrSize = newSz;
            pq = newArr;
        }

        void swap(int i, int j){
            T tmp = pq[i];
            pq[i] = pq[j];
            pq[j] = tmp;
        }

        void swim(int k){
            while(k > 1 && pq[k/2] > pq[k]){
                swap(k, k/2);
                k/=2;
            }
        }

        void insert(T item){
            if(numElements + 1 == arrSize){
                resize(2*arrSize);
            }

            pq[++numElements] = item;
            swim(numElements);
        }

        void sink(int k, int N){
            while(2*k <= N){
                int j = 2*k;
                if(j < N && pq[j] > pq[j+1]){
                    j++;
                }

                if(!(pq[k] > pq[j])){
                    break;
                }

                swap(k,j);
                k = j;
            }
        }

        T delMin(){
            T m = pq[1];

            swap(1, numElements--);
            sink(1, numElements);
            // pq[numElements+1] = 0;
            return m;
        }

        void printPQ(){
            int c = 2;
            for(int i = 1; i <= numElements; i++){
                if(i == c){
                    cout<<endl;
                    c *=2;
                }
                cout<<pq[i]<<" ";
                
            }
            cout<<endl;
        }
};

template <typename T>
class MaxPQ{
    T* pq;
    int numElements, arrSize;
    public:
        MaxPQ(){
            pq = new T[2];
            arrSize = 2;
            numElements = 0;
        }

        MaxPQ(vector<T> &v){
            int sz = 2;
            while(sz < v.size()){
                sz *= 2;
            }

            pq = new T[sz];
            for(int i = 0; i < v.size(); i++){
                pq[i+1] = v[i];
            }

            arrSize = sz;
            numElements = v.size();
        }

        T getItem(int i){
            return pq[i];
        }

        int num(){ return numElements;}

        int updateNum(int n){ numElements = n; }

        bool isEmpty(){ return numElements ==0;}

        T &minimum(){ return pq[1];}

        void resize(int newSz){
            T* newArr = new T[newSz];

            for(int i = 0; i < arrSize; i++){
                newArr[i] = pq[i];
            }

            arrSize = newSz;
            pq = newArr;
        }

        void swap(int i, int j){
            T tmp = pq[i];
            pq[i] = pq[j];
            pq[j] = tmp;
        }

        void swim(int k){
            while(k > 1 && pq[k/2] < pq[k]){
                swap(k, k/2);
                k/=2;
            }
        }

        void insert(T item){
            if(numElements + 1 == arrSize){
                resize(2*arrSize);
            }

            pq[++numElements] = item;
            swim(numElements);
        }

        void sink(int k, int N){
            while(2*k <= N){
                int j = 2*k;
                if(j < N && pq[j] < pq[j+1]){
                    j++;
                }

                if(!(pq[k] < pq[j])){
                    break;
                }

                swap(k,j);
                k = j;
            }
        }

        T delMax(){
            T m = pq[1];

            swap(1, numElements--);
            sink(1, numElements);
            // pq[numElements+1] = 0;
            return m;
        }

        T delLast(){
            T m = pq[numElements--];
            // pq[numElements+1] = 0;
            return m;
        }

        void printPQ(){
            int c = 2;
            for(int i = 1; i <= numElements; i++){
                if(i == c){
                    cout<<endl;
                    c *=2;
                }
                cout<<pq[i]<<" ";
                
            }
            cout<<endl;
        }
};