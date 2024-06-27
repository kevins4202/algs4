#include <iostream>
#include <string>
#include "SubstringSearch.h"

using namespace std;

int main(){
    string all = "SEARCHINGINAHAYSTACKFORANEEDLEINA";

    KnuthMorrisPratt KMP (all);

    cout<<KMP.search("BABAC")<<endl;

    BoyerMoore BM (all);
    cout<<BM.search("NEEDLE")<<endl;

    RabinKarp RK ("26535");
    cout<<RK.search("3141592653589793")<<endl;
}