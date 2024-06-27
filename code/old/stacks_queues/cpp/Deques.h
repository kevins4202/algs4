#include <iostream>
#include <random>
using namespace std;

template<typename T>
struct Node{
    T data;
    Node<T>* next;
    Node<T>* prev;

    Node(){
        data = 0;
        prev = NULL;
        next = NULL;
    }

    Node(Node &n){
        data = n.data;
        prev = n.prev;
        next = n.next;
    }
};

template<typename T>
class DequeLL{
    private:
        Node<T>* first = NULL;
        Node<T>* last = NULL;
        int numElements;

    public:
        DequeLL():numElements(0){
        }
        bool isEmpty(){ return numElements==0;}

        int size(){ return numElements;}

        void addFirst(T item){
            Node<T>* oldfirst = first;

            first = new Node<T>();
            first->data = item;
            if(isEmpty()){
                last = first;
            } 
            else{
                first->next = oldfirst;
                oldfirst->prev = first;
            }
            
            numElements++;
        }

        void addLast(T item){
            Node<T>* oldlast = last;

            last = new Node<T>();
            last->data = item;
  
            // Assign to head
            if (isEmpty()) {
                first = last;
            } else{
                oldlast->next = last;
                last-> prev = oldlast;
            }
            numElements++;
        }

        T removeFirst(){
            if(isEmpty()){ return NULL;}
            
            T item = first->data;

            first = first->next;
            if(isEmpty()) last = NULL;
            numElements--;
            return item;

        }   

        T removeLast(){
            if(isEmpty()){ return NULL;}
            
            T item = last->data;

            last = last->prev;
            last-> next = NULL;

            if(isEmpty()) first = NULL;
            numElements--;
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
class DequeArr{
    private:
        int numElements, arrSize, first, last;
        T* array;
        
    public:
        DequeArr():numElements(0), arrSize(1), first(0), last(0){
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