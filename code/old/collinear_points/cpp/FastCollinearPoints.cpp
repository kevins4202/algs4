#include <iostream>
#include "collinear.h"
#include <vector>
#include <algorithm>
#include <climits>
#include <map>
#include <set>
using namespace std;

vector<Point> points;         // the points
vector<LineSegment> segments; // segments
map<double, vector<Point> > m;

bool cmp(pair<double, Point> &a, pair<double, Point> &b)
{
    if (a.first == b.first)
    {
        return a.second.compareTo(b.second) < 0;
    }

    return a.first < b.first;
}

int main()
{
    freopen("in8.txt", "r", stdin);

    int numPoints;
    cin >> numPoints;

    for (int i = 0; i < numPoints; i++)
    {
        int a, b;
        cin >> a >> b;
        Point p(a, b);
        points.push_back(p);
    }
    sort(points.begin(), points.end());
    for (Point p : points)
        cout << p.toString() << endl;

    // cout<<"read in"<<endl;

    for (int i = 0; i < numPoints; i++)
    {
        // slope_components.clear();
        m.clear();
        Point origin = points[i]; // origin
        // cout << endl
        //      << "ORIGIN " << origin.toString() << endl;
        vector<pair<double, Point> > slopes; // every point's slope to the origin and its index in the points vector
        for (int j = 0; j < numPoints; j++)
        {
            if (i == j)
                continue;
            double slope = origin.slopeTo(points[j]);
            slopes.push_back(make_pair(slope, points[j]));
        }

        sort(slopes.begin(), slopes.end());
        // cout << "Slopes" << endl;
        // for (auto x : slopes)
        //     cout << x.first << " " << x.second.toString() << endl;

        int first = 0, run = 1;
        Point start(origin), end(origin);
        //iterate through slopes to find the segments
        for(int j = 0; j < numPoints-1; j++){
            if(slopes[j].first == slopes[j+1].first){
                start = min(slopes[j].second, start);
                end = max(slopes[j].second, end);
                run++;
            } else{
                if(run >= 3){
                    start = min(slopes[j].second, start);
                    end = max(slopes[j].second, end);
                    LineSegment seg (start, end);
                    bool ok = true;
                    for(LineSegment s : segments) {
                        if(s == seg) ok = false;
                    }

                    if(ok) segments.push_back(seg);
                    // cout<<"RESULT: "<<seg.toString()<<endl;
                    
                } 
                run = 1;
                first = j+1;
                
                start = origin.copy();
                end = origin.copy();
            }
        }

        if(run >= 3){
                start = min(slopes[numPoints-2].second, start);
                end = max(slopes[numPoints-2].second, end);
                LineSegment seg (start, end);
                bool ok = true;
                for(LineSegment s : segments) {
                    if(s == seg) ok = false;
                }
                if(ok) segments.push_back(seg);
                // cout<<"RESULT: "<<seg.toString()<<endl;
            }
    }
    cout<<endl;
    for (auto x : segments)
        cout << x.toString() << endl;
}