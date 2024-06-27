#include <iostream>
#include <random>
#include <chrono>
using namespace std;

template<typename T>
struct Node{
    T data;
    Node<T>* next;

    Node(){
        data = 0;
        next = NULL;
    }

    Node(Node &n){
        data = n.data;
        next = n.next;
    }
};

template<typename T>
class BagLL{
    private:
        Node<T>* first = NULL;
        int numElements;

    public:
        BagLL():numElements(0){
        }
        bool isEmpty(){ return first==NULL;}

        int size(){ return numElements;}

        void insert(T item){
            Node<T>* newNode = new Node<T>();
            newNode->data = item;
  
            // Assign to head
            if (first == NULL) {
                first = newNode;
                numElements++;
                return;
            }
            // Traverse till end of list
            Node<T>* temp = first;
            while (temp->next != NULL) {
                // Update temp
                temp = temp->next;
            }
        
            // Insert at the last.
            temp->next = newNode;
            numElements++;
        }

        T dequeue(){
            if(isEmpty()){ return NULL;}
            random_device rd;  // Will be used to obtain a seed for the random number engine
            mt19937 gen(rd()); // Standard mersenne_twister_engine seeded with rd()
            uniform_int_distribution<int> dis(0,numElements-1);
            int index = dis(gen);
            // cout<<numElements<<"index"<<index<<endl;

            if(index == 0){
                T item = first->data;
                first = first->next;
                numElements--;
                // cout<<"popp"<<item<<endl;
                return item;
            }

            Node<T>* current = first;
            for(int i = 0; i <= index-2; i++){
                current = current->next;
            }
            T item = current->next->data;
            current->next = current->next->next;
            numElements--;
            // cout<<"pop"<<item<<endl;
            return item;
        }   

        T sample(){
            if(isEmpty()){ return NULL;}
            random_device rd;  // Will be used to obtain a seed for the random number engine
            mt19937 gen(rd()); // Standard mersenne_twister_engine seeded with rd()
            uniform_int_distribution<int> dis(0,numElements-1);
            int index = dis(gen);
            // cout<<numElements<<"index"<<index<<endl;

            Node<T>* current = first;
            for(int i = 0; i <= index-1; i++){
                current = current->next;
            }
           
            return current-> data;

        } 

        void print(){
            if(isEmpty()) return;
            Node<T>* temp = first;
            while (temp != NULL) {
                // Update temp
                cout<<temp->data<<" ";
                temp = temp->next;
            }
            cout<<endl;
        }
};

template<typename T>
class BagArr{
    private:
        int numElements, arrSize;
        T* array;
        
    public:
        BagArr():numElements(0), arrSize(1){
            array = new T[arrSize];
        }
        bool isEmpty(){ return numElements ==0;}

        void resize(int newSize){
            T* newArr = new T[newSize];
            for(int i = 0; i < numElements; i++){
                newArr[i] = array[i];
            }
            
            delete [] array;
            array = newArr;
            arrSize = newSize;
        }

        void insert(T item){
            if(numElements == arrSize){
                //full
                resize(2*arrSize);
            }
            array[numElements] = item;
            numElements++;
        }

        T dequeue(){
            if(numElements==0) return 0;
            random_device rd;  // Will be used to obtain a seed for the random number engine
            mt19937 gen(rd()); // Standard mersenne_twister_engine seeded with rd()
            uniform_int_distribution<int> dis(0,numElements-1);
            int index = dis(gen);
            T item = array[index];
            for(int i = index; i < numElements-1; i++){
                array[i] = array[i+1];
            }
            numElements--;
            if(numElements <= arrSize/4){
                resize(numElements);
            }
            return item;

        }   

        vector<T> elems(){
            vector<T> v;
            for(int i = 0; i < numElements; i++){
                v.push_back(array[i]);
            }
            return v;
            // cout<<"numElements: "<<numElements<<" ArraySize: "<<arrSize<<endl;
        }
};