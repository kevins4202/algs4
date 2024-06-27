import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfPoints;
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException("Argument null");
        numberOfPoints = points.length;
        segments = new ArrayList<LineSegment>();
        Arrays.sort(points);
        for (int i = 0; i < numberOfPoints - 3; i++) {
            Point p = points[i];
            for (int k = i + 1; k < numberOfPoints - 2; k++) {
                Point q = points[k];
                for (int j = k + 1; j < numberOfPoints - 1; j++) {
                    Point r = points[j];
                    for (int l = j + 1; l < numberOfPoints; l++) {
                        Point s = points[l];
                        if (p == null || q == null || r == null || s == null || p.equals(q)
                                || q.equals(r) || r.equals(s))
                            throw new IllegalArgumentException("null or repeated point");
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s))
                            segments.add(new LineSegment(p, s));
                    }
                }
            }
        }
    }

    public int numberOfSegments() { // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() { // the line segments
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
