import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
//import edu.princeton.cs.algs4.Queue;

public class Solver {
    SearchNode myNode; 
    //Queue<Board> myQueue;
    public Solver(Board initial) { 
    // find a solution to the initial board (using the A* algorithm)
        if (initial.equals(null)) throw new java.lang.NullPointerException("Solver(): initial board is null");
        if (!(initial).isSolvable()) throw new java.lang.IllegalArgumentException("Solver(): board is not solvable"); 
        myNode = new SearchNode(initial, null, 0);
        SearchNode tmpNode;
        //SearchNode prevNode = new SearchNode(initial, 0);
        MinPQ<SearchNode> myPQ = new MinPQ<SearchNode>();
        //myQueue = new Queue<Board>();
        /*First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue. 
        Then, delete from the priority queue the search node with the minimum priority, and insert onto the priority queue all 
        neighboring search nodes (those that can be reached in one move from the dequeued search node). Repeat this procedure until 
        the search node dequeued corresponds to a goal board.
        The success of this approach hinges on the choice of priority function for a search node. We consider two priority 
        functions:*/
        //myQueue.enqueue(myNode.current);
        myPQ.insert(myNode);
        myNode = myPQ.delMin();
        while(!(myNode.current).isGoal()){
            Iterable<Board> neighbors = (myNode.current).neighbors();
            //myPQ = new MinPQ<SearchNode>();
            //while(!myPQ.isEmpty())
              //  myPQ.delMin();
            for (Board T: neighbors) {
                if (myNode.prev == null || !T.equals((myNode.prev).current)){
                    //if (myNode.prev != null)StdOut.println(myNode.prev.current);
                    tmpNode = new SearchNode(T, myNode, myNode.moves+1);
                    myPQ.insert(tmpNode);
                    if ((tmpNode.current).isGoal()) {
                        myNode = tmpNode;
                        return;
                    }
                        
                }
            }
            //prevNode = new SearchNode(myNode.current, 0);
            myNode = myPQ.delMin();
            //StdOut.println(myNode.current);
            //myQueue.enqueue(myNode.current);
        }
        while(!myPQ.isEmpty())
            myPQ.delMin();
        myPQ = null;
    }
    public int moves() {              
    // min number of moves to solve initial board
        return myNode.moves;
    }

    public Iterable<Board> solution() {
    // sequence of boards in a shortest solution
        Stack<Board> myStack = new Stack<Board>();
        myStack.push(myNode.current);
        while(myNode.prev != null) {
            myStack.push((myNode.prev).current);
            myNode = myNode.prev;
        }
        return myStack;
        //return myQueue;
    }

    private class SearchNode implements Comparable<SearchNode>{
        Board current;
        SearchNode prev;
        int moves;
        int distance;
        SearchNode (Board b, SearchNode p, int m) {
            current = b;
            prev = p;
            moves = m;
            distance = b.manhattan() + moves;
        }
        public int compareTo(SearchNode that) {
            if (this.distance > that.distance) return 1;
            if (this.distance < that.distance) return -1;
            return 0;
        }
    }

    public static void main(String[] args) { 
    // unit testing 
        int[] array = StdIn.readAllInts();
        int[][] matrix = new int[(int)Math.sqrt(array.length)][(int)Math.sqrt(array.length)];
        int k = 0;
        for (int i = 0; i < matrix[0].length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = array[k++];
        Board testBoard = new Board(matrix);
        Solver mySolver = new Solver(testBoard);
        StdOut.println("moves: " + mySolver.moves());
        StdOut.println("solution " + mySolver.solution());

    }
    
}
//To implement the A* algorithm, you must use the MinPQ data type from algs4.jar for the priority queue.

//Corner cases: The constructor should throw a java.lang.IllegalArgumentException if the initial board is not solvable and a 
//java.lang.NullPointerException if the initial board is null