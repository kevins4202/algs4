public class RunPercolation_v1 {
    public static void main(String[] args) {
        //int N = 3;
        int N = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Percolation myPerc = new Percolation(N);
        PercolationStats myPercStats = new PercolationStats(N, trials);
        double percentage = myPerc.numberOfOpenSites() / (N * N);

    }


}

//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                System.out.print(myPerc.arr[i][j]);
//            }
//            System.out.println();
//
//        }
//        myUF = myPerc.open(myPerc.getNumber(0, 1, N), myUF);
//
//
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                System.out.print(myPerc.arr[i][j]);
//            }
//            System.out.println();
//        }
//
//        System.out.println(myPerc.percolates(myUF));
//
//        for (int i = 0; i < myUF.id.length; i++) {
//            System.out.print(myUF.id[i]);
//        }
//        for (int i = 0; i < myUF.id.length; i++) {
//            System.out.print(myUF.sz[i]);
//        }
//        System.out.println(myPerc.count);
