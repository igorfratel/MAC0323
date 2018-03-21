import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

public class KdTreeST<Value> {
   2DBST<Point2D,Value> table;
   public KdTreeST() {
   // construct an empty symbol table of points
      table = new 2DBST<Point2D,Value>();

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
      for (Point2d P: table) {
         if(rect.contains(P))
            myStack.push(P);
      }
      return myStack;
      
   }

   public Point2D nearest(Point2D p) {            
   // a nearest neighbor to point p; null if the symbol table is empty 
      if (p == null) throw new java.lang.NullPointerException("nearest(): argument is null");

   }

   private class 2DBST<Key extends Comparable<Key>, Value> {
      private Node root;

      private class Node {
         private Key key;          
         private Value val;
         private Node left, right;  
         private int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
      }

      public BST() {
      
      }

      public int size() {
         return size(root);
      }

      public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("put(): argument is null");
        root = put(root, key, val, 0);
      }

      private Node put(Node x, Key key, Value val, boolean orient) {
         if (x == null) return new Node(key, val, 1);
         if (orient == 0)
            int cmp = (key.x()).compareTo((x.key).x());
         else
            int cmp = (key.y()).compareTo((x.key).y());
         if (cmp < 0) x.left  = put(x.left,  key, val, !orient);
         else if (cmp > 0) x.right = put(x.right, key, val, !orient);
         else x.val   = val;
         x.size = 1 + size(x.left) + size(x.right);
         return x;
      }

      public void delete(Key key) {
         if (key == null) throw new IllegalArgumentException("called delete() with a null key");
         root = delete(root, key);
         assert check();
       }

      private Node delete(Node x, Key key) {
         if (x == null) return null;
         int cmp = key.compareTo(x.key);
         if      (cmp < 0) x.left  = delete(x.left,  key);
         else if (cmp > 0) x.right = delete(x.right, key);
         else { 
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
         } 
         x.size = size(x.left) + size(x.right) + 1;
         return x;
       } 

      public boolean contains(Key key) {
         if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
      }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
      public Value get(Key key) {
        return get(root, key, 0);
      }

      private Value get(Node x, Key key, boolean orient) {
         if (key == null) throw new IllegalArgumentException("called get() with a null key");
         if (x == null) return null;
         int cmp = key.compareTo(x.key);
         if      (cmp < 0) return get(x.left, key);
         else if (cmp > 0) return get(x.right, key);
         else return x.val;
      }

      public boolean isEmpty() {
         return size() == 0;
      }

      public Iterable<Key> keys() {


      }

   }

   public static void main(String[] args) {                
   // unit testing (required) 

   }

}
//Your implementation should support size() in constant time; it should support put(), get() and contains() in time proportional 
//to the logarithm of the number of points in the set in the worst case; and it should support points(), nearest(), and range() in time 
//proportional to the number of points in the symbol table.