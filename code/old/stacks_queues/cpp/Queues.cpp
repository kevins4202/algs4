#include <iostream>
#include "Queues.h"

using namespace std;

int main(){
    QueueArr<int> s;

    s.print();
    for(int i = 1; i <= 9; i++){
        s.insert(i);
    }
    s.print();
    for(int i = 1; i <= 3; i++){
        s.dequeue();
    }

    s.print();
    s.fl();
    for(int i = 10; i <= 18; i++){
        s.insert(i);
    }
    s.print();

    
    for(int i = 1; i <= 8; i++){
        s.dequeue();
    }
    s.print();
    s.fl();
    s.insert(52);
    s.print();
    // cout<<s.size()<<endl;
}