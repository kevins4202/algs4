import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue_LL<Item> implements Iterable<Item> {
    private Node first, last = null;
    private int count = 0;


    public RandomizedQueue_LL() {

    }

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            if (count != 0) {
                Node oldlast = last;

                last = new Node();
                last.item = item;
                last.next = null;
                oldlast.next = last;

            } else {
                last = new Node();
                last.item = item;
                last.next = null;
                first = last;
            }

            count++;
            System.out.println("enqueued " + item);
        }
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int indexToGet = randomIndex();
        Node temp = first;
        for (int i = 0; i < indexToGet; i++) {
            temp = temp.next;
        }
        return temp.item;
    }

    public Item dequeue() {
        //is empty
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // pick a random index
        int indexToRemove = randomIndex();

        Item item;
        Node temp = first;

        if (indexToRemove == 0) {
            //if the first item is chosen
            item = first.item;
            first = first.next;

        } else if (indexToRemove == size() - 1) {
            //if the last item is chosen
            //Go to second-to-last item
            for (int i = 0; i < size() - 2; i++) {
                temp = temp.next;
            }
            item = last.item;
            last = temp;
            last.next = null;
        } else {
            //Go to the node before it
            for (int i = 0; i < indexToRemove - 1; i++) {
                temp = temp.next;
            }
            item = temp.next.item;
            //skip the node that you are going to remove
            temp.next = temp.next.next;
        }

        count--;
        return item;
    }

    public int randomIndex() {
        Random rand = new Random();
        int index = rand.nextInt(size());
        System.out.println("index: " + index);

        return index;
    }


    public void myIterator() {
        Node temp = first;
        System.out.println(count);
        System.out.println("temp:" + temp.item);
        if (temp == null) {
            System.out.println("Deque is empty.");
        } else {
            System.out.println("Deque is not empty");
            while (temp != null) {
                System.out.println(temp.item);
                temp = temp.next;
            }
        }

    }

    public void myIterator2() {
        Node temp = first;
        while (temp != null) {
            System.out.println(temp.item);
            temp = temp.next;
        }
    }


    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    Node temp;

    private class MyIterator implements Iterator<Item> {
        Node temp_first = first;

        public boolean hasNext() {
            return temp_first.next != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item = temp_first.item;
            temp_first = temp_first.next;
            return item;
        }

    }


    public static void main(String[] args) {
        RandomizedQueue_LL<String> myRandomizedQueue = new RandomizedQueue_LL<String>();

        myRandomizedQueue.enqueue("Hello");
        myRandomizedQueue.enqueue("hi");
        myRandomizedQueue.enqueue("Goodbye");
        myRandomizedQueue.enqueue("See you");
//        System.out.println("Random item: " + myRandomizedQueue.sample());
        myRandomizedQueue.dequeue();
        System.out.println("last: " + myRandomizedQueue.last.item);

        myRandomizedQueue.myIterator2();

    }
}
