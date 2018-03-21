import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;
import java.util.Comparator;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.MaxPQ;

public class KdTreeST<Value> {
    DBST table;
    int N;
    public KdTreeST() {
    // construct an empty symbol table of points
      table = null;
      N = 0;
    }

    public boolean isEmpty() {                     
    // is the symbol table empty? 
        return N == 0;
    }

    public int size() {                        
    // number of points 
        return N;
    }

    public void put(Point2D p, Value val) {     
    // associate the value val with point p
        if (p == null || val == null) throw new java.lang.NullPointerException("put(): at least one argument is null");
        table = put(table, p, val, false, null);
        N++;
    }

    private DBST put(DBST tree, Point2D key, Value val, boolean orient, DBST dad) {
        double cmp;
        if (tree == null) {
            RectHV rect = null;
            double xmin = 0, ymin = 0, xmax = 0, ymax = 0;
            if (dad != null) {
                if (orient == false) {
                    if (key.y() < dad.key.y()) {
                        xmin = dad.rect.xmin();
                        ymin =dad.rect.ymin();
                        xmax= dad.rect.xmax();
                        ymax= dad.key.y();
                        rect = new RectHV(xmin, ymin, xmax, ymax);
                    }

                    else if (key.y() > tree.key.y()) {
                        xmin = dad.rect.xmin();
                        ymin = dad.key.y();
                        xmax= dad.rect.xmax();
                        ymax= dad.rect.ymax();
                        rect = new RectHV(xmin, ymin, xmax, ymax);
                    }
                }
                else {
                    if (key.x() < tree.key.x()) {
                        xmin = dad.rect.xmin();
                        ymin = dad.rect.ymin();
                        xmax= dad.key.x();
                        ymax= dad.rect.ymax();
                        rect = new RectHV(xmin, ymin, xmax, ymax);
                    }

                    else if (key.x() > tree.key.x()) {
                        xmin = dad.key.x();
                        ymin = dad.rect.ymin();
                        xmax= dad.rect.xmax();
                        ymax=dad.rect.ymax();
                        rect = new RectHV(xmin, ymin, xmax, ymax);
                    }
                }
            }
            else 
                rect = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            return new DBST(key, val, rect);
        }
        if (orient == false){
           cmp = key.x() - tree.key.x();
        }
        else{
            cmp = key.y() - tree.key.y();
        }
        if (cmp < 0){ tree.left = put(tree.left,  key, val, !orient, tree);}
        else if (cmp > 0){ tree.right = put(tree.right, key, val, !orient, tree);}
        else {tree.val = val;}
        return tree;
    }

    public Value get(Point2D p) {                
    // value associated with point p 
        if (p == null) throw new java.lang.NullPointerException("get(): argument is null");
            return get(table, p);
   }

   private Value get(DBST tree, Point2D key) {
            if (tree == null) return null;
            int cmp = key.compareTo(tree.key);
            if (cmp < 0) return get(tree.left, key);
            else if (cmp > 0) return get(tree.right, key);
            else return tree.val;
    }

    public boolean contains(Point2D p) {           
    // does the symbol table contain point p?
        if (p == null) throw new java.lang.NullPointerException("contains(): argument is null");
        return get(p) != null;
    }

    public Iterable<Point2D> points() {                      
    // all points in the symbol table 
        Stack<Point2D> myStack = new Stack<Point2D>();
        return points(table, myStack);
    }
    private Stack<Point2D> points(DBST tree, Stack<Point2D> myStack) {
        if (tree == null) return myStack;
        myStack = points(tree.left, myStack);
        myStack = points(tree.right, myStack);
        myStack.push(tree.key);
        return myStack;
    }
    public Iterable<Point2D> range(RectHV rect) {            
    // all points that are inside the rectangle
        if (rect == null) throw new java.lang.NullPointerException("range(): argument is null");
        Stack<Point2D> myStack = new Stack<Point2D>();
        return range(table, rect, myStack);
   }
    private Stack<Point2D> range(DBST tree, RectHV rect, Stack<Point2D> myStack) {
        if (tree == null) return myStack;
        if (!tree.rect.intersects(rect)) return myStack;
        if (rect.contains(tree.key))
        myStack = range(tree.left, rect, myStack);
        myStack = range(tree.right, rect, myStack);
        myStack.push(tree.key);
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

    private class DBST {
        private Point2D key;          
        private Value val;
        private RectHV rect;
        private DBST left, right;  

        public DBST(Point2D key, Value val, RectHV rect) {
            this.key = key;
            this.val = val;
            this.rect = rect;
        }
    }     
    public static void main(String[] args){

    }

}


       

      
   

//Your implementation should support size() in constant time; it should support put(), get() and contains() in time proportional 
//to the logarithm of the number of points in the set in the worst case; and it should support points(), nearest(), and range() in time 
//proportional to the number of points in the symbol table.