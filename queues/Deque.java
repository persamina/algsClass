import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

  private Node first;
  private Node last;
  private int size;
  
  private class Node
  {
    Item item;
    Node next;
    Node previous;
    public Item value()
    {
      return item;
    }
  }
  
  public Deque()
  {
    first = null;
    last = first;
    size = 0;
  }

  public boolean isEmpty()
  {
    return first == null;
  }
  public int size()                        // return the number of items on the deque
  {
    return size;
  }
  public void addFirst(Item item)          // add the item to the front
  {
    checkForAddErrors(item);

    
    Node oldFirst = first;
    first = new Node();
    first.item = item;
    first.next = oldFirst;
    if (oldFirst != null)
      oldFirst.previous = first;
    if (size == 0)
    {
     last = first; 
    }
    size++;
    
  }
  public void addLast(Item item)           // add the item to the end
  {
    checkForAddErrors(item);
    //System.out.println("Value of last");

    //System.out.println(last);
    Node oldLast = last;
    last = new Node();
    last.item = item;
    last.previous = oldLast;
    if (oldLast != null)
      oldLast.next = last;
    
    if (size == 0)
    {
     first = last; 
    }
    size++;
  }
  private void checkForAddErrors(Item item)
  {
    //StdOut.printf("HERE!!!"); 
    if (item == null)
      throw new NullPointerException();
  }
  private void checkForRemoveErrors()
  {
    if (size < 1)
      throw new NoSuchElementException();
  }
  public Item removeFirst()                // remove and return the item from the front
  {
    checkForRemoveErrors();

    Item item = first.item;
    first = first.next;
    size--;
    return item;
  }
  public Item removeLast()                 // remove and return the item from the end
  {
    checkForRemoveErrors();
    Item item = last.item;

    last = last.previous;
    size--;
    return item;
  }
  public Iterator<Item> iterator()         // return an iterator over items in order from front to end
  {
    return new ItemIterator();
  }
  private class ItemIterator implements Iterator<Item>
  {
    private Node current = first;
    public boolean hasNext() 
    {
      return current != null;
    }
    public void remove() {throw new UnsupportedOperationException();}
    public Item next()
    {
      if (current.item == null)
        throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }
  public static void main(String[] args)   // unit testing
  {
    Deque<Integer> deck = new Deque<Integer>();
    
    //deck.addLast(6);
    //deck.addFirst(5);
    //deck.addLast(7);
    //deck.addFirst(4);
    //deck.addLast(8);
    //deck.addFirst(3);
    //deck.addLast(9);
    //deck.addFirst(2);
    //deck.addLast(10);
    //deck.addFirst(1);

    deck.addFirst(10);
    deck.addFirst(9);
    deck.addFirst(8);
    deck.addFirst(7);
    deck.addFirst(6);
    deck.addFirst(5);
    deck.addFirst(4);
    deck.addFirst(3);
    deck.addFirst(2);
    deck.addFirst(1);

    //StdOut.printf("Starting while loop\n\n");
    for (Integer num : deck)
    {
      //StdOut.printf("Attached Node: %d\n", deck.first.attachedNode.value());
      StdOut.printf("Value: %d\n", num);
      
    }
    StdOut.printf("size: %d\n", deck.size);
  }
}