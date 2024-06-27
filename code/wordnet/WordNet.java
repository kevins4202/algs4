import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private final SET<String> allWords;
    private final HashMap<String, Bag<Integer>> nounToInt;
    private final HashMap<Integer, String> intToSynset;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("arguments null");
        allWords = new SET<>();
        nounToInt = new HashMap<>();
        intToSynset = new HashMap<>();

        In in = new In(synsets);

        while (!in.isEmpty()) {
            String input = in.readLine();
            String[] s = input.split(",");

            int id = Integer.parseInt(s[0]);
            intToSynset.put(id, s[1]);
            String[] words = s[1].split(" ");

            for (String word : words) {
                if (!nounToInt.containsKey(word))
                    nounToInt.put(word, new Bag<>());

                nounToInt.get(word).add(id);
                allWords.add(word);
            }
        }

        In in1 = new In(hypernyms);
        ArrayList<Bag<Integer>> hyps = new ArrayList<>();

        while (!in1.isEmpty()) {
            String input = in1.readLine();

            String[] s = input.split(",");
            Bag<Integer> bag = new Bag<>();

            // int id = Integer.parseInt(s[0]);
            for (int i = 1; i < s.length; i++) {
                int hypernym = Integer.parseInt(s[i]);
                bag.add(hypernym);
            }

            hyps.add(bag);
        }

        Digraph digraph = new Digraph(hyps.size());
        for (int i = 0; i < hyps.size(); i++)
            for (int x : hyps.get(i))
                digraph.addEdge(i, x);


        Topological topological = new Topological(digraph);
        if (!topological.hasOrder()) throw new IllegalArgumentException("not a DAG");
        sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return allWords;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("not a word");
        return allWords.contains(word);
        // return nounToInt.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("invalid word 1");

        return sap.length(nounToInt.get(nounA), nounToInt.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("invalid word 2");

        return intToSynset.get(sap.ancestor(nounToInt.get(nounA), nounToInt.get(nounB)));
    }

    public static void main(String[] args) {
        // WordNet wordnet = new WordNet(args[0], args[1]);
        //
        // int cnt = 0;
        // for (String word : wordnet.nouns()) {
        //     cnt++;
        // }
        // StdOut.println(cnt);
        // StdOut.println(wordnet.sap("municipality", "region"));
    }

}
