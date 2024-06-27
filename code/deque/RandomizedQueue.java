import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    //    private int n;
    private int last;
    private Item[] queue;

    // construct
    public RandomizedQueue() {
//        n = 0;
//        arrLength = 2;
        last = -1;
//        first = 0;
        queue = (Item[]) new Object[8];
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        for (int i = 0; i < 9; i++) rq.enqueue(i);

        StdOut.println("is Empty: " + rq.isEmpty());
//        StdOut.println("size: " + rq.size() + " array size: " + rq.arrsize());
        StdOut.println("last: " + rq.last);
        for (Integer integer : rq) {
            StdOut.print(integer + " ");
        }
        StdOut.println();

        for (int i = 0; i < 4; i++) StdOut.println("dequeue " + rq.dequeue());
        for (Integer integer : rq) {
            StdOut.print(integer + " ");
        }
        StdOut.println();

        StdOut.println(rq.sample());


    }

    // is the deque empty?
    public boolean isEmpty() {
        return last == -1;
    }

    // return the number of items on the deque
    public int size() {
        return last + 1;
    }

//    public int arrsize() {
//        return queue.length;
//    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't be null");
        if (last == queue.length - 1) {//full
            resize(queue.length * 2);
        }
        queue[++last] = item;
    }

    // remove and return random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("empty");

        int index = StdRandom.uniformInt(last + 1);

        Item item = queue[index];
        queue[index] = queue[last--];
        queue[last + 1] = null;
        if (size() <= queue.length / 4 && queue.length > 8) resize(queue.length / 4);
        return item;
    }

    // return a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        int index = StdRandom.uniformInt(last + 1);

        return queue[index];
    }

    private void resize(int newLen) {
        Item[] newArray = (Item[]) new Object[newLen];

        if (last + 1 >= 0) System.arraycopy(queue, 0, newArray, 0, last + 1);

        queue = newArray;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i <= last;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queue[i++];
        }
    }


}
