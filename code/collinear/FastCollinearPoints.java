import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Argument null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Null points");
        }

        segments = new ArrayList<>();

        Point[] pointsCopy = points.clone();

        for (int i = 0; i < points.length; i++) {
            // StdOut.println(i);
            Point p = pointsCopy[i];

            Arrays.sort(points, p.slopeOrder());

            ArrayList<Point> found = new ArrayList<Point>();

            double slope = p.slopeTo(points[1]);
            if (slope == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException("Null points");

            found.add(points[0]);
            found.add(points[1]);

            for (int j = 2; j < points.length; j++) {
                double currSlope = p.slopeTo(points[j]);
                // StdOut.println("CURRENT SLOPE " + currSlope);
                if (slope == currSlope) found.add(points[j]);
                else {
                    if (found.size() >= 4) {
                        found.sort(Point::compareTo);
                        LineSegment segment = new LineSegment(found.get(0),
                                                              found.get(found.size() - 1));

                        if (p.compareTo(found.get(0)) == 0)
                            segments.add(segment);

                    }

                    found.clear();
                    slope = currSlope;
                    found.add(p);
                    found.add(points[j]);
                }
            }

            if (found.size() >= 4) {
                found.sort(Point::compareTo);
                LineSegment segment = new LineSegment(found.get(0), found.get(found.size() - 1));
                if (p.compareTo(found.get(0)) == 0)
                    segments.add(segment);
            }
        }
    }  // finds all line segments containing 4 or more points

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }       // the line segments

    public int numberOfSegments() {
        return segments.size();
    }    // the number of line segments
}
