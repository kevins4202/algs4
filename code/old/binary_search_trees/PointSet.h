#include <iostream>
#include <algorithm>
// #include "RBBST.h"
#include <cmath>
#include <climits>
#include <vector>
#define RED true
#define BLACK false

using namespace std;

struct Point2D
{
    // friend ostream& operator<< (std::ostream& stream, const Point2D& p) {
    //     cout<<"("<<p.x<<" "<<p.y<<")";
    // }
    double x, y;
    Point2D(double x, double y)
    {
        this->x = x, this->y = y;
    }

    Point2D()
    {
        x = NULL, y = NULL;
    }

    double getx() { return x; }

    double gety() { return y; }

    double distanceTo(Point2D that)
    {
        return sqrt(distanceSquaredTo(that));
    }

    double distanceSquaredTo(Point2D that)
    {
        return (x - that.x) * (x - that.x) + (y - that.y) * (y - that.y);
    }

    int cmp(Point2D that, int h){
        if(h%2==1){
            //vertical line, compare x

            if(x == that.x){
                return y - that.y;
            } else return x - that.x;
        } else{
            if(y == that.y){
                return x- that.x;
            } else return y - that.y;
        }
    }

    int comparedTo(Point2D that)
    {
        if (y == that.y)
            return x - that.x;
        else
            return y - that.y;
    }

    // void print()
    // {
    //     std::cout << "(" << x << ", " << y << ")" << endl;
    // }


    bool operator<(Point2D that)
    {
        return comparedTo(that) < 0;
    }

    bool operator>(Point2D that)
    {
        return comparedTo(that) > 0;
    }

    bool operator==(Point2D that)
    {
        return x == that.x && y == that.y;
    }

    void toString()
    {
        std::cout << to_string(x) + " " + to_string(y) << endl;
    }
};

struct RectHV
{
    double mx, Mx, my, My;
    RectHV(double xmin, double ymin, double xmax, double ymax) : mx(xmin), Mx(xmax), my(ymin), My(ymax) {}

    RectHV():mx(NULL), Mx(NULL), my(NULL), My(NULL){}

    double xmin() { return mx; }

    double ymin() { return my; }

    double xmax() { return Mx; }

    double ymax() { return My; }

    bool contains(Point2D p) { return p.x <= Mx && p.x >= mx && p.y <= My && p.y >= my; }

    bool intersects(RectHV that)
    {
        if (mx > that.Mx || Mx < that.mx || my > that.My || My < that.my)
            return false;
        return true;
    }

    double distanceTo(Point2D p)
    {
        return sqrt(distanceSquaredTo(p));       
    }

    double distanceSquaredTo(Point2D p)
    {
        double dx = 0.0, dy = 0.0;

        if(p.x < mx) dx = p.x - mx;
        else if(p.x > Mx) dx = p.x - Mx;

        if(p.y < my) dy = p.y - my;
        else if(p.y > My) dy = p.y - My;
        

        return dx*dx + dy*dy;
    }

    bool operator==(RectHV that) { return mx == that.mx && My == that.My && my == that.my && My == that.My; }

    void toString()
    {
        std::cout << "(" << mx << ", " << my << "), (" << Mx << ", " << My << ")" << endl;
    }
};

struct Node{
    Point2D p;
    RectHV rect;

    Node *left;
    Node *right;

    int count;
    bool color;

    Node(Point2D q, RectHV bounds, bool r): p(q), rect(bounds), left(nullptr), right(nullptr), count(1), color(r){}

    Node(Node &x){
        p = x.p;
        rect = x.rect;
        left = x.left;
        right = x.right;
        count = x.count;
        color = x.color;
        // height = x.height;
    }

    Node(){
        p = Point2D();
        rect = RectHV();
        left = nullptr;
        right = nullptr;
        count = 1;
        color = RED;
        // height = 1;
    }
};

bool isRed(Node* x){
    if(x ==nullptr) return false;
    return x-> color == RED;
}

class KDTree{
    Node *root= nullptr;
    vector<Point2D> pts;

    int minx = 0, miny = 0, maxx = 1, maxy = 1;

    public:
        Node* rotateLeft(Node* h){
            //h.right must be red
            Node* x = h-> right;
            h->right = x->left;
            x-> left = h;
            x-> color = h-> color;
            h-> color = RED;
            return x;
        }

        Node* rotateRight(Node* h){
            //h.right must be red
            Node* x = h-> left;
            h->left = x->right;
            x-> right = h;
            x-> color = h-> color;
            h-> color = RED;
            return x;
        }

        void flipColors(Node* h){
            //h is black
            //h.right is red
            // h.left is red

            h->color = RED;
            h->left->color = BLACK;
            h->right->color = BLACK;
        }

        void put(Point2D p){
            root = put(root,p, 1, minx, miny, maxx, maxy);
            root-> color = BLACK;
        }
        
        Node * put(Node* h, Point2D p, int height, int xmin, int ymin, int xmax, int ymax){
            RectHV x(xmin, ymin, xmax, ymax);
            if(h ==nullptr) {
                cout<<"new node"<<endl;
                pts.push_back(p);
                return new Node(p, x ,RED);
            }
            // cout<<"start"<<endl;
            if(p.cmp(h->p, height)<0){
                //odd level, compare x
                if(height%2==1) h->left = put(h->left, p, height+1, xmin, ymin, h->p.x, ymax);
                else h->left = put(h->left, p, height+1, xmin, ymin, xmax, h->p.y);
            }
            else if(p.cmp(h->p, height) >0){
                if(height%2==1){
                    h->right = put(h->right, p, height+1, h->p.x, ymin, xmax, ymax);
                } else{
                    h->right = put(h->right, p, height+1, xmin, h->p.y, xmax, ymax);
                }
            }
            else {
                h-> p = p;
                pts.erase(find(pts.begin(), pts.end(), h->p));
                pts.push_back(p);
            }

            //little code, perfect balance
            if(isRed(h->right) && !isRed(h->left)) h = rotateLeft(h); //lean left
            if(isRed(h->left) && isRed(h->left->left)) h = rotateRight(h);//balance 4-node
            if(isRed(h->left) && isRed(h->right)) flipColors(h);//split 4-node

            return h;
        }

        //same as elementary
        int get(Point2D key, int h){
            Node* x = root;

            while(x != nullptr){
                if(key.cmp(x->p, h)<0) {
                    
                    x = x-> left;
                }
                else if(key.cmp(x->p, h)>0) {
                    x = x-> right;
                    cout<<"right"<<endl;
                }
                else return x-> p.x;

                h++;
            }            

            return NULL;
        }

        void remove(Point2D key){
            root = remove(root, key, 1);
        }

        //deletion from red-black bst
        Node* remove(Node* x, Point2D key, int height){
            if(x == nullptr) return nullptr;

            if(x->left==nullptr && x->right==nullptr && key.cmp(x->p, height) == 0) {
                cout<<"hi"<<endl;
                Node*tmp = new Node(*(x));
                delete x;
                pts.erase(find(pts.begin(), pts.end(), (tmp->p)));  
                return tmp;
            }

            if(key.cmp(x->p, height)<0) x->left = remove(x->left, key, height+1);
            else if(key.cmp(x->p, height)>0) x->right = remove(x->right, key, height+1);
            else{
                
                if(x->right == nullptr) {
                    cout<<"found1"<<endl;
                    Node*tmp = new Node(*(x));
                    delete x;
                    pts.erase(find(pts.begin(), pts.end(), (tmp->p)));  
                    return x->left;
                }
                if(x->left == nullptr) {
                    cout<<"found2"<<endl;
                    Node*tmp = new Node(*(x->right));
                    delete x->right;
                    pts.erase(find(pts.begin(), pts.end(), (x->p)));  
                    return tmp;
                }
                //both exist
                Node* t = x;
                x = min(t->right);

                cout<<x->p.x<<" "<<x->p.y<<endl;
                t = new Node(*(x));
                pts.erase(find(pts.begin(), pts.end(), (x->p)));  
                delete x;

                return t;
            }
        }

        bool contains(Point2D p){
            // cout<<NULL<<endl;
            return get(p, 1) !=  NULL;
        }

        bool isEmpty(){
            return root == nullptr;
        }

        int totalSize(){
            return sz(root);
        }

        int sz(Node* x){
            if(x==nullptr) return 0;
            return x->count;
        }

        Point2D min(){
            return min(root)->p;
        }

        Node* min(Node* x){
            if(x->left == nullptr) return x;
            return min(x->left);
        }

        Point2D max(){
            return max(root)->p;
        }

        Node* max(Node* x){
            if(x->right == nullptr) return x;
            return max(x->right);
        }

        // largest key <= given key
        Point2D floor(Point2D key){
            Node* x = floor(root, key, 1);
            if(x == nullptr) return Point2D();
            return x->p;
        }

        Node* floor(Node* x, Point2D key, int height){
            if(x == nullptr) return nullptr;

            if(key.cmp(x->p, height)<0) return x;
            if(key.cmp(x->p, height)>0) return floor(x->left, key, height+1);

            Node* t = floor(x->right, key, height+1);
            if(t != nullptr) return t;
            else return x;
        }

        // smallest key >= given key
        Point2D ceiling(Point2D key){
            Node* x = ceiling(root, key, 1);
            if(x == nullptr) return Point2D();
            return x->p;
        }

        Node* ceiling(Node* x, Point2D p, int height){
            if(x == nullptr) return NULL;

            if(p.cmp(x->p, height)<0) return x;
            if(p.cmp(x->p, height) >0) return ceiling(x->right, p, height+1);

            Node* t = ceiling(x->left, p, height+1);
            if(t != nullptr) return t;
            else return x;
        }

        // number of keys < given key
        int rank(Point2D key){
            return rank(key, root, 1);
        }

        int rank(Point2D p, Node* x, int h){
            if(x == nullptr) return 0;

            if(p.cmp(x->p, h)<0) return rank(p, x->left, h+1);
            else if(p.cmp(x->p, h)>0) return 1 + sz(x->left) + rank(p, x->right, h+1);
            else return sz(x->left);
        }

        // key of rank k
        Point2D select(int k){
            return select(root, k, 1)->p;
        }

        Node* select(Node* x, int k, int h){
            if(k < 0 || k >= sz(x)) return nullptr;
            if(x == nullptr) return NULL;

            int t = sz(x->left);
            if(t > k) return select(x->left, k, h+1);
            else if(t < k) return select(x->right, k-t-1, h+1);
            else return x;
        }

        void removeMin(){
            root = removeMin(root, 1);
        }  

        Node* removeMin(Node* x, int h){
            if(x->left == nullptr) {
                
                Node*tmp = new Node(*(x->right));
                delete x->right;
                pts.erase(find(pts.begin(), pts.end(), (x->right->p)));  
                return tmp;
            }
            x->left = removeMin(x->left, h+1);
            x->count = sz(x->left) + sz(x->right) + 1;

            Node*tmp = new Node(*x);
            delete x;
            pts.erase(find(pts.begin(), pts.end(), (x->p))); 
            return tmp;
        }

        void removeMax(){
            root = removeMax(root);
        }

        Node* removeMax(Node* x){
            if(x->right==nullptr) {
                Node*tmp = new Node(*(x->left));
                delete x->left;
                pts.erase(find(pts.begin(), pts.end(), (x->p)));               
                return x-> left;
            }

            x->right = removeMax(x->right);

            x->count = sz(x->left) + sz(x->right) + 1;
            Node*tmp = new Node(*(x));
            delete x;
            pts.erase(find(pts.begin(), pts.end(), (x->p)));   
            return x;
        }

        vector<Point2D> inOrder(){
            vector<Point2D>* ret;
            return inOrder(root, ret);
        }

        vector<Point2D> inOrder(Node* x, vector<Point2D>* v){
            if (x == nullptr) return *v;
            cout<<x->p.x<<" "<<x->p.y<<endl;

            auto left = inOrder(x->left, v);

            left.push_back(x->p);

            return inOrder(x->right, &left);
        }

        vector<Point2D> range(RectHV rect) // all points that are inside the rectangle (or on the boundary) 
        {
            vector<Point2D>* curr_pts = new vector<Point2D>();

            return *range(rect, root, curr_pts);
        }

        vector<Point2D>* range(RectHV rect, Node* node, vector<Point2D>* curr_pts){
            if(node == nullptr) {
                return new vector<Point2D>();
            }

            if(rect.contains(node->p)) curr_pts->push_back(node->p);

            if(node->left != nullptr && rect.intersects(node->left->rect)) range(rect, node->left, curr_pts);
            if(node->right != nullptr && rect.intersects(node->right->rect)) range(rect, node->right, curr_pts);

            return curr_pts;
        }

        Point2D nearest(Point2D p)// a nearest neighbor in the set to point p; null if the set is empty 
        {
            Point2D closest = root->p;
            
            Point2D left = nearest(root->left, p, closest);
            Point2D right = nearest(root->right, p, closest);

            if(left.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) closest = left;
            if(right.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) closest = right;

            return closest;
        }

        Point2D nearest(Node* node, Point2D p, Point2D currClosest){
            if(node == nullptr) return currClosest;

            if(node->rect.distanceSquaredTo(p) > currClosest.distanceSquaredTo(p)) return currClosest;

            if(node->p.distanceSquaredTo(p) < currClosest.distanceSquaredTo(p)) currClosest = node->p;

            if(node->left != nullptr && node->right != nullptr){
                if(node->left->rect.contains(p)){
                    currClosest = nearest(node->left, p, currClosest);
                    currClosest = nearest(node->right, p, currClosest);
                } else{
                    currClosest = nearest(node->right, p, currClosest);
                    currClosest = nearest(node->left, p, currClosest);
                }
            } else if(node->left != nullptr){
                currClosest = nearest(node->left, p, currClosest);
            } else if(node->right != nullptr){
                currClosest = nearest(node->right, p, currClosest);
            }

            return currClosest;
        }


};


class kdtree{
    KDTree bst;

    public:
        kdtree() {}

        bool isEmpty() { return bst.isEmpty(); }

        int size() { return bst.totalSize(); }

        void insert(Point2D p) { bst.put(p); }

        bool contains(Point2D p) { return bst.contains(p); }

        vector<Point2D> points() { return bst.inOrder(); }

        Point2D nearest(Point2D p) { return bst.nearest(p); }

        vector<Point2D> range(RectHV rect) { return bst.range(rect); }

        void remove(Point2D key){ bst.remove(key); }

        void removeMin(){ bst.removeMin();}

        void removeMax(){ bst.removeMax();}

        Point2D select(int k){ return bst.select(k);}

        Point2D ceiling(Point2D key){ return bst.ceiling(key); }

        Point2D floor(Point2D key){ return bst.floor(key); }

        Point2D min(){ return bst.min(); }

        Point2D max(){ return bst.max(); }
};