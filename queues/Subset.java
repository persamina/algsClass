import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset 
{
  public static void main(String[] args)
  {
    int k  = Integer.parseInt(args[0]);
    RandomizedQueue<String> rq = new RandomizedQueue<String>();
    String currentString;
    while (!StdIn.isEmpty())
    {
      currentString = StdIn.readString();
      rq.enqueue(currentString);
      //StdOut.printf("string value: %s\n", currentString);
    }
    for (int i = 0; i<k; i++)
    {
      StdOut.printf("%s\n", rq.dequeue());
    }
  }
}