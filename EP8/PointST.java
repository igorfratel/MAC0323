import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MaxPQ;
import java.util.Iterator;
import java.util.Comparator;

public class PointST<Value> {
   RedBlackBST<Point2D,Value> table;
   public PointST() {
   // construct an empty symbol table of points
      table = new RedBlackBST<Point2D,Value>();

   }

   public boolean isEmpty() {                     
   // is the symbol table empty? 
      return table.isEmpty();
   }

   public int size() {                        
    // number of points 
      return table.size();
   }

   public void put(Point2D p, Value val) {     
   // associate the value val with point p
      if (p == null || val == null) throw new java.lang.NullPointerException("put(): at least one argument is null");
      table.put(p, val);
   }

   public Value get(Point2D p) {                
   // value associated with point p 
      if (p == null) throw new java.lang.NullPointerException("get(): argument is null");
      return table.get(p);

   }

   public boolean contains(Point2D p) {           
   // does the symbol table contain point p?
      if (p == null) throw new java.lang.NullPointerException("contains(): argument is null");
      return table.contains(p);
   }

   public Iterable<Point2D> points() {                      
   // all points in the symbol table 
      return table.keys();

   }

   public Iterable<Point2D> range(RectHV rect) {            
   // all points that are inside the rectangle
      if (rect == null) throw new java.lang.NullPointerException("range(): argument is null");
      Stack<Point2D> myStack = new Stack<Point2D>();
      for (Point2D P: points()) {
         if(rect.contains(P))
            myStack.push(P);
      }
      return myStack;
   }

   public Iterable<Point2D> nearest(Point2D p, int k) {            
   // a nearest neighbor to point p; null if the symbol table is empty 
      if (p == null) throw new java.lang.NullPointerException("nearest(): argument is null");
      double distance = -1;
      MaxPQ<Point2D> myPQ = new MaxPQ<Point2D>(new ComparePoints(p));
      for (Point2D P: points())
         myPQ.insert(P);
      for (int i = myPQ.size() - k; i > 0; i--)
         myPQ.delMax();
      return myPQ;
   }

   private class ComparePoints implements Comparator<Point2D> {
      Point2D q;
      public ComparePoints(Point2D p) {
         q = p;
      }
      public int compare(Point2D r, Point2D s) {
         double d1 = r.distanceSquaredTo(q);
         double d2 = s.distanceSquaredTo(q);
         if (d1 < d2) return -1; 
         if (d1 > d2) return 1;
         return 0;
      }
   }

   public Point2D nearest(Point2D p) {            
   // a nearest neighbor to point p; null if the symbol table is empty 
      if (p == null) throw new java.lang.NullPointerException("nearest(): argument is null");
      double distance = -1;
      Point2D nearPoint = null;
      for (Point2D P: points()){
         if (distance == -1) {
            distance =  p.distanceSquaredTo(P);
            nearPoint = P;
         }
         else{ 
            if (p.distanceSquaredTo(P) < distance)
               distance = p.distanceSquaredTo(P);
               nearPoint = P;
         }
      }
      return nearPoint;
   }

   public static void main(String[] args) {                
   // unit testing (required) 

   }

}
//Your implementation should support size() in constant time; it should support put(), get() and contains() in time proportional 
//to the logarithm of the number of points in the set in the worst case; and it should support points(), nearest(), and range() in time 
//proportional to the number of points in the symbol table.