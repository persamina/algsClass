import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
  private int[] board;
  private int dimension;
  private int blankSpot;
  private int blankSpotI;
  private int blankSpotJ;
  private Board[] neighbors;
  private int[][] boardBlocks;
  public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
  {                                        // (where blocks[i][j] = block in row i, column j)
    boardBlocks = blocks;
    board = new int[blocks.length * blocks[0].length];
    dimension = blocks.length;
    for (int i = 0; i < blocks.length; i++)
    {
      for (int j = 0; j < blocks[0].length; j++)
      {
        if (blocks[i][j] == 0)
        {
          blankSpotI = i;
          blankSpotJ = j;
          blankSpot = i*blocks[0].length + j;
        }
        board[i*blocks[0].length + j] = blocks[i][j];
      }
    }
    
  }
  public int dimension()                 // board dimension N
  {
    return dimension;
  } 
  public int hamming()                   // number of blocks out of place
  {
    int oopBlocks = 0;
    for (int i = 0; i < board.length-1; i++)
    {
      if (board[i] != 0 && board[i] != i+1 ) oopBlocks++;
    }
    if (board[board.length-1] != 0) oopBlocks++;
    return oopBlocks;
  }
  
  public int manhattan()                 // sum of Manhattan distances between blocks and goal
  {
    int manhattanDistance = 0;
    int homeSpot, currentSpot, rowDistance;
    for (int i = 0; i < board.length; i++)
    {
      homeSpot = board[i] - 1;
      currentSpot = i;
      if (currentSpot == homeSpot || board[currentSpot] == 0) continue;
      if (currentSpot < homeSpot)
      {
        rowDistance = homeSpot / dimension - currentSpot/dimension;
        currentSpot += dimension*rowDistance;
      }
      else 
      {
        rowDistance = currentSpot/dimension - homeSpot/dimension;
        currentSpot -= dimension*rowDistance;
      }
      manhattanDistance += rowDistance + Math.abs(homeSpot-currentSpot);
    }
    return manhattanDistance;
  } 
  
  public boolean isGoal()                // is this board the goal board?
  {
    for (int i = 0; i < board.length-1; i++)
    {
      if (board[i] != i+1) return false;
    }
    return true;
  } 
  
  public Board twin()                    // a board that is obtained by exchanging any pair of blocks
  {
    Board twinBoard;
    int[][] blocks = new int[dimension][dimension];
    boolean blocksSwapped = false;
    for (int i = 0; i < board.length; i++)
    {
      if (!blocksSwapped && board[i] != 0 && board[i + 1] != 0)
      {
       blocks[i / dimension][i % dimension] = board[i+1];
       blocks[(i+1) / dimension][(i+1) % dimension] = board[i];
       blocksSwapped = true;
       i++;
      }
      else
      {
        blocks[i / dimension][i % dimension] = board[i];
      }
    }
    return new Board(blocks);
  } 
  @Override 
  public boolean equals(Object y)        // does this board equal y?
  {
    
    if (y == this)
      return true;
    
    if (y == null)
      return false;
    
    if (y.getClass() != this.getClass()) 
      return false;
    
    Board other = (Board) y;
    if (other.dimension != this.dimension) return false;
    
    return y.toString().equals(this.toString());
  } 
  
  public Iterable<Board> neighbors()     // all neighboring boards
  {
    return new Iterable<Board>()
    {
      public Iterator<Board> iterator()
      {
        return new BoardIterator();
      }
    };
  } 
  
  private class BoardIterator implements Iterator<Board> 
  {
    
    private Board[] boards;
    private BoardIterator()
    {
      boards = findNeighbors();
    }
    
    private int index;
    
    public boolean hasNext()
    {
      return index != boards.length;
    }
    public Board next()
    {
      if (!hasNext()) 
        throw new NoSuchElementException();
      return boards[index++];
    }
    public void remove() {throw new UnsupportedOperationException();}
  }
  
  private Board[] findNeighbors()
  {
    Board[] neighbors = new Board[4];
    int neighCurIndex = 0;
    if (blankSpotI < dimension - 1) 
    {
      int[][] neighborBlocks = BoardCopy(boardBlocks);
      neighborBlocks[blankSpotI][blankSpotJ] = neighborBlocks[blankSpotI+1][blankSpotJ];
      neighborBlocks[blankSpotI+1][blankSpotJ] = 0;
      neighbors[neighCurIndex] = new Board(neighborBlocks);
      neighCurIndex++;
    }
    if (blankSpotI != 0) 
    {
      int[][] neighborBlocks = BoardCopy(boardBlocks);
      neighborBlocks[blankSpotI][blankSpotJ] = neighborBlocks[blankSpotI-1][blankSpotJ];
      neighborBlocks[blankSpotI-1][blankSpotJ] = 0;
      neighbors[neighCurIndex] = new Board(neighborBlocks);
      neighCurIndex++;
    }
    if (blankSpotJ < dimension - 1) 
    {
      int[][] neighborBlocks = BoardCopy(boardBlocks);
      neighborBlocks[blankSpotI][blankSpotJ] = neighborBlocks[blankSpotI][blankSpotJ+1];
      neighborBlocks[blankSpotI][blankSpotJ+1] = 0;
      neighbors[neighCurIndex] = new Board(neighborBlocks);
      neighCurIndex++;
    }
    if (blankSpotJ != 0) 
    {
      int[][] neighborBlocks = BoardCopy(boardBlocks);
      neighborBlocks[blankSpotI][blankSpotJ] = neighborBlocks[blankSpotI][blankSpotJ-1];
      neighborBlocks[blankSpotI][blankSpotJ-1] = 0;
      neighbors[neighCurIndex] = new Board(neighborBlocks);
      neighCurIndex++;
    }
    if (neighCurIndex  == neighbors.length)
      return neighbors;
    
    Board[] sizedNeighbors = new Board[neighCurIndex];
    for (int i = 0; i < neighCurIndex; i++)
      sizedNeighbors[i] = neighbors[i];
    //StdOut.println(sizedNeighbors.length);
    return sizedNeighbors;
  }
  private int[][] BoardCopy(int[][] arrayToCopy)
  {
    int[][] returnArray = new int[arrayToCopy.length][];
    for (int i = 0; i< arrayToCopy.length; i++)
      returnArray[i] = arrayToCopy[i].clone();
    return returnArray;
    
  }
  public String toString()               // string representation of this board (in the output format specified below)
  {
    int prevRow = 0;
    int rowNum = 0;
    String returnString = " " + dimension + "\n";
    int totalStringLength = String.valueOf(board.length).length() + 1;
    for (int i = 0; i < board.length; i++)
    {
      rowNum = i / dimension;
      if (rowNum != prevRow) returnString += "\n";
      String formatString = "%" + totalStringLength + "d";
      returnString += String.format(formatString, board[i]);
      prevRow = rowNum;
    }
    return returnString;
  }
  
  public static void main(String[] args) // unit tests (not graded)
  {
    //int[][] blocks = new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
    int[][] blocks1 = new int[][] { {  1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, {13, 14, 0, 15}};
    int[][] blocks2 = new int[][] { {  1, 7, 8, 9 }, { 5, 4, 3, 2 }, { 11, 12, 13, 14 }, {6, 15, 0, 10}};
    int[][] blocks3 = new int[][] { {  1, 7, 8, 9 }, { 5, 4, 3, 2 }, { 11, 12, 13, 14 }, {6, 15, 0, 10}};
    Board bd1 = new Board(blocks1);
    Board bd2 = new Board(blocks2);
    Board bd3 = new Board(blocks3);
    ///for (Board bd : bd2.neighbors())
      //StdOut.println(bd);
    //bd1.neighbors();
    StdOut.println(bd1);
    //StdOut.println(bd2.equals(bd3));
    //bd.dimension();
    //StdOut.println(bd);
    //StdOut.println("\n\n");
    //StdOut.println(bd.twin());
    //StdOut.println(bd.hamming());
    //StdOut.println(bd.manhattan());
    //StdOut.println(bd);
    //StdOut.println(bd.isGoal());
  } 
}