public class arrTest {
    public static void main(String[] args) {
        int N = 10;
        int[] id = new int[N];
        for (int i = 0; i < N; i++) {
            // root of each node, size N
            id[i] = i;
            System.out.println(id[i]);
        }
    }
}
