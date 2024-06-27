public class LSDRadix{
    public static void sort(String a[], int w){
        int R = 256;

        int N = a.length;

        String aux[] = new String[N];

        //right to left

        for(int d = w-1; d >=0; d--){
            int count[] = new int[R+1];

            for(int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;
            
            for(int r = 0; r < R; r++)
                count[r+1] += count[r];
            
            for(int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            for(int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    public static void main(String[] args){
        String a[] = {"4PGC938", "2IYE230", "3CIO720", "1ICK750", "1OHV845", "4JZY524", "1ICK750", "3CIO720", "1OHV845", "1OHV845", "2RLA629", "2RLA629", "3ATW723"};

        sort(a, 7);

        for(int i = 0; i < a.length; i++)
            System.out.println(a[i]);
    }
}