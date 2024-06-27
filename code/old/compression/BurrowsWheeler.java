import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform(){
        String input = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(input);

        int index = -1;

        for(int i = 0; i < input.length(); i++){
            if(csa.index(i) == 0){
                index = i;
                break;
            }
        }

        BinaryStdOut.write(index);

        for(int i = 0; i < input.length(); i++){
            BinaryStdOut.write(input.charAt((input.length() - 1 + csa.index(i)) % input.length()));
        }

        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform(){
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int[] count = new int[R + 1];
        int[] next = new int[s.length()];
        for (int i = 0; i < s.length(); i++)
        count[s.charAt(i) + 1]++;
        for (int i = 1; i <= R; i++)
            count[i] += count[i - 1];
        
        for (int i = 0; i < s.length(); i++)
            next[count[s.charAt(i)]++] = i;

        for (int curr = next[first], i = 0; i < s.length(); curr = next[curr], i++)
            BinaryStdOut.write(s.charAt(curr));
        BinaryStdOut.close();
        }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args){
        if(args[0].equals("-")) transform();
        else if(args[0].equals("+")) inverseTransform();
    }

}