import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
  
  private static class Node {
    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree
  }
  private Node initial;
  private int size;
  public KdTree() // construct an empty set of points 
  {
     
  }
  public boolean isEmpty() // is the set empty? 
  {
     return size == 0 && initial == null;
  } 
  public int size() // number of points in the set  
  {
     return size;
  }
  public void insert(Point2D p) // add the point to the set (if it is not already in the set) 
  {
    int level = 0;
    boolean inserted = false;
    boolean cmpXCoor = false;
    double currentNodeCV;
    double insertNodeCV;
    double[] rectCoor = new double[] {0.0, 0.0, 1.0, 1.0};
    Node currentNode = initial;
    Node newNode;
    while (!inserted)
    {
      if (currentNode == null)
      {
        currentNode = new Node();
        currentNode.p = p;
        currentNode.rect = new RectHV(rectCoor[0], rectCoor[1], rectCoor[2], rectCoor[3]);
        inserted = true;
        if (size == 0) initial = currentNode;
        continue;
      }
      if (level % 2 == 0)
      {
        currentNodeCV = currentNode.p.x();
        insertNodeCV = p.x();
        cmpXCoor = true;
      }
      else
      {
        currentNodeCV = currentNode.p.y();
        insertNodeCV = p.y();
        cmpXCoor = false;
      }      
      if (insertNodeCV < currentNodeCV)
      {
        if (cmpXCoor)
          rectCoor[2] = currentNode.p.x();
        else
          rectCoor[3] = currentNode.p.y();
          
        if (currentNode.lb != null)
        {
          currentNode = currentNode.lb;
          level++;
          continue;
        }
        newNode = new Node();
        newNode.p = p;
        newNode.rect = new RectHV(rectCoor[0], rectCoor[1], rectCoor[2], rectCoor[3]);
        currentNode.lb = newNode;
        inserted = true;
      }
      else
      {
        if (cmpXCoor)
          rectCoor[0] = currentNode.p.x();
        else
          rectCoor[1] = currentNode.p.y();
        if(currentNode.p.x() == p.x() && currentNode.p.y() == p.y())
          break;
        if (currentNode.rt != null) 
        {
          currentNode = currentNode.rt;
          level++;
          continue;
        }

        newNode = new Node();
        newNode.p = p;
        newNode.rect = new RectHV(rectCoor[0], rectCoor[1], rectCoor[2], rectCoor[3]);
        currentNode.rt = newNode; 
        inserted = true;
      }

      level++;
    }
    if (inserted) size++;
  }
 
  public boolean contains(Point2D p) // does the set contain point p?  
  {
    Node currentNode = initial;
    boolean pointFound = false;

    double currentNodeValue;
    double valueToFind;
    int level = 0;
    
    while (!pointFound && currentNode != null)
    {
      if (level % 2 == 0)
      {
        currentNodeValue = currentNode.p.x();
        valueToFind = p.x();
      }
      else
      {
        currentNodeValue = currentNode.p.y();
        valueToFind = p.y();
      } 
      if (valueToFind < currentNodeValue)
        currentNode = currentNode.lb;
      else if (valueToFind > currentNodeValue) 
        currentNode = currentNode.rt;
      else
      {
        if (p.x() == currentNode.p.x() && p.y() == currentNode.p.y())
          pointFound = true;
        currentNode = currentNode.rt;
      }
      level++;
    }
    return pointFound;
  }
  
  public void draw() // draw all points to standard draw  
  {
    int pointsDrawn = 0;
    Node currentNode;
    boolean currentCompX;
    Queue<Node> nodesToDraw = new Queue<Node>();
    Queue<Boolean> compX = new Queue<Boolean>();
    nodesToDraw.enqueue(initial);
    compX.enqueue(true);
    while (!nodesToDraw.isEmpty())
    {
      currentNode = nodesToDraw.dequeue();
      currentCompX = compX.dequeue();
      if (currentNode.lb != null) 
      {
        nodesToDraw.enqueue(currentNode.lb);
        compX.enqueue(!currentCompX);
      }
      if (currentNode.rt != null) 
      {
        nodesToDraw.enqueue(currentNode.rt);
        compX.enqueue(!currentCompX);
      }
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(.01);
      currentNode.p.draw();
      if (currentCompX)
      {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(currentNode.p.x(), currentNode.rect.ymin(), currentNode.p.x(), currentNode.rect.ymax());
      }
      else
      {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius();
        StdDraw.line(currentNode.rect.xmin(), currentNode.p.y(), currentNode.rect.xmax(), currentNode.p.y());
      }
    }
  }
  
  public Iterable<Point2D> range(RectHV rect) // all points that are inside the rectangle  
  {
    Node currentNode;
    Queue<Node> firstSearch = new Queue<Node>();
    firstSearch.enqueue(initial);
    Stack<Point2D> points = new Stack<Point2D>();
    while(!firstSearch.isEmpty())
    {
      currentNode = firstSearch.dequeue();
      if (rect.contains(currentNode.p)) 
      {
        points.push(currentNode.p);
      }
      if (currentNode.lb != null && rect.intersects(currentNode.lb.rect)) firstSearch.enqueue(currentNode.lb);
      if (currentNode.rt != null && rect.intersects(currentNode.rt.rect)) firstSearch.enqueue(currentNode.rt);
    }
    return points;
  }
  
  public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty 
  {
    if (isEmpty()) return null;
    Node currentNode;
    double currentSquareDistance, xDistance, yDistance;
    double nearestSquareDistance = 9999999.0;
    double distSquaredLB = 9999999.0;
    double distSquaredRT = 9999999.0;
    Queue<Node> firstSearch = new Queue<Node>();
    Queue<Node> secondSearch = new Queue<Node>();
    Point2D nearestPoint = initial.p;
    firstSearch.enqueue(initial);
    
    while (!firstSearch.isEmpty()  || !secondSearch.isEmpty())
    {
      if(!firstSearch.isEmpty()) 
        currentNode = firstSearch.dequeue();
      else
        currentNode = secondSearch.dequeue();
      if (currentNode.rect.distanceSquaredTo(p) >= nearestSquareDistance) continue;
      if (currentNode.lb == null || currentNode.rt == null)
      {
        if ( currentNode.lb != null) firstSearch.enqueue(currentNode.lb);
        if ( currentNode.rt != null) firstSearch.enqueue(currentNode.rt);
      }
      else
      {
        distSquaredLB = currentNode.lb.rect.distanceSquaredTo(p);
        distSquaredRT = currentNode.rt.rect.distanceSquaredTo(p);
        if (distSquaredLB < distSquaredRT)
        {
          if (distSquaredLB < nearestSquareDistance && currentNode.lb != null) firstSearch.enqueue(currentNode.lb);
          if (distSquaredRT < nearestSquareDistance && currentNode.rt != null) secondSearch.enqueue(currentNode.rt);
        }
        else
        {
          if (distSquaredRT < nearestSquareDistance && currentNode.lb != null) firstSearch.enqueue(currentNode.rt);
          if (distSquaredLB < nearestSquareDistance && currentNode.rt != null) secondSearch.enqueue(currentNode.lb);
        }
      }
      xDistance = currentNode.p.x() - p.x();
      yDistance = currentNode.p.y() - p.y();
      currentSquareDistance = Math.pow(xDistance, 2) + Math.pow(yDistance, 2);
      if (currentSquareDistance < nearestSquareDistance)
      {
        nearestSquareDistance = currentSquareDistance;
        nearestPoint = currentNode.p;
      }
    }
    return nearestPoint;
  } 
  public static void main(String[] args) // unit testing of the methods (optional)  
  {
    KdTree kd = new KdTree();
    StdOut.println(kd.isEmpty());

    Point2D p1 = new Point2D(0.363281, 0.576172);
    Point2D p2 = new Point2D(0.548828, 0.443359);
    Point2D p3 = new Point2D(0.216797, 0.632813);
    
    Point2D p4 = new Point2D(0.152344, 0.089844);
    Point2D p5 = new Point2D(0.074219, 0.558594);
    Point2D p6 = new Point2D(0.296875, 0.841797);
    Point2D p7 = new Point2D(0.457031, 0.376953);
    Point2D p8 = new Point2D(0.900391, 0.898438);
    Point2D p9 = new Point2D(0.833984, 0.806641);
    Point2D p10 = new Point2D(0.533203, 0.134766);
    Point2D ptest = new Point2D(.06, .65);
    Point2D[] points = new Point2D[] { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10};
    for (Point2D p : points)
    {
      kd.insert(p);
    }
    kd.draw();
    StdOut.println(kd.nearest(ptest));
    //StdOut.println(kd.isEmpty());
    //StdOut.println(kd.contains(p5));
    //StdOut.println(kd.contains(new Point2D(.6, .1)));
    //StdOut.println(kd.size());
     
  }
}