import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import java.awt.Color;
import java.lang.Math;

public class SeamCarver {

   Picture myPicture;

   public SeamCarver(Picture picture) {                
   // create a seam carver object based on the given picture
      if (picture == null) throw new java.lang.NullPointerException();
      myPicture = picture; 
   }

   public Picture picture() {                         
   // current picture
      return myPicture;

   }

   public int width() {                           
   // width of current picture
      return myPicture.width();
   }

   public int height() {                         
   // height of current picture
      return myPicture.height();

   }

   public double energy(int x, int y) {              
   // energy of pixel at column x and row y
      if (x < 0 || y < 0 || x >= width() || y >= height()) throw new java.lang.IndexOutOfBoundsException();
      int temp = x;
      Color rGBLeft = myPicture.get((x + height() - 1)%height(), y);
      Color rGBRight = myPicture.get((x + 1)%height(), y);
      Color rGBDown = myPicture.get(x, (y + 1)%width());
      Color rGBUp = myPicture.get(x, (y + width() - 1)%width());

      int rLeft = rGBLeft.getRed();
      int gLeft = rGBLeft.getGreen();
      int bLeft = rGBLeft.getBlue();

      int rRight = rGBRight.getRed();
      int gRight = rGBRight.getGreen();
      int bRight = rGBRight.getBlue();

      int rUp = rGBUp.getRed();
      int gUp = rGBUp.getGreen();
      int bUp = rGBUp.getBlue();

      int rDown = rGBDown.getRed();
      int gDown = rGBDown.getGreen();
      int bDown = rGBDown.getBlue();

      int xGradient = (rUp - rDown)*(rUp - rDown) + (gUp - gDown)*(gUp - gDown) + (bUp - bDown)*(bUp - bDown);
      int yGradient = (rRight - rLeft)*(rRight - rLeft) + (gRight - gLeft)*(gRight - gLeft) + (bRight - bLeft)*(bRight - bLeft);

      double energy = Math.sqrt(xGradient + yGradient);
      return energy;

   }

   private int pairToNum (int x, int y) {
      int k = y*width() + x;
      return k;
   }

   private int[] numToPair (int k) {
      int [] pair = new int[2];
      pair[0] = k%width();
      pair[1] = k/width();
      return pair;
   }

   private  EdgeWeightedDigraph createGraph() {
      EdgeWeightedDigraph myGraph = new EdgeWeightedDigraph(width()*height() + 2);
      for (int j = 0; j < width(); j++) {
         for (int i = 1; i < height() - 1; i++) {
            int num = pairToNum(j, i);
            if (j-1 >= 0) {
               int left = pairToNum(j-1, i+1);
               DirectedEdge myDirLeft = new DirectedEdge(num, left, energy(j-1, i+1));
               myGraph.addEdge(myDirLeft);
            }
            if (j+1 < width()) {
               int right = pairToNum(j+1, i+1);
               DirectedEdge myDirRight = new DirectedEdge(num, right, energy(j+1, i+1));
               myGraph.addEdge(myDirRight);
            }
            int center = pairToNum(j, i+1);
            DirectedEdge myDirCenter = new DirectedEdge(num, center, energy(j, i+1));
            myGraph.addEdge(myDirCenter);
         }
      }
      //Nós dummy
      int dummyUp = width() * height();
      int dummyDown = width() * height() + 1;
      for (int j = 0; j < width(); j++) {
         int coord = pairToNum(j, 0);
         DirectedEdge dummyDir = new DirectedEdge(dummyUp, coord, energy(j, 0));
      }

      for (int j = 0; j < width(); j++) {
         int coord = pairToNum(j, height() - 1);
         DirectedEdge dummyDir = new DirectedEdge(coord, dummyDown, 0);
      }
      return myGraph;

   } 

   public int[] findHorizontalSeam() {             
   // sequence of indices for horizontal seam
    return findVerticalSeam();
   }

   public int[] findVerticalSeam() {               
   // sequence of indices for vertical seam
      EdgeWeightedDigraph myGraph = createGraph();
      DijkstraSP myDij = new DijkstraSP(myGraph, width()*height());
      int[] seam = new int[height()];
      int i = 0;
      for (DirectedEdge T: myDij.pathTo(width()*height()+1)) {
         if (T.to() == width()*height()+1)
            break;
         int coord = T.to();
         int [] pair = numToPair(coord);
         seam[i] = pair[0];
         i++;
      }
      return seam;

   }
//Throw a java.lang.IllegalArgumentException if either removeVerticalSeam() or removeHorizontalSeam() is called with an array of the wrong length or if the array is not 
//a valid seam (either an entry is outside the height/width bounds or two adjacent entries differ by more than 1).
//Throw a java.lang.IllegalArgumentException if either removeVerticalSeam() or removeHorizontalSeam() is called when the width or height of the current picture is 1, respectively.

   public void removeHorizontalSeam(int[] seam) {  
   // remove horizontal seam from current picture
      if (seam == null) throw new java.lang.NullPointerException();
      if (height() == 1) throw new java.lang.IllegalArgumentException(); 
      if (seam.length != width()) throw new java.lang.IllegalArgumentException();
      for (int i = 0; i < width(); i++){
         if (seam[i] < 0 || seam[i] >= width()) throw new java.lang.IllegalArgumentException();
         if (i != 0 && seam[i] - seam[i-1] > 1) throw new java.lang.IllegalArgumentException();
      }
   }

   public void removeVerticalSeam(int[] seam) {    
   // remove vertical seam from current picture
      if (seam == null) throw new java.lang.NullPointerException();
      if (width() == 1) throw new java.lang.IllegalArgumentException();
      if (seam.length != height()) throw new java.lang.IllegalArgumentException();
      for (int i = 0; i < height(); i++){
         if (seam[i] < 0 || seam[i] >= height()) throw new java.lang.IllegalArgumentException();
         if (i != 0 && seam[i] - seam[i-1] > 1) throw new java.lang.IllegalArgumentException();
      }
      Picture newPicture = new Picture(width() - 1, height());
      for (int j = 0; j < width(); j++) {
         for (int i = 0; i < height(); i++) {
            if (j >= seam[i])
               newPicture.set(j, i, myPicture.get(j + 1, i));
            else
               newPicture.set(j, i, myPicture.get(j, i));
         }
      }
      myPicture = newPicture;
   }

   public static void main(String[] args) {          
   // do unit testing of this class

   }
}
