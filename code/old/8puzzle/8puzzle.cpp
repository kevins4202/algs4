#include <iostream>
#include <cmath>
#include "8puzzle.h"

using namespace std;

int main(){
    vector<vector<int> > tiles = {{8,6,7},{2,5,4},{3,0,1}};
    Board board(tiles);
    Solver solve(board);

    // for(auto b : board.neighbors()){
    //     b.printBoard();
    //     cout<<b.isGoal()<<endl;;
    // }

    cout<<solve.moves()<<endl<<solve.isSolvable()<<endl<<endl;
    for(auto b : solve.solution()) b.printBoard();
    return 0;
}