#include <iostream>
#include <vector>
#include <chrono>
#include <random>
using namespace std;

template <typename T>
class QuickSort
{
    void swap(vector<T> &a, int i, int j)
    {
        int first = a[i];
        a[i] = a[j];
        a[j] = first;
    }

    int partition(vector<T> &a, int lo, int hi)
    {
        int i = lo, j = hi + 1;

        while (true)
        {
            while (a[++i] < a[lo])
                if (i == hi)
                    break;

            while (a[lo] < a[--j])
                if (j == lo)
                    break;

            if (i >= j)
                break;

            swap(a, i, j);
        }

        swap(a, lo, j);
        return j;
    }

    int median3(vector<T> &a, int x, int y, int z)
    {
        int i = a[x], j = a[y], k = a[z];
        if ((i <= j && j <= k) || (k <= j && j <= i))
            return x;
        else if ((j <= i && i <= k) || (k <= i && i <= j))
            return y;
        else
            return z;
    }

    void sort(vector<T> &a, int lo, int hi)
    {
        if (hi <= lo)
            return;

        int mi = median3(a, lo, (lo + hi) / 2, hi);
        swap(a, lo, mi);

        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

public:
    void sort(vector<T> &a)
    {
        mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());

        shuffle(a.begin(), a.end(), rng);
        sort(a, 0, a.size() - 1);
    }
};

template <typename T>
class ThreeWayQuickSort
{
    void swap(vector<T> &a, int i, int j)
    {
        int first = a[i];
        a[i] = a[j];
        a[j] = first;
    }

    void sort(vector<T> &a, int lo, int hi)
    {
        if (hi <= lo)
            return;

        // lt: lower elements, gt: greater elements
        int lt = lo, gt = hi;

        // partition
        T v = a[lo];

        int i = lo;
        // i is the current element, after equal keys
        while (i <= gt)
        {
            if (a[i] < v)
                swap(a, lt++, i++);
            else if (a[i] > v)
                swap(a, i, gt--);
            else
                i++;
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

public:
    void sort(vector<T> &a)
    {
        mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());

        shuffle(a.begin(), a.end(), rng);
        sort(a, 0, a.size() - 1);
    }
};