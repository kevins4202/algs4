#include <iostream>
#include "Bags.h"

using namespace std;

int main(){
    // BagLL<int> b;
    
    // b.insert(1);
    // b.print();
    // cout<<b.first->data<<endl;

    BagLL<int> b;
    // b.print();
    for(int i = 1; i <=4; i++){
        b.insert(i);
    }
    // cout<<"i"<<endl;
    b.print();
    cout<<b.sample()<<endl;
    // for(int i = 1; i <=4; i++){
    //     b.dequeue();
    //     b.print();
    // }
    // for(int i = 1; i <=4; i++){
    //     b.insert(i);
    // }
    // // cout<<"i"<<endl;
    // b.print();
    // for(int i = 1; i <=4; i++){
    //     b.dequeue();
    //     b.print();
    // }
    // for(int i = 1; i <=4; i++){
    //     b.insert(i);
    // }
    // // cout<<"i"<<endl;
    // b.print();
    // for(int i = 1; i <=4; i++){
    //     b.dequeue();
    //     b.print();
    // }
    // for(int i = 1; i <=4; i++){
    //     b.insert(i);
    // }
    // // cout<<"i"<<endl;
    // b.print();
    // for(int i = 1; i <=4; i++){
    //     b.dequeue();
    //     b.print();
    // }
    // // b.dequeue();
    // b.print();

}