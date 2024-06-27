#include <iostream>
#define RED true
#define BLACK false
// #include "PointSet.h"
using namespace std;

template<typename T, typename U>
struct Node{
    T key;
    U val;
    Node<T, U> * left;
    Node<T, U> *right;

    int count;//, height;
    bool color;

    Node(T k, U v, bool r, int h): key(k), val(v), left(nullptr), right(nullptr), count(1), color(r) {}

    Node(Node<T, U> &x){
        key = x.key;
        val = x.val;
        left = x.left;
        right = x.right;
        count = x.count;
        color = x.color;
        // height = x.height;
    }

    Node(){
        key = NULL;
        val = NULL;
        left = nullptr;
        right = nullptr;
        count = 1;
        color = RED;
        // height = 1;
    }
};
template<typename T, typename U>
bool isRed(Node<T, U>* x){
    if(x ==nullptr) return false;
    return x-> color == RED;
}



//left-leaning red black tree
// identical to elementary BST, red links are glue
template<typename T, typename U>
class RBBST{
    Node<T, U> *root= nullptr;

    public:
        Node<T, U>* rotateLeft(Node<T, U>* h){
            //h.right must be red
            Node<T, U>* x = h-> right;
            h->right = x->left;
            x-> left = h;
            x-> color = h-> color;
            h-> color = RED;
            return x;
        }

        Node<T, U>* rotateRight(Node<T, U>* h){
            //h.right must be red
            Node<T, U>* x = h-> left;
            h->left = x->right;
            x-> right = h;
            x-> color = h-> color;
            h-> color = RED;
            return x;
        }

        void flipColors(Node<T, U>* h){
            //h is black
            //h.right is red
            // h.left is red

            h->color = RED;
            h->left->color = BLACK;
            h->right->color = BLACK;
        }

        void put(T key, U val){
            root = put(root, key, val);
            root-> color = BLACK;
        }
        
        Node<T, U> * put(Node<T, U>* h, T key, U val){
            if(h ==nullptr) return new Node<T, U>(key, val, RED);

            if(key < h-> key) h->left = put(h-> left, key, val);
            else if(key > h-> key) h->right = put(h-> right, key, val);
            else h-> val = val;

            //little code, perfect balance
            if(isRed(h->right) && !isRed(h->left)) h = rotateLeft(h); //lean left
            if(isRed(h->left) && isRed(h->left->left)) h = rotateRight(h);//balance 4-node
            if(isRed(h->left) && isRed(h->right)) flipColors(h);//split 4-node

            return h;
        }

        //same as elementary
        U get(T key){
            Node<T, U> x = root;

            while(x != nullptr){
                if(key < x-> key){
                    x = x-> left;
                } else if(key > x->key){
                    x = x-> right;
                } else return x-> val;
            }

            return (U) NULL;
        }

        void remove(T key){
            root = remove(root, key);
        }

        //Hibbard deletion
        Node<T, U>* remove(Node<T, U>* x, T key){
            if(x == nullptr) return nullptr;

            if(key < x->key) x->left = remove(x->left, key);
            else if(key > x->key) x->right = remove(x->right, key);
            else{
                if(x->right == nullptr) return x->left;
                if(x->left == nullptr) return x->right;

                Node<T, U>* t = x;
                x = min(t->right);
                x->right = removeMin(t->right);
                x->left = t->left;
            }

            x->count = sz(x->left) + sz(x->right) + 1;
            return x;
        }

        bool contains(T key){
            return get(key) != NULL;
        }

        bool isEmpty(){
            return root == nullptr;
        }

        int totalSize(){
            return sz(root);
        }

        int sz(Node<T, U>* x){
            if(x==nullptr) return 0;
            return x->count;
        }

        T min(){
            return min(root)->key;
        }

        Node<T, U>* min(Node<T, U>* x){
            if(x->left == nullptr) return x;
            return min(x->left);
        }

        T max(){
            return max(root)->key;
        }

        Node<T, U>* max(Node<T, U>* x){
            if(x->right == nullptr) return x;
            return max(x->right);
        }

        // largest key <= given key
        T floor(T key){
            Node<T, U>* x = floor(root, key);
            if(x == nullptr) return NULL;
            return x->key;
        }

        Node<T, U>* floor(Node<T, U>* x, T key){
            if(x == nullptr) return nullptr;

            if(key == x->key) return x;
            if(key < x->key) return floor(x->left, key);

            Node<T, U>* t = floor(x->right, key);
            if(t != nullptr) return t;
            else return x;
        }

        // smallest key >= given key
        T ceiling(T key){
            Node<T, U>* x = ceiling(root, key);
            if(x == nullptr) return NULL;
            return x->key;
        }

        Node<T, U>* ceiling(Node<T, U>* x, T key){
            if(x == nullptr) return NULL;

            if(key == x->key) return x;
            if(key > x->key) return ceiling(x->right, key);

            Node<T, U>* t = ceiling(x->left, key);
            if(t != nullptr) return t;
            else return x;
        }

        // number of keys < given key
        int rank(T key){
            return rank(key, root);
        }

        int rank(T key, Node<T, U>* x){
            if(x == nullptr) return 0;

            if(key < x->key) return rank(key, x->left);
            else if(key > x->key) return 1 + sz(x->left) + rank(key, x->right);
            else return sz(x->left);
        }

        // key of rank k
        T select(int k){
            return select(root, k)->key;
        }

        Node<T, U>* select(Node<T, U>* x, int k){
            if(k < 0 || k >= sz(x)) return nullptr;
            if(x == nullptr) return NULL;

            int t = sz(x->left);
            if(t > k) return select(x->left, k);
            else if(t < k) return select(x->right, k-t-1);
            else return x;
        }

        void removeMin(){
            root = removeMin(root);
        }  

        Node<T, U>* removeMin(Node<T, U>* x){
            if(x->left == nullptr) return x->right;
            x->left = removeMin(x->left);
            x->count = sz(x->left) + sz(x->right) + 1;
            return x;
        }

        void removeMax(){
            root = removeMax(root);
        }

        Node<T, U>* removeMax(Node<T, U>* x){
            if(x->right==nullptr) return x-> left;

            x->right = removeMax(x->right);

            x->count = sz(x->left) + sz(x->right) + 1;
            return x;
        }

        void inOrder(){
            inOrder(root);
        }

        void inOrder(Node<T, U>* x){
            if(x==nullptr) return;

            inOrder(x->left);
            cout<<x->val<<endl;
            // cout<<"right"<<endl;
            inOrder(x->right);
        }
};