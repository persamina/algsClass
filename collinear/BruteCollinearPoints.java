import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
public class BruteCollinearPoints 
{
  //private Point[] points;
  private LineSegment[] ls = new LineSegment[0];
  private Point leftPoint, rightPoint;
  public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
  {
    if (points == null) { throw new NullPointerException(); }
    Arrays.sort(points);
    Point prevPoint = points[0];
    for (int i = 1; i< points.length; i++)
    {
      if (points[i].compareTo(prevPoint) == 0)
      {
        throw new IllegalArgumentException();
      }
      prevPoint = points[i];
    }
    double slopeJ, slopeK, slopeM;
    int count = 0;
    for (int i = 0; i < points.length; i++)
    {            
      if (points[i] == null) { throw new NullPointerException(); }
      for(int j = i+1; j < points.length; j++) 
      {
        if (points[i] == null) { throw new NullPointerException(); }
        slopeJ = points[i].slopeTo(points[j]);
        for(int k = j+1; k < points.length; k++)
        {
          if (points[i] == null) { throw new NullPointerException(); }
          slopeK = points[i].slopeTo(points[k]);
          for(int m = k+1; m < points.length; m++)
          {
            if (points[i] == null) { throw new NullPointerException(); }
            slopeM = points[i].slopeTo(points[m]);
            checkPoint(points[i]);
            checkPoint(points[j]);
            checkPoint(points[k]);
            checkPoint(points[m]);
            count++;
            if (slopeJ == Double.NEGATIVE_INFINITY || 
               slopeK == Double.NEGATIVE_INFINITY || 
               slopeM == Double.NEGATIVE_INFINITY)
            {
              throw new IllegalArgumentException();
            }
            if (slopeJ == slopeK && slopeJ == slopeM) 
            {
              addToSegments(new LineSegment(leftPoint, rightPoint));
              //StdOut.printf("combinations: %d %d %d %d \n", i, j, k, m);
            }
            leftPoint = null;
            rightPoint = null;
          }
        }
      }
    }
    //StdOut.printf("total: %d \n", count);
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
  public int numberOfSegments()        // the number of line segments
  {
    return ls.length;
  }
  public LineSegment[] segments()                // the line segments
  {
    return ls;
  }
  private void checkPoint(Point pointToCheck)
  {
    if (leftPoint == null || pointToCheck.compareTo(leftPoint) < 0)
    {
      leftPoint = pointToCheck;
    }
    if (rightPoint == null || pointToCheck.compareTo(rightPoint) > 0)
    {
      rightPoint = pointToCheck;
    }
  }
  public static void main(String[] args) {

    /*Point p0 = new Point(19000,  10000);
    Point p1 = new Point(18000,  10000);
    Point p2 = new Point(32000,  10000);
    Point p3 = new Point(21000,  10000);
    Point p4 = new Point( 1234,   5678);
    Point p5 = new Point(14000,  10000);
    Point[] points = new Point[] {p0, p1, p2, p3, p4, p5};
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    StdOut.println(collinear.segments());
    */
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    
  }
}