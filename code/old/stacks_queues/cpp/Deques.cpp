#include <iostream>
#include "Deques.h"

using namespace std;

int main(){
    DequeLL<int> d;
    d.addFirst(1);
    d.print();
    d.addLast(2);
    d.addFirst(3);
    d.print();
    d.removeLast();
    d.print();
    d.removeFirst();
    d.print();
    cout<<"hi"<<endl;
    d.removeLast();
    d.print();
}