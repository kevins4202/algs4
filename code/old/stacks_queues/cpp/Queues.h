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
class QueueLL{
    private:
        Node<T>* first = NULL;
        Node<T>* last = NULL;
        int numElements;

    public:
        QueueLL():numElements(0){
        }
        bool isEmpty(){ return first==NULL;}


        void insert(T item){
            Node<T>* oldlast = last;

            last = new Node<T>();
            last->data = item;
  
            // Assign to head
            if (isEmpty()) {
                first = last;
            } else{
                oldlast->next = last;
            }
            numElements++;
        }

        T dequeue(){
            if(isEmpty()){ return NULL;}
            
            T item = first->data;

            first = first->next;
            numElements--;
            if(isEmpty()) last = NULL;
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
class QueueArr{
    private:
        int numElements, arrSize, first, last;
        T* array;
        
    public:
        QueueArr():numElements(0), arrSize(1), first(0), last(0){
            array = new T[arrSize];
        }
        bool isEmpty(){ return numElements ==0;}

        void resize(int newSize){
            int* newArr = new int[newSize];
            for(int i = first; i <= last; i++){
                newArr[i - first] = array[i];
                array[i] = NULL;
            }

            first = 0; last = numElements-1;
            
            delete [] array;
            array = newArr;
            arrSize = newSize;
        }

        void insert(T item){
            if(last+1 == arrSize){
                //full
                resize(2*arrSize);
            }
            last = first + numElements;
            array[last] = item;
            
            numElements++;
        }

        T dequeue(){
            if(numElements==0) return 0;
            T item = array[first];
            array[first] = NULL;
            numElements--;
            first++;
            if(numElements <= arrSize/4){
                resize(numElements);
            }
            return item;
        }   

        void print(){
            for(int i = 0; i < arrSize; i++){
                cout<<array[i]<<" ";
            }
            cout<<endl;
            // cout<<"numElements: "<<numElements<<" ArraySize: "<<arrSize<<endl;
        }

        void fl(){ cout<<first<<" "<<last<<endl;}
};