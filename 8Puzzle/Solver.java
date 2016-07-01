import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Comparator;

public class Solver {
  
  private MinPQ<Node> pq = new MinPQ<Node>();
  private MinPQ<Node> pqTwin = new MinPQ<Node>();
  private boolean boardSolved = false;
  private boolean twinBoardSolved = false;
  private boolean isSolvable = false;
  private Board solutionBoard;
  private Node solutionNode;
  private int minMoves = -1;
  private Stack<Board> solutionStack = new Stack<Board>();
  
  private class Node implements Comparable<Node>
  {
    //public static final Comparator<Board> BY_PRIORITY = new ByPriority();
    private Board current;
    private Node previous;
    private int moves;

    public Node(Board currentBoard, Node previousNode, int totalMoves)
    {
      current = currentBoard;
      previous = previousNode;
      moves = totalMoves;
    }
    @Override
    public int compareTo(Node that)
    {
      if ((this.moves + this.current.manhattan()) < (that.moves + that.current.manhattan())) return -1;
      if ((this.moves + this.current.manhattan()) > (that.moves + that.current.manhattan())) return 1;
      return 0;
    }
    
  }
    
  public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
  {
    if (initial == null) throw new NullPointerException();
    
    Node minNode = new Node(initial, null, 0);
    Node minTwinNode = new Node(initial.twin(), null, 0);
    
    pq.insert(minNode);
    pqTwin.insert(minTwinNode);
    
    Node currentNode;
    Node currentTwinNode;
    if (minNode.current.isGoal())
    {
      solutionNode = minNode;
      minMoves = minNode.moves;
      boardSolved = true;
    }
    if (minTwinNode.current.isGoal())
    {
      solutionNode = minTwinNode;
      twinBoardSolved = true;
    }
    while (!boardSolved && !twinBoardSolved)
    {
      
      minNode = pq.delMin();
      minTwinNode = pqTwin.delMin();
      /*
      if(System.nanoTime() - startTime > 10000000000.0)
      {
        StdOut.printf("moves: %d, priority: %d \n\n", minNode.moves, (minNode.current.manhattan()+minNode.moves));
        StdOut.println(minNode.current);
        startTime = System.nanoTime();
      }
      */
      for(Board bd: minNode.current.neighbors())
      {
        if (minNode.previous != null && bd.equals(minNode.previous.current))
          continue;
        currentNode = new Node(bd, minNode, minNode.moves+1);
        if (bd.isGoal())
        {
          boardSolved = true;
          solutionNode = currentNode;
          minMoves = solutionNode.moves;
          break;
        }

        pq.insert(currentNode);

      }
        //StdOut.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n");
        //StdOut.println(minTwinNode.current);
        //StdOut.println("Neigbors\n");
      for(Board bd: minTwinNode.current.neighbors())
      {
        
        if (minTwinNode.previous != null && bd.equals(minTwinNode.previous.current))
        {
          //StdOut.println("same as previous");
          continue;
        }
        //StdOut.println(bd);
        currentTwinNode = new Node(bd, minTwinNode, minTwinNode.moves+1);
        if (bd.isGoal())
        {
          twinBoardSolved = true;
          break;
        }
        pqTwin.insert(currentTwinNode);
      }
      
    }
    if (boardSolved && !twinBoardSolved)
    {
      currentNode = solutionNode;
      while (currentNode.previous != null)
      {
        solutionStack.push(currentNode.current);
        currentNode = currentNode.previous;
      }
      solutionStack.push(currentNode.current);
    }
  }
    
  public boolean isSolvable()            // is the initial board solvable?
  {
    return boardSolved;
  }
    
  public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
  {
      return minMoves;
  }
    
  public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
  {
      return solutionStack;
  }
  
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }
  
  /*
  public static void main(String[] args) // solve a slider puzzle (given below)
  {
    int[][] blocks2 = new int[][] { {  1, 7, 8, 9 }, { 5, 4, 3, 2 }, { 11, 12, 13, 14 }, {6, 15, 0, 10}};
    int[][] blocks1 = new int[][] { {  1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, {13, 14, 15, 0}};
    int[][] blocks3 = new int[][] { { 1, 0 }, { 3, 2 } };
    int[][] blocks4 = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } };
    int[][] blocks5 = new int[][] { {  2, 1, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, {13, 15, 14, 0}};
    //Board bd1 = new Board(blocks1);
    //Solver solve = new Solver(bd1);
    Board bd1 = new Board(blocks1);
    Solver solve = new Solver(bd1);
    int i = 0;
    StdOut.println(solve.moves());
    for(Board bd : solve.solution())
    {
      
      StdOut.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
      StdOut.println(i++);
      StdOut.println(bd);
    }
    StdOut.println(solve.isSolvable());
    
  }
  */
}