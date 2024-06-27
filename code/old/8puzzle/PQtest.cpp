#include <iostream>
#include "PQ.h"

using namespace std;

int main(){
    MaxPQ<int> pq;
    pq.insert(5);
    pq.insert(8);
    pq.insert(10);
    pq.insert(7);
    pq.insert(4);
    pq.insert(6);
    pq.insert(9);
    pq.printPQ();
    pq.delMax();
    pq.printPQ();
    // pq.delMin();
    // pq.printPQ();
    // pq.delMin();
    // pq.printPQ();
    // pq.delMin();
    // pq.printPQ();
    // pq.delMin();
    // pq.printPQ();
    // pq.delMin();
    // pq.printPQ();
    // pq.delMin();
    // pq.insert(9);
    // pq.printPQ();


    pq.printPQ();
}