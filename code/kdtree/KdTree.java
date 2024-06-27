import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }                             // construct an empty set of points

    public static void main(String[] args) {
    }            // unit testing of the methods (optional)

    public boolean isEmpty() {
        return size() == 0;
    }                // is the set empty?

    public int size() {
        return size;
    }               // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");

        if (contains(p)) return;

        root = insert(p, root, true, 0.0, 0.0, 1.0, 1.0);
    }           // add the point to the set (if it is not already in the set)

    private Node insert(Point2D p, Node curr, boolean vert, double xmin, double ymin, double xmax,
                        double ymax) {
        if (curr == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        int cmp = compareTo(p, curr.p, vert);

        if (cmp == 0) return curr;

        if (vert) { // line vertical
            if (cmp < 0) curr.lb = insert(p, curr.lb, !vert, xmin, ymin, curr.p.x(), ymax);
            else curr.rt = insert(p, curr.rt, !vert, curr.p.x(), ymin, xmax, ymax);
        }
        else {
            if (cmp < 0) curr.lb = insert(p, curr.lb, !vert, xmin, ymin, xmax, curr.p.y());
            else curr.rt = insert(p, curr.rt, !vert, xmin, curr.p.y(), xmax, ymax);
        }

        return curr;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");

        return contains(p, root, true);
    }       // does the set contain point p?

    private boolean contains(Point2D p, Node curr, boolean vert) {
        if (curr == null) return false;

        int cmp = compareTo(p, curr.p, vert);
        if (cmp == 0) return true;
        else if (cmp < 0) return contains(p, curr.lb, !vert);
        else return contains(p, curr.rt, !vert);

    }       // does the set contain point p?

    public void draw() {
        StdDraw.clear();

        draw(root, true);
    }        // draw all points to standard draw

    private void draw(Node curr, boolean vert) {
        if (curr == null) return;

        draw(curr.lb, !vert);

        if (vert) { // vertical
            StdDraw.setPenColor(StdDraw.RED);

            StdDraw.line(curr.p.x(), curr.rect.ymin(), curr.p.x(), curr.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);

            StdDraw.line(curr.rect.xmin(), curr.p.y(), curr.rect.xmax(), curr.p.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        curr.p.draw();

        draw(curr.rt, !vert);
    }

    private int compareTo(Point2D p1, Point2D p2, boolean vert) {
        if (p1.equals(p2)) return 0;
        if (vert) { // root height 0
            // compare x
            if (p1.x() == p2.x()) return p1.y() < p2.y() ? -1 : 1;
            else return p1.x() < p2.x() ? -1 : 1;
        }
        else { // compare y
            if (p1.y() == p2.y()) return p1.x() < p2.x() ? -1 : 1;
            else return p1.y() < p2.y() ? -1 : 1;
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("null argument");

        Queue<Point2D> queue = new Queue<>();

        range(rect, root, queue);

        return queue;
    }        // all points that are inside the rectangle (or on the boundary)

    private Iterable<Point2D> range(RectHV rect, Node curr, Queue<Point2D> queue) {
        if (curr == null || !rect.intersects(curr.rect)) return queue;

        if (rect.contains(curr.p)) queue.enqueue(curr.p);

        range(rect, curr.lb, queue);
        range(rect, curr.rt, queue);

        return queue;
    }


    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");

        return nearest(p, root, root, true).p;
    }       // a nearest neighbor in the set to point p; null if the set is empty

    private Node nearest(Point2D target, Node curr, Node best, boolean vert) {
        if (curr == null || curr.rect.distanceSquaredTo(target) > best.p.distanceSquaredTo(target))
            return best;

        double dist = curr.p.distanceSquaredTo(target);

        if (dist < best.p.distanceSquaredTo(target)) {
            best = curr;
        }

        int cmp = compareTo(target, curr.p, vert);

        if (cmp < 0) {
            best = nearest(target, curr.lb, best, !vert);
            best = nearest(target, curr.rt, best, !vert);
        }
        else if (cmp > 0) {
            best = nearest(target, curr.rt, best, !vert);
            best = nearest(target, curr.lb, best, !vert);
        }

        return best;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            lb = null;
            rt = null;
        }

        public Node getLb() {
            return lb;
        }

        public void setLb(Node lb) {
            this.lb = lb;
        }

        public Node getRt() {
            return rt;
        }

        public void setRt(Node rt) {
            this.rt = rt;
        }

        public RectHV getRect() {
            return rect;
        }

        public Point2D getP() {
            return p;
        }
    }
}
