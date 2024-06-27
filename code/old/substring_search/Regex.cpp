#include "Regex.h"
#include <iostream>

int main(){
    Regex re("((A*B|AC)D)");

    cout<<re.check("BD")<<endl;
}