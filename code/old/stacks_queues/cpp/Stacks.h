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
class StackLL{
    private:
        Node<T>* first = NULL;
        int numElements;

    public:
        StackLL():numElements(0){
        }
        bool isEmpty(){ return first==NULL;}


        void insert(T item){
            Node<T>* oldfirst = first;

            first = new Node<T>();
            first->data = item;
            first->next = oldfirst;
  
            // Assign to head
            if (first == NULL) {
                first->data = item;
                numElements++;
                return;
            }
            // Traverse till end of list
            Node<T>* old = first;
            
            numElements++;
        }

        T dequeue(){
            if(isEmpty()){ return NULL;}
            
            T item = first->data;
            numElements--;
            first = first->next;
            return item;

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
class StackArr{
    private:
        int numElements, arrSize;
        T* array;
        
    public:
        StackArr():numElements(0), arrSize(1){
            array = new T[arrSize];
        }
        bool isEmpty(){ return numElements ==0;}

        void resize(int newSize){
            int* newArr = new int[newSize];
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
            T item = array[numElements-1];
            array[numElements-1] = NULL;
            numElements--;
            if(numElements <= arrSize/4){
                resize(numElements);
            }
            return item;
        }   

        void print(){
            for(int i = 0; i < numElements; i++){
                cout<<array[i]<<" ";
            }
            cout<<endl;
            // cout<<"numElements: "<<numElements<<" ArraySize: "<<arrSize<<endl;
        }

        int size(){ return arrSize;}
};