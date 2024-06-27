import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque_Arr<Item> implements Iterable<Item> {
    private Item[] deque;
    public int last;
    //if it is -1, it means the deque is empty and the "last" index is null
    private static int index_is_null = -1;


    public Deque_Arr() {
        deque = (Item[]) new Object[1];
        // Do not need "first" index because it is always 0
        // last item in deque
        // use "last" index to check if deque is empty
        //
        last = index_is_null;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            deque[0] = item;
            last = 0;
        } else {
            if (last == deque.length - 1) {
                //if full, double the size
                resizeForward(2 * deque.length);
            }
            //shift every item one spot down
            for (int i = last; i >= 0; i--) {
                deque[i + 1] = deque[i];
            }
            //insert first item
            deque[0] = item;
            last++;

        }
    }

    private void resizeForward(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.out.println("new forward length: " + capacity);
        for (int i = 0; i < deque.length; i++) {
            copy[i] = deque[i];
        }

        deque = copy;
    }

    public void addLast(Item item) {
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

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty deque");
        }
        Item item = deque[0];

        if (last == 0) {
            //there is only one item in the deque
            last = index_is_null;
        } else {
            for (int i = 0; i < last; i++) {
                deque[i] = deque[i + 1];
            }
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

        for (int i = 0; i < last + 1; i++) {
            copy[i] = deque[i];
        }

        deque = copy;
    }

    public Item removeLast() {

        if (isEmpty()) {

            throw new UnsupportedOperationException("Cannot remove from empty deque");
        }
        Item item = deque[last];
        deque[last] = null;
        if (last == 0) {
            last = index_is_null;
        } else {
            if (last <= deque.length / 4) {
                shrink(deque.length / 4);
            }
            last--;
        }

        return item;

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
        Deque_Arr<String> myDeque = new Deque_Arr<String>();
        for (int i = 0; i < 1000; i++) {
            myDeque.addFirst("Hello" + i);
        }

        for (int i = 0; i < 600; i++) {
            myDeque.removeLast();
            myDeque.addLast("Goodbye");
            myDeque.removeFirst();
        }
        System.out.println(myDeque.size());
        System.out.println(myDeque.last);
        myDeque.iterator2();
    }
}
