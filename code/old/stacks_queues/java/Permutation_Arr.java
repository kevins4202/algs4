import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;

public class Permutation_Arr<Item> {
    private Item[] deque;
    public int last;
    //if it is -1, it means the deque is empty and the "last" index is null
    private static int index_is_null = -1;


    public Permutation_Arr() {
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

        for (int i = 0; i < last + 1; i++) {
            copy[i] = deque[i];
        }

        deque = copy;
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

    public void iterator2() {
        for (Item i : deque) {
            if (i != null) {
                System.out.print(i);
            } else {
                System.out.print("null");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        Permutation_Arr<String> myPerm = new Permutation_Arr<String>();

        int numberToPrint = Integer.parseInt(args[0]);
        BufferedReader bf = new BufferedReader(new FileReader("input.txt"));
        String strToEnqueue = bf.readLine();
        String[] arr = strToEnqueue.split(" ");
        for (String item : arr) {
            myPerm.enqueue(item);
            //System.out.println(item);
        }
        for (int i = 0; i < numberToPrint; i++) {
            System.out.println(myPerm.dequeue());
            myPerm.iterator2();

        }
    }
}
