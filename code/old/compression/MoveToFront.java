import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.ArrayList;

public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode(){
        ArrayList<Character> sequence = new ArrayList<>();
        
        for(int i = 0; i < R; i++){
            sequence.add((char) i);
        }

        while(!BinaryStdIn.isEmpty()){
            char c = BinaryStdIn.readChar();

            char currIndex = (char) sequence.indexOf(c);

            BinaryStdOut.write(currIndex);

            sequence.remove(currIndex);
            sequence.add(0,c);
        }

        BinaryStdOut.flush();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode(){
        ArrayList<Character> sequence = new ArrayList<>();
        
        for(int i = 0; i < R; i++){
            sequence.add((char) i);
        }

        while(!BinaryStdIn.isEmpty()){
            int c = (int) BinaryStdIn.readChar();

            // BinaryStdOut.write(c);

            BinaryStdOut.write(sequence.get(c));

            char tmp = sequence.remove(c);
            sequence.add(0, tmp);
        }

        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args){
        if(args[0].equals("-")) encode();
        else if(args[0].equals("+")) decode();
    }

}