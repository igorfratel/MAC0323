import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
//A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items 
//in the data structure.
//Create a generic data type RandomizedQueue that implements the following API:
    private Item queue[];
    int n;

    public RandomizedQueue() {                 
    // construct an empty randomized queue
        this(1);
    }
    
    public RandomizedQueue(int N) {                 
    // construct an empty randomized queue
        queue = (Item[]) new Object[N];
    }
    public boolean isEmpty() {               
    // is the queue empty?
        return n == 0;
    }

    public int size() {                     
    // return the number of items on the queue
        return n;
    }

    public void enqueue(Item item) {         
    // add the item
        if (item == null) throw new java.lang.NullPointerException("enqueue(): argument item is null");
        if (n == queue.length)
            resize(2*queue.length);
        queue[n++] = item;
    }

    private void resize(int size) {
        Item newQueue[] = (Item[]) new Object[size];
        for (int i = 0; i < n; i++)
            newQueue[i] = queue[i];
        queue = newQueue;
    }

    public Item dequeue() {                  
    // remove and return a random item
        if (isEmpty()) throw new java.util.NoSuchElementException("dequeue(): randomized queue is empty");
        int randNum = StdRandom.uniform(n);
        Item tmpItem = queue[randNum];
        if (n <= queue.length/4)
            resize(queue.length/2);
        queue[randNum] = queue[n-1];
        queue[n-1] = null;
        n--;
        return tmpItem;
    }

    public Item sample() {                
    // return a random item (but do not remove it)
        if (isEmpty()) throw new java.util.NoSuchElementException("sample(): randomized queue is empty");
        int randNum = StdRandom.uniform(n);
        return queue[randNum];
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private int index = 0;
        private Item[] shuffledQueue;
        
        public RandomQueueIterator() {
            shuffledQueue = queue.clone();
            StdRandom.shuffle(shuffledQueue, 0, n-1);
        }

        public boolean hasNext()  {
                return index < n;             
        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return shuffledQueue[index++]; 
        }

        public void remove() { 
            throw new UnsupportedOperationException(); 
        }
    }

    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }


    public static void main(String[] args) {   
    // unit testing (required)
        RandomizedQueue<String> queue = new RandomizedQueue();
        String item = null;

        System.out.println("Digite 10 itens:");
        for (int i = 0; i < 10; i++) {
            item = StdIn.readString();
            queue.enqueue(item); 
        }

        System.out.println("Agora vamos retirar 5 itens");
        for (int i = 0; i < 5; i++) {
            item = queue.dequeue();
            System.out.println("dequeue: " + item);
        }

        System.out.println("Agora vamos amostrar 2 itens");
        for (int i = 0; i < 2; i++) {
            item = queue.sample();
            System.out.println("sample: " + item);
        }

        System.out.println("Agora vamos iterar");
        for (String t: queue)
            System.out.println("iterator1: " + t);

        System.out.println("Agora vamos retirar 5 itens novamente (a fila ficará vazia)");
        for (int i = 0; i < 5; i++) {
            item = queue.dequeue();
            System.out.println("dequeue2: " + item);
        }

        System.out.println("Digite um item para ser adicionado");
        for (int i = 0; i < 1; i++) {
            item = StdIn.readString();
            queue.enqueue(item); 
        }

        System.out.println("last item: " + item);
    }
}

//Performance requirements: your randomized queue implementation must support each randomized queue operation 
//(besides creating an iterator) in constant amortized time and and use space linear in the number of items currently in the queue. 
//That is, any sequence of M randomized queue operations (starting from an empty queue) must take at most cM steps in the worst case, 
//for some constant c. 
//Additionally, your iterator implementation must support next() and hasNext() in constant worst-case time and construction in 
//linear time; you may use a linear amount of extra memory per iterator.