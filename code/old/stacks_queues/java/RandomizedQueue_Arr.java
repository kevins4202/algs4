import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue_Arr<Item> implements Iterable<Item> {

    private Item[] deque;
    public int last;
    //if it is -1, it means the deque is empty and the "last" index is null
    private static int index_is_null = -1;


    public RandomizedQueue_Arr() {
        deque = (Item[]) new Object[1];
        // Do not need "first" index because it is always 0
        // last item in deque
        // use "last" index to check if deque is empty
        //
        last = index_is_null;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        //full, so double the size
        if (last == deque.length - 1) {
            resizeBack(2 * deque.length);
        }

        //insert item to last
        last++;
        deque[last] = item;
    }

    private void resizeBack(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.out.println("new length: " + capacity);
        for (int i = 0; i < deque.length; i++) {
            copy[i] = deque[i];
        }

        deque = copy;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty deque");
        }

        int indexToRemove = randomIndex();

        Item item = deque[indexToRemove];

        if (last == 0) {
            //there is only one item in the deque
            last = index_is_null;
        } else {
            //move everything forward, starting from that index
            for (int i = indexToRemove; i < last; i++) {
                deque[i] = deque[i + 1];
            }
            deque[last] = null;
            last--;
        }

        if (last < deque.length / 4) {
            shrink(deque.length / 4);
        }

        return item;
    }

    public void shrink(int capacity) {
        //if capacity is 0, then do nothing
        if (capacity == 0) {
            return;
        }

        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < last; i++) {
            copy[i] = deque[i];
        }

        deque = copy;
    }

    public Item sample() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        int indexToRemove = randomIndex();
        return deque[indexToRemove];
    }

    private int randomIndex() {
        Random rand = new Random();

        int index = rand.nextInt(size());
        System.out.println("index: " + index);

        return index;
    }

    public boolean isEmpty() {
        return last == index_is_null;
    }

    public int size() {
        return last + 1;
    }

    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {
        public boolean hasNext() {
            //temporary
            return true;
        }

        public Item next() {
            //temporary
            return deque[0];
        }
    }

    public void iterator2() {
        for (Item i : deque) {
            if (i != null) {
                System.out.println(i);
            } else {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        RandomizedQueue_Arr<String> myRandQueue = new RandomizedQueue_Arr<String>();


        myRandQueue.enqueue("Hello");
        myRandQueue.enqueue("Goodbye");
        myRandQueue.enqueue("See you");
        myRandQueue.dequeue();
        myRandQueue.dequeue();
        myRandQueue.dequeue();
        myRandQueue.enqueue("Hi");
        //System.out.println(myRandQueue.sample());
        myRandQueue.iterator2();
    }
}
