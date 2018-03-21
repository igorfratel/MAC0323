import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

	public static void main(String[] args)  {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> queue = new RandomizedQueue(k);
		while (!StdIn.isEmpty())
			queue.enqueue(StdIn.readString());
		for (int i = 0; i < k; i++)
			StdOut.println(queue.dequeue());
   }
}

//Performance requirements:  the running time of Subset must be linear in the size of the input. 
//You may use only a constant amount of memory plus either one Deque or RandomizedQueue object of maximum size at most N. 
//(For an extra challenge, limit the maximum size to k.)