import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class TrieSet implements Iterable<String> {
    private static final int R = 26; // A-Z
    private Node root; // root of trie
    private int n; // number of keys in trie

    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    public TrieSet() {
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null)
            return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        int c = ((int) key.charAt(d))-65;
        return get(x.next[c], key, d + 1);
    }

    public void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString)
                n++;
            x.isString = true;
        } else {
            int c = ((int) key.charAt(d)) - 65;

            x.next[c] = add(x.next[c], key, d + 1);
        }

        return x;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Iterator<String> iterator() {
        return keysWithPrefix("").iterator();
    }


    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        
        return results;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, StringBuilder prefix, Queue<String> result) {
        if (x == null) return;

        if (x.isString) result.enqueue(prefix.toString());
        for (int c = 0; c < R; c++) {
            prefix.append((char) (c+65));
            collect(x.next[c], prefix, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    // all keys in trie that match pattern, where . symbol is wildcard
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        StringBuilder prefix = new StringBuilder();
        collect(root, prefix, pattern, results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null)
            return;
        int d = prefix.length();

        if (d == prefix.length() && x.isString) {
            results.enqueue(prefix.toString());
        }

        if (d == pattern.length()) {
            return;
        }

        char c = pattern.charAt(d);

        if (c == '.') {
            for (int ch = 0; ch < R; ch++) {
                prefix.append((char) (ch+65));
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        } else {
            prefix.append(c);
            collect(x.next[((int) c) - 65], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
    
    // Returns the string in the set that is the longest prefix of query, or null, if no such string.
    public String longestPrefixOf(String query){
        if(query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");

        int length = longestPrefixOf(root, query, 0, -1);

        if(length == -1) return null;
        else return query.substring(0, length);
    }

    private int longestPrefixOf(Node x, String query, int d, int length){
        if(x == null) return length;
        if(x.isString) length = d;
        if(d == query.length()) return length;

        int c = ((int) query.charAt(d)) - 65;

        return longestPrefixOf(x.next[c], query, d+1, length);
    }

    public void delete(String key){
        if(key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    public Node delete(Node x, String key, int d){
        if (x == null) return null;

        if (d == key.length()) {
            if (x.isString) n--;
            x.isString = false;
        } else {
            int c = ((int) key.charAt(d)) - 65;
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        if (x.isString) return x;
        for (int c = 0; c < R; c++) {
            if (x.next[c] != null) return x;
        }
        return null;
    }

    public static void main(String[] args) {
        TrieSet trie = new TrieSet();

        // trie.add("YOU");
        // trie.add("HELL");
        // trie.add("HE");
        // trie.add("HELLS");
        // trie.add("HELLISH");
        // trie.add("HELLSHELL");

        In in = new In(args[0]);// args[0] is the dictionary file
        String[] dictionary = in.readAllStrings();

        for(String s : dictionary) trie.add(s);

        System.out.println(trie.keysWithPrefix("YO").iterator().next());
        System.out.println(trie.keysWithPrefix("H").iterator().hasNext());

        // System.out.println(trie.contains("HELLSHELL"));
        // Iterator<String> it = trie.iterator();
        // while(it.hasNext()){
        //     System.out.println(it.next());
        // }
    }
}
