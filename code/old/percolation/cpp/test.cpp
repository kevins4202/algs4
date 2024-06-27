#include <iostream>
#include <vector>
using namespace std;

int n, x[200], y[200], p[200];

vector<bool> visited;
vector<int> adj[200];

void dfs(int start){
	cout<<"visit "<<start<<endl;
	visited[start] = true;
	for(int j = 0; j < n; j++){
		cout<<visited[j]<<" ";
	}
	cout<<endl;
	for(int i : adj[start]){
		if(!visited[i]){
			dfs(i);
		}
	}
}

int main() {
	cin>>n;
	
	for(int i = 0; i < n; i++){
		cin>>x[i]>>y[i]>>p[i];
	}
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			if((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]) <= p[i] * p[i]){
				//i can broadcast to j
				adj[i].push_back(j);
				cout<<i<<" "<<j<<endl;
			}
		}
	}
	
	int count = 0;
	
	for(int i = 0; i < n; i++){
		visited = vector<bool> (n, false);
		for(int j = 0; j < n; j++){
			cout<<visited[i]<<" ";
		}
		cout<<"start "<<i<<endl;
		dfs(i);
		cout<<"finish dfs"<<endl;
		for(int j = 0; j < n; j++){
			cout<<visited[i]<<" ";
		}
		cout<<endl;
		int temp = 0;
		for(int j = 0; j < n; j++){
			temp += visited[i];
			cout<<visited[i]<<" ";
		}
		cout<<endl;
		count = max(count, temp);
	}
	
	cout<<endl;
	
	for(int i = 0; i < n; i++){
		for(int j : adj[i]){
			cout<<j<<" ";
		}
		
		cout<<endl;
	}
	
	
	cout<<count<<endl;
}