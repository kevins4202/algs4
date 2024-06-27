#include <iostream>
#include <string>
#include <limits>
using namespace std;

class Point{
    private:
        int x,y;

    public:
    
        Point(int xx, int yy):x(xx), y(yy) {}

        Point(const Point &p){
            x = p.x; y = p.y;
        }

        int const getX() const {return x;}
        int const getY() const { return y;}


        /**
         * Returns the slope between this point and the specified point.
         * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
         * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
         * +0.0 if the line segment connecting the two points is horizontal;
         * Double.POSITIVE_INFINITY if the line segment is vertical;
         * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
         *
         * @param  that the other point
         * @return the slope between this point and the specified point
         */

        double slopeTo(Point that) {
            double dx = x-that.x;
            double dy = y-that.y;

            if(dx ==0){
                if(dy==0) return (-1)*numeric_limits<double>::infinity();
                return numeric_limits<double>::infinity();
            }

            return dy/dx;
        }

        Point copy(){
            Point q(x,y);
            return q;
        }

        /**
         * Compares two points by y-coordinate, breaking ties by x-coordinate.
         * Formally, the invoking point (x0, y0) is less than the argument point
         * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
         *
         * @param  that the other point
         * @return the value <tt>0</tt> if this point is equal to the argument
         *         point (x0 = x1 and y0 = y1);
         *         a negative integer if this point is less than the argument
         *         point; and a positive integer if this point is greater than the
         *         argument point
         */
        int compareTo(Point that) {
            if(x==that.x && y == that.y) return 0;
            if(y<that.y ||(y == that.y && x<that.x)){
                return -1;
            } else return 1;
        }

        /**
         * Compares two points by the slope they make with this point.
         * The slope is defined as in the slopeTo() method.
         *
         * @return the Comparator that defines this ordering on points
         */
        int slopeOrder(Point a, Point b) {
            double sa = slopeTo(a), sb = slopeTo(b);
            if(sa < sb) return -1;
            else if (sa > sb) return 1;
            return 0;
        }

        bool operator==(const Point &a){
            return x == a.x && y == a.y;
        }

        bool operator<(const Point &a) const{
            return y < a.y || (y == a.y && x < a.x);
        }

        bool operator>(const Point &a){
            return y > a.y || (y == a.y && x > a.x);
        }

        /**
         * Returns a string representation of this point.
         * This method is provide for debugging;
         * your program should not rely on the format of the string representation.
         *
         * @return a string representation of this point
         */
        string toString() {
            
            return "(" + to_string(x) + ", " + to_string(y) + ")";
        }

};

// Point clone(const Point p) const {}

// bool operator<(const Point &a, const Point &b){
//             return a.getY() < b.getY() || (a.getY() == b.getY() && a.getX() < b.getX());
//         }

class LineSegment {
    private:
        Point p,q;

    /**
     * Initializes a new line segment.
     *
     * @param  p one endpoint
     * @param  q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *         is <tt>null</tt>
     */
    public:
        
        LineSegment(Point pp, Point qq):p(pp), q(qq){
            if (p.compareTo(q) ==0) {
                throw  invalid_argument("both arguments to LineSegment constructor are the same point: " + p.toString());
            }
        }

        Point getP() const { return p;}
        Point getQ()const { return q;}

        /**
         * Returns a string representation of this line segment
         * This method is provide for debugging;
         * your program should not rely on the format of the string representation.
         *
         * @return a string representation of this line segment
         */
        string toString() {
            return p.toString() + " -> " + q.toString();
        }

        bool operator==(const LineSegment &a){
            return p==a.p && q == a.q;
        }
};

bool operator==(const LineSegment &a, const LineSegment &b){
    return (b.getP()==a.getP() && b.getQ() == a.getQ()) || (b.getP() == a.getQ() && b.getQ() == a.getP());
}