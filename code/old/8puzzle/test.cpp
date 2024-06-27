#include <iostream>
#include <vector>

using namespace std;

int main(){
    vector<vector<int> > a;
    a.resize(3, vector<int>(3,1));
    auto b = a;
    a[0][0] = 2;

    for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
            cout<<a[i][j]<<" ";
        }
        cout<<endl;
    }

    for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
            cout<<b[i][j]<<" ";
        }
        cout<<endl;
    }
}