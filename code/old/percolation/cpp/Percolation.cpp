#include <iostream>
#include <chrono>
#include <random>
#include "Percolation.h"

using namespace std;

int main()
{
    freopen("perc.in", "r", stdin);
    int T, N;
    cin >> T >> N;

    srand(chrono::steady_clock::now().time_since_epoch().count());

    PercolationStats ps(N, T);

    for (int i = 0; i < T; i++)
    {
        Percolation p(N);

        while (!p.percolates())
        {
            // cout << endl;
            // for (int i = 0; i < N; i++)
            // {
            //     for (int j = 0; j < N; j++)
            //     {
            //         cout << p.id[p.toIndex(make_pair(i, j))] << " ";
            //     }
            //     cout << endl;
            // }
            // cout << endl;

            // for (int i = 0; i < N; i++)
            // {
            //     for (int j = 0; j < N; j++)
            //     {
            //         cout << p.opened[p.toIndex(make_pair(i, j))] << " ";
            //     }
            //     cout << endl;
            // }

            // cout << endl
            //      << p.numOpenSites() << endl;
            int rand_node;
            do
            {
                rand_node = (int)((double)rand() / (RAND_MAX)*N * N);
            } while (p.isOpen(rand_node));

            p.open(rand_node);

            // cout << rand_node<<endl;
            
        }

        ps.addTrial(p.numOpenSites());

        // cout << endl;
        // for (int i = 0; i < N; i++)
        // {
        //     for (int j = 0; j < N; j++)
        //     {
        //         cout << p.id[p.toIndex(make_pair(i, j))] << " ";
        //     }
        //     cout << endl;
        // }
        // cout << endl;

        // for (int i = 0; i < N; i++)
        // {
        //     for (int j = 0; j < N; j++)
        //     {
        //         cout << p.opened[p.toIndex(make_pair(i, j))] << " ";
        //     }
        //     cout << endl;
        // }

        // cout << endl
        //      << p.numOpenSites() << endl;
    }

    // for(int i : ps.openSites) cout<<i<<" ";

    cout << endl;

    cout << "mean = " << ps.mean() << endl;
    cout << "stddev = " << ps.stddev() << endl;
    cout << "95% confidence interval = [" << ps.confidenceLo() << ", " << ps.confidenceHi() << "]" << endl;
}