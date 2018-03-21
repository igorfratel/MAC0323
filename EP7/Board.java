import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Board {
    int N;
    int[] myBoard;
    public Board(int[][] tiles) {         
    // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
        int k=0;
        myBoard = new int[tiles[0].length*tiles[0].length];
        for(int i = 0; i < tiles[0].length; i++) {
            for(int j = 0; j < tiles[0].length; j++)
                myBoard[k++] = tiles[i][j];
        }
        N = tiles[0].length;
    }

    public int tileAt(int i, int j) {        
    // return tile at row i, column j (or 0 if blank)
        if (i < 0 || i > N-1 || j < 0 || j > N-1) throw new java.lang.IndexOutOfBoundsException("tileAt: indexes are out of bound");
        return myBoard[i*N + j]; 
    }
    public int size() {                     
    // board size N
        return N;
    }
    public int hamming() {                  
    // number of tiles out of place
        int hamming = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (myBoard[i*N + j] != i*N + j + 1 && myBoard[i*N + j] != 0) hamming++;
        return hamming;
    }
    public int manhattan() { 
    // sum of Manhattan distances between tiles and goal
        int manhattan = 0;
        int flag;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if (myBoard[i*N + j] != i*N + j + 1) {
                    flag = 0;
                    for (int k = 0; k < N && flag == 0; k++){
                        for (int l = 0; l < N && flag == 0; l++){
                            if (myBoard[k*N + l] == i*N + j + 1){
                                flag = 1;
                                manhattan +=  java.lang.Math.abs(i - k) +  java.lang.Math.abs(j - l);
                            }
                        }
                    }
                }
            }
        }
        return manhattan;

    }
    public boolean isGoal() {               
    // is this board the goal board?
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (myBoard[i*N + j] != i*N + j + 1  && myBoard[i*N + j] != 0) return false;
        return true;
    }

    public boolean isSolvable() {            
    // is this board solvable?
        int count = 0, l;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = i; k < N; k++) {
                    if (k == i) {
                        l = j + 1;
                        if (l == N){
                            l = 0;
                            k++;
                            if (k == N)
                                break;
                        }
                    }
                    else l = 0;
                    for (; l < N; l++) {
                        if ((myBoard[k*N +l] < myBoard[i*N + j]) && myBoard[k*N + l] != 0) {
                            //StdOut.println("k = " + k + " l = " + l + "i = " + i + "j = " + j + " " + myBoard[k][l] + " < " + myBoard[i][j]);
                            count++;
                        }
                    }
                }
            }
        }
        if (N%2 != 0) {
            if  (count%2 != 0) return false;
            return true;
        }
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (myBoard[i*N + j]  == 0) count += i;
        if (count%2 == 0)
            return false;
        return true;
    }

    public boolean equals(Object y) {      
    // does this board equal y?
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (this.tileAt(i, j) != that.tileAt(i, j)) return false;
        return true;
    }

    private void changeValue(int i, int j,  int value) {
        myBoard[i*N + j] = value;

    }

    public Iterable<Board> neighbors() {     
    // all neighboring boards
        Stack<Board> myStack = new Stack<Board>();
        int i, j = 0, flag = 0;
        for (i = 0; i < N && flag == 0; i++)
            for (j = 0; j < N && flag == 0; j++)
                if (myBoard[i*N + j] == 0)
                    flag = 1;
        i--; j--;
        int[][] myMatrix = new int[N][N];
        for (int m = 0; m < N; m++)
            for (int n = 0; n < N; n++)
                myMatrix[m][n] = myBoard[m*N + n];
        if (i - 1 >= 0) {
            Board tmpBoard = new Board(myMatrix);
            tmpBoard.changeValue(i-1, j, 0);
            tmpBoard.changeValue(i, j, myMatrix[(i-1)][j]);
            myStack.push(tmpBoard);
            //tmpBoard.changeValue(i-1, j, myBoard[i-1][j]);
            //tmpBoard.changeValue(i, j, 0);
        }
        if (j - 1 >= 0) {
            Board tmpBoard2 = new Board(myMatrix);
            tmpBoard2.changeValue(i, j-1, 0);
            tmpBoard2.changeValue(i, j, myMatrix[i][j-1]);
            myStack.push(tmpBoard2);
            //tmpBoard.changeValue(i, j-1, myBoard[i][j-1]);
            //tmpBoard.changeValue(i, j, 0);
        }
        if (i + 1 < N) {
            Board tmpBoard3 = new Board(myMatrix);
            tmpBoard3.changeValue(i+1, j, 0);
            tmpBoard3.changeValue(i, j, myMatrix[i+1][j]);
            myStack.push(tmpBoard3);
            //tmpBoard.changeValue(i+1, j, myBoard[i+1][j]);
            //tmpBoard.changeValue(i, j, 0);
        }
        if (j + 1 < N) {
            Board tmpBoard4 = new Board(myMatrix);
            tmpBoard4.changeValue(i, j + 1, 0);
            tmpBoard4.changeValue(i, j, myMatrix[i][j+1]);
            myStack.push(tmpBoard4);
        }
        return myStack;
    }
    public String toString() {            
    // string representation of this board (in the output format specified below)
    //!!!MUDAR!!!   
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
            s.append(String.format("%2d ", tileAt(i, j)));
        }
        s.append("\n");
    }
    return s.toString();
    }

    public static void main(String[] args) {
    // unit testing (required)
        int[] array = StdIn.readAllInts();
        int[][] matrix = new int[(int)Math.sqrt(array.length)][(int)Math.sqrt(array.length)];
        int k = 0;
        for (int i = 0; i < matrix[0].length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = array[k++];
        Board testBoard = new Board(matrix);
        StdOut.println(testBoard);
        StdOut.println("tile at 1,1: " + testBoard.tileAt(1,1));
        StdOut.println("is goal: " + testBoard.isGoal());
        StdOut.println("manhattan: " + testBoard.manhattan());
        StdOut.println("hamming: " + testBoard.hamming());
        StdOut.println("is solvable: " + testBoard.isSolvable());
        StdOut.println("neighbors " + testBoard.neighbors());
        //neighbors

    }
}

//Corner cases: You may assume that the constructor receives an N-by-N array containing the N2 integers between 0 and N2 − 1, 
//where 0 represents the blank square. The tileAt() method should throw a java.lang.IndexOutOfBoundsException unless both i or j are 
//between 0 and N − 1.

//Performance requirements: Your implementation should support all Board methods in time proportional to N² (or better) in the worst
//case, with the exception that isSolvable() may take up to N4 in the worst case.