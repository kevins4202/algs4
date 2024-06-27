import java.util.Iterator;
import java.util.NoSuchElementException;

//use linked list
public class Deque_L_List<Item> implements Iterable<Item> {
    private Node first, last = null;
    private int count = 0;
    //private Node oldlast, oldfirst = null;


    public Deque_L_List() {

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

    //add in front (stack)
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;

        //temp = first;

        count++;
        //after adding first item, if only 1 item, last = first

        if (count == 1) {
            last = first;
        }
    }

    public void addLast(Item item) {
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

            //temp = first;
            count++;

        }
    }

    //remove the first(queue)
    public Item removeFirst() {
        //is empty
        if (isEmpty()) {
            last = null;
            throw new NoSuchElementException();
        }
        // remove first element (queue)
        Item item = first.item;
        Node oldfirst = first;
        first = first.next;
        //clean up old first memory
        oldfirst = null;

        //temp = first;

        count--;

        return item;
    }

    //remove the last one (stack
    public Item removeLast() {
        //is empty
        if (isEmpty()) {
            last = null;
            throw new NoSuchElementException();
        }

        Item item = last.item;
        Node oldlast = last;
        Node temp = first;
        if (count >= 2) {
            for (int i = 0; i < count - 2; i++) {
                temp = temp.next;

            }
            last = temp;
            last.next = null;
        } else if (count == 1) {
            last = null;
            first = null;
        }
        //clean up
        oldlast = null;

        count--;
        return item;
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
        Deque_L_List<String> myDeque = new Deque_L_List<>();
        myDeque.addLast("Hello");
        myDeque.addFirst("Goodbye");
        myDeque.addFirst("hi");
//        myDeque.addFirst("u");
//
//        myDeque.removeLast();
        myDeque.removeFirst();
        //System.out.println(myDeque.last.item);
        System.out.println("size after remove:" + myDeque.size());
        System.out.println("Empty: " + myDeque.isEmpty());

        //        while (myDeque.iterator().hasNext()) {
//            System.out.println(myDeque.iterator().next());
//
//        }
        //System.out.println(myDeque.last.item);

        myDeque.myIterator2();


    }
}
