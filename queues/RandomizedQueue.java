import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
  
  private Item[] q;
  private Item tempStore;
  private int tail = -1;
  private int head = 0;
  private int rIndex;
  private boolean shuffled = false;
  public RandomizedQueue()                 // construct an empty randomized queue
  {
    q = (Item[]) new Object[2];  
  }
  
  public boolean isEmpty()                 // is the queue empty?
  {
    return tail-head < 0;
  }
  public int size()                        // return the number of items on the queue
  {
    return tail-head+1;
  }
  public void enqueue(Item item)           // add the item
  {
    if (item == null)
      throw new NullPointerException();
    if (tail-head >= q.length-1 || (tail-head+1) < q.length/4)
      resizeArray();
    int newItemIndex;

    if (tail >= q.length-1 && tail-head < q.length - 1)
        newItemIndex = --head;
    else 
        newItemIndex = ++tail;
    if(tail + 1 >= 2)
        rIndex = StdRandom.uniform(head, tail+1);
    else
        rIndex = 0;
    q[newItemIndex] = q[rIndex];
    q[rIndex] = item;
  }
  public Item dequeue()                    // remove and return a random item
  {
    if (size() <= 0)
      throw new NoSuchElementException();
    Item item = q[head];
    q[head] = null;
    head++;
    return item;
  }
  public Item sample()                     // return (but do not remove) a random item
  {
    if (size() <= 0)
      throw new NoSuchElementException();
    int i = StdRandom.uniform(head, tail+1);
    return q[i];
  }
  private void resizeArray()
  {
    Item[] oldQ = q;
    if (tail >= q.length - 1)
    {
      q = (Item[]) new Object[oldQ.length * 2];
      for (int i = 0; i < oldQ.length; i++)
      {
          q[i] = oldQ[i];
      }
    }
  }
  public Iterator<Item> iterator()         // return an independent iterator over items in random order
  {
    return new ItemIterator();
  }
  
  private class ItemIterator implements Iterator<Item>
  {
    Item value;
    public boolean hasNext() 
    {
      return !isEmpty();
    }
    public void remove() {throw new UnsupportedOperationException();}
    public Item next()
    {
      if (q[head] == null)
        throw new NoSuchElementException();
      value = q[head];
      q[head++] = null;
      return value;
    }
  }
  public static void main(String[] args)   // unit testing
  {
    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
    StdOut.printf("size: %d\n", rq.size());
    rq.enqueue(10);
    StdOut.printf("size: %d\n", rq.size());
    rq.enqueue(9);
    StdOut.printf("size: %d\n", rq.size());
    rq.enqueue(6);
    StdOut.printf("size: %d\n", rq.size());
    rq.enqueue(3);
    StdOut.printf("size: %d\n", rq.size());
    rq.enqueue(1);
    StdOut.printf("size: %d\n", rq.size());
    //rq.enqueue(4);
    //rq.enqueue(5);
    //rq.enqueue(11);
    StdOut.printf("sample value: %d\n", rq.sample());
    StdOut.printf("sample value: %d\n", rq.sample());
    StdOut.printf("sample value: %d\n", rq.sample());
    //StdOut.printf("random value: %d\n", rq.dequeue());
    //StdOut.printf("random value: %d\n", rq.dequeue());
    //StdOut.printf("random value: %d\n", rq.dequeue());
    //StdOut.printf("random value: %d\n", rq.dequeue());
    //rq.enqueue(58);
    //rq.enqueue(64);
    for (Integer num : rq)
    {   
        StdOut.printf("random value: %d\n", num);
        StdOut.printf("size: %d\n", rq.size());
    }
    
  }
}
