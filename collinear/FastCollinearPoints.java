import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
  private LineSegment[] ls = new LineSegment[0];
  public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
  {
    Arrays.sort(points);
    Point[] localPoints = new Point[points.length];

    for (int i = 0; i < points.length; i++)
    {
      for (int j = 0; j < points.length; j++)
      {
        localPoints[j] = points[j];
      }
      Arrays.sort(localPoints, points[i].slopeOrder());
      double prevValue = localPoints[0].slopeTo(localPoints[1]);
      double currentValue;
      int startIndex = 1;
      int endIndex = 1;
      for (int j = 2; j < localPoints.length; j++)
      {
        currentValue = localPoints[0].slopeTo(localPoints[j]);
        if (currentValue == prevValue)
        {
          endIndex++;
        }
        if (currentValue != prevValue || j >= localPoints.length-1)
        {
          if (endIndex - startIndex + 1 >= 3)
          {
            if (localPoints[0].compareTo(localPoints[startIndex]) < 0)
            {
              addToSegments(new LineSegment(localPoints[0], localPoints[endIndex]));
            }
          }
          prevValue = currentValue;
          startIndex = j;
          endIndex = j;
        }
      }
    }
  }
  private void addToSegments(LineSegment lsToAdd)
  {
    if (ls.length != 0)
    {
      LineSegment[] oldls = ls;
      ls = new LineSegment[oldls.length+1];
      for (int i = 0; i< oldls.length; i++)
      {
        ls[i] = oldls[i];
      }
    }
    else { ls = new LineSegment[1]; }
    ls[ls.length-1] = lsToAdd;
  }
  public int numberOfSegments() // the number of line segments
  {
    return ls.length;
  }
  public LineSegment[] segments() // the line segments
  {
    return ls;
  }
  public static void main(String[] args) {

    // read the N points from a file
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.show(0);
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
  }

}