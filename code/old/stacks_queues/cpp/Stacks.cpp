#include <iostream>
#include "Stacks.h"

using namespace std;

int main(){
    StackLL<int> s;

    s.print();
    for(int i = 1; i <= 9; i++){
        s.insert(i);
    }
    s.print();
    for(int i = 1; i <= 9; i++){
        s.dequeue();
    }
    s.print();
    // cout<<s.size()<<endl;
}