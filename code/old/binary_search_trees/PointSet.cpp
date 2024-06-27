#include <iostream>
#include "PointSet.h"
using namespace std;

int main(){
    kdtree tree;

    Point2D p1(1,1);
    Point2D p2(2,3);
    Point2D p3(0,0);

    tree.insert(p1);
    tree.insert(p2);
    tree.insert(p3);

    // cout<<"inserted"<<endl;
    // cout<<tree.points().size()<<endl;

    // tree.remove(p1);
    // auto ps = tree.points();

    // RectHV rect(2,2,3,3);
    cout<<tree.contains(p1)<<endl;;
    // ps[1].toString();
}