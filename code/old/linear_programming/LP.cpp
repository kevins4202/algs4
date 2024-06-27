#include "LP.h"

int main(){
    double ta1[] = {5.0,15.0};
    double ta2[] = {4.0,4.0};
    double ta3[] = {35.0,20.0};
    vector<vector<double> > a ;
    a.push_back(vector<double>(ta1, ta1+2));
    a.push_back(vector<double>(ta2, ta2+2));
    a.push_back(vector<double>(ta3, ta3+2));
    

    double tb[] = {480.0,160.0,1190.0};
    vector<double> b(tb, tb+3);

    double tc[] = {13.0,23.0};
    vector<double> c (tc, tc+2);

    Simplex simplex(a,b,c);
    
    cout<<-simplex.solve()<<endl;;
}