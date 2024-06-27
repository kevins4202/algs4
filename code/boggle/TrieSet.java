/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class TrieSet implements Iterable<String> {
    private static final int R = 26;
    private Node root = new Node();

    private static class Node {
        private boolean isString;
        private Node[] next = new Node[R];
    }

    public void add(String key) {
        if (key == null) throw new IllegalArgumentException("null arg");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.isString = true;
        }
        else {
            int c = key.charAt(d) - 'A';
            x.next[c] = add(x.next[c], key, d + 1);
        }

        return x;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("null arg");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;

        int c = key.charAt(d) - 'A';

        return get(x.next[c], key, d + 1);
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> result = new Queue<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), result);

        return result;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> result) {
        if (x == null) return;
        if (x.isString) result.enqueue(prefix.toString());

        for (int c = 0; c < R; c++) {
            prefix.append((char) (c + 'A'));
            collect(x.next[c], prefix, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public Iterator<String> iterator() {
        return keysWithPrefix("").iterator();
    }
}
