import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }                             // construct an empty set of points

    public static void main(String[] args) {
    }            // unit testing of the methods (optional)

    public boolean isEmpty() {
        return size() == 0;
    }                // is the set empty?

    public int size() {
        return set.size();
    }               // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");

        set.add(p);
    }           // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        return set.contains(p);
    }       // does the set contain point p?

    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(0.01);

        for (Point2D point : set) {
            point.draw();
        }
    }        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("null argument");

        Queue<Point2D> queue = new Queue<>();

        for (Point2D p : set) {
            if (rect.contains(p)) queue.enqueue(p);
        }

        return queue;
    }        // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");

        Point2D min = null;

        for (Point2D q : set) {
            if (min == null || p.distanceSquaredTo(q) < p.distanceSquaredTo(min)) {
                min = q;
            }
        }

        return min;
    }       // a nearest neighbor in the set to point p; null if the set is empty
}
