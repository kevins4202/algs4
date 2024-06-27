#include <iostream>
#include "collinear.h"
#include <vector>
#include <algorithm>
using namespace std;

vector<Point> points;
vector<LineSegment> segments;

bool cmp(Point &a, Point &b){
    
    if(a.getY()<b.getY() ||(a.getY() == b.getY() && a.getX()<b.getX())){
        return true;
    } else return false;
}

int main(){
    freopen("in.txt", "r", stdin);

    int numPoints; cin>>numPoints;

    for(int i = 0; i < numPoints; i++){
        int a,b; cin>>a>>b;
        Point p(a,b);
        points.push_back(p);
    }

    sort(points.begin(), points.end(), cmp);

    // for(Point p : points) cout<<p.toString()<<endl;

    for(int i = 0; i < numPoints-3; i++){
        for(int j = i+1; j < numPoints-2; j++){
            for(int k = j+1; k < numPoints-1; k++){
                for(int l = k+1; l < numPoints; l++){
                    // cout<<points[i].toString() <<" " <<points[j].toString() <<" "<<points[k].toString() <<" "<<points[l].toString() <<endl;
                    // cout<<to_string(points[i].slopeTo(points[j])==points[j].slopeTo(points[k]))<<" "<<points[k].slopeTo(points[l])<<endl;
                    if(points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) && points[j].slopeTo(points[k])==points[k].slopeTo(points[l])){
                        LineSegment s(points[i],points[l]);
                        if(find(segments.begin(), segments.end(), s) == segments.end()) segments.push_back(s);
                    }
                }
            }
        }
    }

    // cout<<segments.size()<<endl;

    for(LineSegment s : segments) cout<<s.toString()<<endl;
}