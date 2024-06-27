#include <iostream>
#include <vector>
#include <cmath>
#include <random>
#include <chrono>
#include "PQ.h"

using namespace std;

class Board
{
public:
    vector<vector<int> > board;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    Board(vector<vector<int>> tiles)
    {
        board.resize(tiles.size(), vector<int>(tiles[0].size(), 0));

        for (int i = 0; i < tiles.size(); i++)
        {
            for (int j = 0; j < tiles[0].size(); j++)
            {
                board[i][j] = tiles[i][j];
            }
        }
    }

    Board(int sz)
    {
        board.resize(sz, vector<int>(sz, 0));
    }

    Board() {}

    void swap(int i1, int j1, int i2, int j2)
    {
        int temp = board[i1][j1];
        board[i1][j1] = board[i2][j2];
        board[i2][j2] = temp;
    }

    // board dimension n
    int dimension() { return board.size(); }

    // number of tiles out of place
    int hamming()
    {
        int dist = 0;
        for (int i = 0; i < board.size(); i++)
        {
            for (int j = 0; j < board[0].size(); j++)
            {
                if (board[i][j] == 0)
                    continue;

                dist += ((i * board.size() + j) != board[i][j] - 1);
            }
        }

        return dist;
    }

    // sum of Manhattan distances between tiles and goal
    int manhattan()
    {
        int dist = 0;
        for (int i = 0; i < board.size(); i++)
        {
            for (int j = 0; j < board[0].size(); j++)
            {
                if (board[i][j] == 0)
                    continue;

                dist += abs((int)(((board[i][j] - 1) / board.size()) - i)) + abs((int)(((board[i][j] - 1) % board.size()) - j));
            }
        }

        return dist;
    }

    // is this board the goal board?
    bool isGoal()
    {
        return hamming() == 0;
    }

    // does this board equal y?
    bool equals(Board y)
    {
        if (y.dimension() != this->dimension())
            return false;

        for (int i = 0; i < board.size(); i++)
        {
            for (int j = 0; j < board[0].size(); j++)
            {
                if (y.board[i][j] != this->board[i][j])
                    return false;
            }
        }

        return true;
    }

    // all neighboring boards
    vector<Board>* neighbors()
    {
        for (int i = 0; i < board.size(); i++)
        {
            for (int j = 0; j < board[0].size(); j++)
            {
                if (board[i][j] == 0)
                {
                    vector<Board>* neighbors = new vector<Board>();
                    
                    if (i > 0)
                    {

                        // top row
                        Board neighbor(board);
                        neighbor.swap(i, j, i - 1, j);
                        neighbors->push_back(neighbor);
                    }

                    if (i < board.size() - 1)
                    {
                        // bottom row
                        Board neighbor(board);
                        neighbor.swap(i, j, i + 1, j);
                        neighbors->push_back(neighbor);
                    }

                    if (j > 0)
                    {
                        // left col
                        Board neighbor(board);
                        neighbor.swap(i, j, i, j - 1);
                        neighbors->push_back(neighbor);
                    }

                    if (j < board[0].size() - 1)
                    {
                        // right col
                        Board neighbor(board);
                        neighbor.swap(i, j, i, j + 1);
                        neighbors->push_back(neighbor);
                    }

                    return neighbors;
                }
            }
        }

        return NULL;
    }

    // a board that is obtained by randomly exchanging any pair of tiles
    Board twin()
    {
        random_device rd;  // Will be used to obtain a seed for the random number engine
        mt19937 gen(rd()); // Standard mersenne_twister_engine seeded with rd()
        uniform_int_distribution<int> dis(0, board.size() - 1);
        int i, j;
        do
        {
            i = dis(gen);
            j = dis(gen);
        } while (board[i][j] == 0);

        Board twin(board);
        uniform_int_distribution<int> dis2(0, 4);
        bool done = false;
        do
        {
            int k = dis2(gen);
            switch (k)
            {
            case 0:
                if (i > 0 && board[i - 1][j] != 0)
                {
                    // top row
                    twin.swap(i, j, i - 1, j);
                    done = true;
                }
                break;
            case 1:
                if (i < board.size() - 1 && board[i + 1][j] != 0)
                {
                    // bottom row
                    twin.swap(i, j, i + 1, j);
                    done = true;
                }
                break;
            case 2:
                if (j > 0 && board[i][j - 1] != 0)
                {
                    // left col
                    twin.swap(i, j, i, j - 1);
                    done = true;
                }
                break;
            case 3:
                if (j < board[0].size() - 1 && board[i][j + 1] != 0)
                {
                    // right col
                    twin.swap(i, j, i, j + 1);
                    done = true;
                }
                break;
            default:
                break;
            }
        } while (!done);

        return twin;
    }

    void printBoard(){
        cout<<dimension()<<endl;
        for (int i = 0; i < dimension(); i++)
    {
        for (int j = 0; j < dimension(); j++)
        {
            cout << board[i][j] << " ";
        }
        cout << endl;
    }
    }

};

// ostream &operator<<(ostream &os, Board &board)
// {
//     os << board.dimension()<< endl;
//     board.printBoard();
// }

class SearchNode
{
public:
    Board gameBoard;
    int moves;
    SearchNode *previous;
    int priority;

    SearchNode(Board b, int m, SearchNode* p) : gameBoard(b), moves(m), previous(p), priority(b.manhattan() + m)
    {
    }

    SearchNode() {
        gameBoard = Board(0);
        moves = 0;
        previous = NULL;
        priority = 0;
    }

    // The Hamming priority function is the
    // Hamming distance of a board plus the
    // number of moves made so far to get to the search node.

    // The Manhattan priority function is the Manhattan
    // distance of a board plus the number of moves made
    // so far to get to the search node.
    bool operator>(SearchNode s)
    {
        return this->priority > s.priority;
    }

    void printNode()
    {
        gameBoard.printBoard();
    }
};



class Solver
{
    MinPQ<SearchNode> tree, treeTwin;
    SearchNode lastNode, lastNodeTwin;
    Board start;
    bool solvable = true;

    // find a solution to the initial board (using the A* algorithm)
public:
    Solver(Board initial) : start(initial)
    {
        // insert initial board
        SearchNode init(initial, 0, new SearchNode());
        tree.insert(init);
        
        Board twin(initial.twin());
        // cout<<"hi"<<endl;
        SearchNode tw(twin, 0, new SearchNode());
        treeTwin.insert(tw);

        // treeTwin.printPQ();

        // solve

        do{
            // original
            lastNode = tree.delMin();
            if(lastNode.gameBoard.isGoal()) break;

            vector<Board>* neighborsBoard = lastNode.gameBoard.neighbors();

            while(neighborsBoard->size() > 0){
                if(lastNode.previous == NULL || !(*neighborsBoard)[neighborsBoard->size() - 1].equals(lastNode.previous->gameBoard)) tree.insert(SearchNode((*neighborsBoard)[neighborsBoard->size() - 1], lastNode.moves + 1, new SearchNode(lastNode)));
                neighborsBoard->pop_back();
            }

            //twin
            lastNodeTwin = treeTwin.delMin();
            if(lastNodeTwin.gameBoard.isGoal()) {
                solvable = false;
                break;
            }

            vector<Board>* TwinNeighborsBoard = lastNodeTwin.gameBoard.neighbors();

            while(TwinNeighborsBoard->size() > 0){
                if(lastNodeTwin.previous == NULL || !(*TwinNeighborsBoard)[TwinNeighborsBoard->size() - 1].equals(lastNodeTwin.previous->gameBoard)) treeTwin.insert(SearchNode((*TwinNeighborsBoard)[TwinNeighborsBoard->size() - 1], lastNodeTwin.moves + 1, new SearchNode(lastNodeTwin)));
                TwinNeighborsBoard->pop_back();
            }
        }
        while (!lastNode.gameBoard.isGoal() && !lastNodeTwin.gameBoard.isGoal());

        // lastNode.previous->gameBoard.printBoard();
    }
        
    

    // is the initial board solvable?
    bool isSolvable()
    {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    int moves()
    {
        if (!isSolvable())
            return -1;

        return lastNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    vector<Board> solution()
    {
        if (!isSolvable())
            return {};

        vector<Board> sol;
        SearchNode* curr = &lastNode;

        // curr->gameBoard.printBoard();
        // cout<<endl<<endl<<endl;

        while (curr->gameBoard.dimension() != 0)
        {
            // cout<<"H1"<<endl;
            // curr->gameBoard.printBoard();
            sol.push_back(curr->gameBoard);
            curr = curr->previous;
            if(curr->gameBoard.equals(start)) break;
            // cout<<"H"<<endl;
        }
        sol.push_back(start);
        reverse(sol.begin(), sol.end());
        return sol;
    }
};