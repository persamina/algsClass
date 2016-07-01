import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
  private SET<Point2D> points;
  public PointSET() // construct an empty set of points 
  {
    points = new SET<Point2D>();
  }
  public boolean isEmpty() // is the set empty?  
  {
    if (points.size() == 0 || points == null)
      return true;
    return false;
  }
  public int size() // number of points in the set  
  {
    return points.size();
  }
  public void insert(Point2D p) // add the point to the set (if it is not already in the set) 
  {
    if (p == null) throw new NullPointerException();
    points.add(p);
  }
  public boolean contains(Point2D p) // does the set contain point p?  
  {
    if (p == null) throw new NullPointerException();
    return points.contains(p);
  }
  public void draw() // draw all points to standard draw  
  {
    for (Point2D p : points) 
    {
      p.draw();
    }
    //points.min().drawTo(new Point2D(100,100));
  }
  
  public Iterable<Point2D> range(RectHV rect) // all points that are inside the rectangle  
  {
    Stack<Point2D> pointsStack = new Stack<Point2D>();
    for(Point2D p : points)
    {
      if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax())
      {
        pointsStack.push(p);
      }
    }
    return pointsStack;
  }
  
  public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty  
  { 
    if (points == null || p == null) return null;
    Point2D nearestPoint = points.min();
    double nearestSquaredDistance = -1.0;
    double xDistance, yDistance, currentSquaredDistance;
    for(Point2D currentPoint : points)
    {
      xDistance = p.x() - currentPoint.x();
      yDistance = p.y() - currentPoint.y();
      currentSquaredDistance = Math.pow(xDistance, 2) + Math.pow(yDistance, 2);
      if (currentSquaredDistance < nearestSquaredDistance || nearestSquaredDistance < 0)
      {
        nearestPoint = currentPoint;
        nearestSquaredDistance = currentSquaredDistance;
      }
    }
    return nearestPoint;
  }
  
  public static void main(String[] args)                  // unit testing of the methods (optional)  
  {
    Point2D p1 = new Point2D(0.1, 0.1);
    Point2D p2 = new Point2D(0.2, 0.2);
    Point2D p3 = new Point2D(0.3, 0.3);
    Point2D p4 = new Point2D(0.4, 0.4);
    Point2D p5 = new Point2D(0.5, 0.5);
    Point2D p6 = new Point2D(0.6, 0.6);
    Point2D p7 = new Point2D(0.7, 0.7);
    Point2D p8 = new Point2D(0.8, 0.8);
    Point2D p9 = new Point2D(0.9, 0.9);
    Point2D p10 = new Point2D(1.0, 1.0);
    Point2D p11 = new Point2D(1.1, 1.1);
    Point2D p12 = new Point2D(1.2, 1.2);
    Point2D p13 = new Point2D(1.3, 1.3);
    Point2D p14 = new Point2D(1.4, 1.4);
    Point2D p15 = new Point2D(1.5, 1.5);
    Point2D p16 = new Point2D(1.6, 1.6);
    Point2D p17 = new Point2D(1.7, 1.7);
    Point2D p18 = new Point2D(1.8, 1.8);
    Point2D p19 = new Point2D(1.9, 1.9);
    Point2D p20 = new Point2D(2.0, 2.0);
    Point2D[] allPoints = new Point2D[] {p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20};
    PointSET ps = new PointSET();
    for (Point2D p : allPoints)
    {
      ps.insert(p);
    }
    RectHV rhv = new RectHV(.1,.1,.4,.4);
    for(Point2D p :ps.range(rhv))
    {
      StdOut.println(p);
    }
    StdOut.println(ps.nearest(new Point2D(1.01, 1.01)));
    rhv.draw();
    ps.draw();
  }
}