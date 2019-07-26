#include <iostream>
#include <fstream>
#include <cstdlib>
#include <ctime>

using namespace std;

int main(void) {
    int i, j, k, n, rand_var;
    string filename;
    cout << "Insert the file name: " << endl;
    cin >> filename;
    ofstream file;
    file.open(filename + ".txt");
    cout << "How many test cases? " << endl;
    cin >> n;
    srand(time(nullptr));
    for(i = 0; i < n; i++) {
        k = 0;
        while(rand()%2) {
            j++;
        }
        while(rand()%5) {
            k++;
        }
        file << j << " " << k << endl;
    }
    cout << "Test cases created" << endl;
    file.close();
}
