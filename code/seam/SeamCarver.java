import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;
    private boolean vertical;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("null arg");

        this.picture = new Picture(picture);
        // width = picture.width();
        // height = picture.height();

        vertical = true;

        energy = new double[height()][width()];
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                energy[j][i] = energy(i, j);
    }

    private int index(int x, int y) {
        return y * width() + x;
    }

    private int getX(int index) {
        return index % width();
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture().width();
    }

    // height of current picture
    public int height() {
        return picture().height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new IllegalArgumentException("points out of range");

        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
            return 1000;

        // int left = picture.getRGB(x - 1, y);
        // int right = picture.getRGB(x + 1, y);
        // int up = picture.getRGB(x, y - 1);
        // int down = picture.getRGB(x, y + 1);
        //
        // double dx = Math.pow(getR(left) - getR(right), 2) + Math.pow(getG(left) - getG(right), 2)
        //         + Math.pow(getB(left) - getB(right), 2);
        // double dy = Math.pow(getR(up) - getR(down), 2) + Math.pow(getG(up) - getG(down), 2)
        //         + Math.pow(getB(up) - getB(down), 2);

        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);
        Color up = picture.get(x, y - 1);
        Color down = picture.get(x, y + 1);

        double dx = Math.pow(left.getRed() - right.getRed(), 2) + Math.pow(
                left.getGreen() - right.getGreen(), 2)
                + Math.pow(left.getBlue() - right.getBlue(), 2);
        double dy = Math.pow(up.getRed() - down.getRed(), 2) + Math.pow(
                up.getGreen() - down.getGreen(), 2)
                + Math.pow(up.getBlue() - down.getBlue(), 2);

        return Math.sqrt(dx + dy);
    }

    // private int getR(int rgb) {
    //     return (rgb % (16 * 16 * 16 * 16 * 16 * 16)) / (16 * 16 * 16 * 16);
    // }
    //
    // private int getG(int rgb) {
    //     return (rgb % (16 * 16 * 16 * 16)) / (16 * 16);
    // }
    //
    // private int getB(int rgb) {
    //     return rgb % (16 * 16);
    // }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (vertical) {
            transpose();
            vertical = false;
        }

        int[] seam = findVerticalSeam();
        transpose();
        vertical = true;
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[][] edgeTo = new int[height()][width()];
        double[][] distTo = new double[height()][width()];

        Arrays.fill(distTo[0], 1000);
        for (int i = 1; i < height(); i++)
            Arrays.fill(distTo[i], Double.POSITIVE_INFINITY);

        for (int i = 1; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                for (int k = -1; k <= 1; k++) {
                    if (j + k < 0 || j + k >= width()) continue;

                    if (distTo[i - 1][j + k] + energy[i][j] < distTo[i][j]) {
                        distTo[i][j] = distTo[i - 1][j + k] + energy[i][j];
                        edgeTo[i][j] = index(j + k, i - 1);
                    }
                }
            }
        }

        double minSeam = Double.POSITIVE_INFINITY;
        int end = -1;

        for (int i = 0; i < width(); i++) {
            if (distTo[height() - 1][i] < minSeam) {
                end = i;
                minSeam = distTo[height() - 1][i];
            }
        }

        int[] seam = new int[height()];
        seam[height() - 1] = end;

        for (int i = height() - 2; i >= 0; i--) {
            int prevX = seam[i + 1];
            int from = edgeTo[i + 1][prevX];
            seam[i] = getX(from);
        }

        // for (double[] row : distTo) {
        //     for (double d : row) StdOut.print(d + "\t");
        //     StdOut.println();
        // }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("null arg");
        if (seam.length != width()) throw new IllegalArgumentException("wrong length");
        if (height() <= 1) throw new IllegalArgumentException("picture 1 high");
        for (int i = 1; i < seam.length; i++)
            if (Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("difference more than 1");

        if (vertical) {
            transpose();
            vertical = false;
        }
        removeVerticalSeam(seam);

        transpose();
        vertical = true;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("null arg");
        if (seam.length != height()) throw new IllegalArgumentException("wrong length");
        if (width() <= 1) throw new IllegalArgumentException("picture 1 wide");
        for (int i = 1; i < seam.length; i++)
            if (Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("difference more than 1");

        Picture newPicture = new Picture(width() - 1, height());
        int h = height();
        int w = width();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < seam[i]; j++) {
                newPicture.setRGB(j, i, picture.getRGB(j, i));
            }

            for (int j = seam[i] + 1; j < w; j++) {
                newPicture.setRGB(j - 1, i, picture.getRGB(j, i));
            }
        }

        picture = newPicture;

        double[][] newEnergy = new double[h][w - 1];

        for (int i = 0; i < h; i++) {
            System.arraycopy(energy[i], 0, newEnergy[i], 0, seam[i]);
            System.arraycopy(energy[i], seam[i] + 1, newEnergy[i], seam[i], w - seam[i] - 1);
        }

        for (int i = 0; i < h; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = seam[i] + j;
                if (x < 0 || x >= width()) continue;

                newEnergy[i][x] = energy(x, i);
            }
        }

        energy = newEnergy;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture pic = new Picture("3x4.png");

        SeamCarver sc = new SeamCarver(pic);
        StdOut.println(sc.energy(0, 1));
    }

    private void transpose() {
        double[][] newEnergy = new double[energy[0].length][energy.length];

        for (int i = 0; i < energy.length; i++) {
            for (int j = 0; j < energy[0].length; j++) {
                newEnergy[j][i] = energy[i][j];
            }
        }

        energy = newEnergy;

        Picture newPicture = new Picture(height(), width());

        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                newPicture.set(j, i, picture.get(i, j));
            }
        }

        picture = newPicture;
    }

}
