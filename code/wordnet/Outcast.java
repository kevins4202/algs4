import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }        // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        if (nouns.length == 0) throw new IllegalArgumentException("empty array");
        String outcast = nouns[0];
        int dist = -1;

        for (int i = 0; i < nouns.length; i++) {
            int curr = 0;
            for (int j = 0; j < nouns.length; j++) {
                curr += wordNet.distance(nouns[i], nouns[j]);
            }

            if (curr > dist) outcast = nouns[i];
        }
        return outcast;
    }  // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
