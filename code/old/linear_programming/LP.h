#include </Users/kevins/stdc++.h>

using namespace std;

class Simplex{
    private:
        vector<vector<double> > a;
        int m, n;

        int bland(){
            for(int q = 0; q < m+n; q++)
                if(a[m][q] > 0) return q;//look for objective function column

            return -1;//optimal
        }

        int minRatioRule(int q){
            //best row
            int p = -1;

            for(int i = 0; i < m; i++){
                if(a[i][q] <= 0) continue;
                else if (p==-1) p = i;
                else if(a[i][m+n]/a[i][q] < a[p][m+n]/a[p][q]) p = i;
            }

            return p;
        }

    public: 
        Simplex(vector<vector<double> > A, vector<double> b, vector<double> c){
            m = b.size();
            n = c.size();
            a = vector<vector<double> > (m + 1, vector<double> (m + n + 1));

            for(int i = 0; i < m; i++)
                for(int j = 0; j < n; j++)
                    a[i][j] = A[i][j];
            
            for(int j = n; j < m+n; j++) a[j-n][j] = 1.0; //set up matrix
            for(int j = 0; j < n; j++) a[m][j] = c[j]; // objective function
            for(int i = 0; i < m; i++) a[i][m+n] = b[i]; // totals
        }

        void pivot(int p, int q){
            for(int i = 0; i <=m; i++)
                for(int j = 0; j <=m+n; j++)
                    if(i != p && j != q)
                        a[i][j] -= a[p][j] * a[i][q] / a[p][q]; //scale all other entries

            for(int i = 0; i <=m; i++)
                if(i != p) a[i][q] = 0.0;//zero out column q

            //scale row p
            for(int j = 0; j <= m+n; j++)
                if(j != q) a[p][j] /= a[p][q];
            
            a[p][q] = 1.0;
        }

        double solve(){
            while(true){
                int q = bland();
                if(q==-1) break;

                int p = minRatioRule(q);
                // row p unbounded if -1

                pivot(p,q);
            }

            return a[m][m+n];
        }
};