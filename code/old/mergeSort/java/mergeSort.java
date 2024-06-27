public class mergeSort {
    public static void main(String[] args) {
        double[] a = {1, 1, 1, 1, 1, 1};
        double[] aux = new double[a.length];

        int lo = 0;
        int mid = a.length/2-1;
        int hi = a.length-1;

        merge2(a, aux, lo, mid, hi);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "\t");
        }
    }

    private static void merge(double[] a, double[] aux, int lo, int mid, int hi) {
        //copy a to aux array
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int k = lo;
        int i = lo;
        int j = mid + 1;

        while ((i <= mid) || (j <= hi)) {
            if (i > mid) {
                a[k] = aux[j];
                j++;
            } else if (j > hi) {
                a[k] = aux[i];
                i++;
            } else if (aux[i] <= aux[j]) {
                a[k] = aux[i];
                i++;
            } else {
                a[k] = aux[j];
                j++;
            }
            k++;
        }
    }

    private static void merge2(double[] a, double[] aux, int lo, int mid, int hi) {
        //copy a to aux array
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j];
                j++;
            } else if (j > hi) {
                a[k] = aux[i];
                i++;
            } else if (aux[i] <= aux[j]) {
                a[k] = aux[i];
                i++;
            } else {
                a[k] = aux[j];
                j++;
            }
        }
    }
}
