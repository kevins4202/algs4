

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.ST;


public class SeamCarver {
    private Picture pic;
    private int width, height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        pic = picture;
        
        width = pic.width();
        height = pic.height();
    }

    private int toIndex(int w,int h){
        return h*pic.width() + w;
    }

    private int[] toCoord(int index){
        int[] coord = new int[2];
        // horizontal, vertical
        coord[0] = index % width;
        coord[1] = index / width;
        return coord;
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if(x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }

        if(x == 0 || x == width() - 1 || y == 0 || y == height() - 1) return 1000;

        double dx = (pic.get(x-1, y).getRed() - pic.get(x+1,y).getRed())*(pic.get(x-1, y).getRed() - pic.get(x+1,y).getRed()) + (pic.get(x-1, y).getBlue() - pic.get(x+1,y).getBlue()) * (pic.get(x-1, y).getBlue() - pic.get(x+1,y).getBlue()) + (pic.get(x-1, y).getGreen() - pic.get(x+1,y).getGreen()) * (pic.get(x-1, y).getGreen() - pic.get(x+1,y).getGreen());

        double dy = (pic.get(x, y-1).getRed() - pic.get(x,y+1).getRed())*(pic.get(x, y-1).getRed() - pic.get(x,y+1).getRed()) + (pic.get(x, y-1).getBlue() - pic.get(x,y+1).getBlue()) * (pic.get(x, y-1).getBlue() - pic.get(x,y+1).getBlue()) + (pic.get(x, y-1).getGreen() - pic.get(x,y+1).getGreen()) * (pic.get(x, y-1).getGreen() - pic.get(x,y+1).getGreen());

        return Math.sqrt(dx + dy);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        ST<Integer, Integer> edgeTo = new ST<Integer, Integer>();
        ST<Integer, Double> engTo = new ST<Integer, Double>();

        double best = Double.MAX_VALUE;
        int last = -1;

        for(int col = 0; col < width-1; col++){
            for(int row = 0; row < height; row++){
                int curr = toIndex(col, row);

                if(col == 0){
                    engTo.put(curr, energy(col, row));
                    edgeTo.put(curr, -1);
                }

                for(int i = row-1; i<= row+1; i++){
                    if(i >=0 && i < height){
                        int next = toIndex(col+1, i);
                        double newEng = engTo.get(curr) + energy(col+1, i);
                        if(!engTo.contains(next) || engTo.get(next) > newEng){
                            engTo.put(next, newEng);
                            edgeTo.put(next, curr);
                        }

                        if(col == width-2 && newEng < best){
                            best = newEng;
                            last = next;
                        }
                    }
                }

            }
        }
        
        int[] seam = new int[width];

        int tmp = last;
        seam[width-1] = toCoord(tmp)[1];
        for(int i = 1; i < width; i++){
            // System.out.println(tmp);
            seam[width-1-i] = toCoord(edgeTo.get(tmp))[1];
            tmp = edgeTo.get(tmp);
        }

        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        ST<Integer, Integer> edgeTo = new ST<Integer, Integer>();
        ST<Integer, Double> engTo = new ST<Integer, Double>();

        double best = Double.MAX_VALUE;
        int last = -1;

        for(int row = 0; row < height-1; row++){
            for(int col = 0; col < width; col++){
                int curr = toIndex(col, row);

                if(row == 0){
                    engTo.put(curr, energy(col, row));
                    edgeTo.put(curr, -1);
                }

                for(int i = col-1; i<= col+1; i++){
                    if(i >=0 && i < width){
                        int next = toIndex(i, row+1);
                        double newEng = engTo.get(curr) + energy(i, row+1);
                        if(!engTo.contains(next) || engTo.get(next) > newEng){
                            engTo.put(next, newEng);
                            edgeTo.put(next, curr);
                        }

                        if(row == height-2 && newEng < best){
                            best = newEng;
                            last = next;
                        }
                    }
                }

            }
        }
        
        int[] seam = new int[height];

        int tmp = last;
        seam[height-1] = toCoord(tmp)[0];
        for(int i = 1; i < height; i++){
            // System.out.println(tmp);
            seam[height-1-i] = toCoord(edgeTo.get(tmp))[0];
            tmp = edgeTo.get(tmp);
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if(seam == null) throw new IllegalArgumentException("seam is null");

        if(height <=1) throw new IllegalArgumentException();

        if(seam[0] < 0) throw new IllegalArgumentException();

        for(int i = 1; i < seam.length; i++){
            if(Math.abs(seam[i] - seam[i-1]) >1 || seam[i] >= height || seam[i] <0) throw new IllegalArgumentException();
        }

        Picture newPic = new Picture(width, height-1);

        for(int col = 0; col < width; col++){
            for(int row = 0; row < height; row++){
                if(row == seam[col]) row++;
                pic.set(col, row, pic.get(col, row));
            }
        }

        pic = newPic;
        height--;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if(seam == null) throw new IllegalArgumentException("seam is null");

        if(width <=1) throw new IllegalArgumentException();

        if(seam[0] < 0) throw new IllegalArgumentException();

        for(int i = 1; i < seam.length; i++){
            if(Math.abs(seam[i] - seam[i-1]) >1 || seam[i] >= width || seam[i] <0) throw new IllegalArgumentException();
        }

        Picture newPic = new Picture(width-1, height);
        
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                
                if(col == seam[row]) row++;
                pic.set(col, row, pic.get(col, row));
            }
        }

        pic = newPic;
        width--;
    }

    public static void main(String[] args) {
        Picture pic = new Picture("./"+args[0]);
        SeamCarver sc = new SeamCarver(pic);
        // System.out.println(sc.energy(1,1));
        int[] seam = sc.findVerticalSeam();
        for(int i = 0; i < seam.length; i++){
            System.out.print(seam[i] + " ");
        }

        System.out.println();
    }
}
