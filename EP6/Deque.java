import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
//A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports adding and removing items
//from either the front or the back of the data structure.
    private Node first;
    private Node last;
    private int n;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {       
    // construct an empty deque
        first = new Node();
        last = first;
         
    }

    public boolean isEmpty() {                
    // is the deque empty?
        return n == 0;
    }

    public int size() {
    // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item) {         
    // add the item to the front
        if (item == null) throw new java.lang.NullPointerException("addFirst(): argument item is null");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (first.next != null)
            (first.next).prev = first;
        n++;
        if (n == 1)
            last = first;
    }

    public void addLast(Item item) {           
    // add the item to the end
        if (item == null) throw new java.lang.NullPointerException("addLast(): argument item is null");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (last.prev != null)
            (last.prev).next = last;
        n++;
        if (n == 1)
            first = last;
    }

    public Item removeFirst() {       
    // remove and return the item from the front
        if (isEmpty()) throw new java.util.NoSuchElementException("removeFirst(): attempt to remove an item from an empty deque");
        Item item = first.item;
        first = first.next;
        if (first != null)
            first.prev = null;
        n--;
        return item;
    }

    public Item removeLast() {               
    // remove and return the item from the end
        if (isEmpty()) throw new java.util.NoSuchElementException("removeLast(): attempt to remove an item from an empty deque");
        Item item = last.item;
        last = last.prev;
        if (last != null)
            last.next = null;
        n--;
        return item;
    }

    public Iterator<Item> iterator() {       
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
                    
        public void remove() { 
            throw new UnsupportedOperationException(); 
        }
    }

    public static void main(String[] args) {
    // unit testing (required)
        Deque<String> queue = new Deque();
        String item = null;

        System.out.println("Digite 10 itens (vamos adicionar na frente:");
        for (int i = 0; i < 10; i++) {
            item = StdIn.readString();
            queue.addFirst(item); 
        }

        System.out.println("Agora vamos retirar 10 itens de trás:");
        for (int i = 0; i < 10; i++) {
            item = queue.removeLast();
            System.out.println("dequeue: " + item);
        }

        System.out.println("Digite 10 itens (vamos adicionar atrás):");
        for (int i = 0; i < 10; i++) {
            item = StdIn.readString();
            queue.addLast(item); 
        }

        System.out.println("Agora vamos retirar 10 itens da frente:");
        for (int i = 0; i < 10; i++) {
            item = queue.removeFirst();
            System.out.println("dequeue: " + item);
        }

        System.out.println("Digite 10 itens (vamos adicionar atrás):");
        for (int i = 0; i < 10; i++) {
            item = StdIn.readString();
            queue.addLast(item); 
        }

        System.out.println("Agora vamos iterar:");
        for (String t: queue)
            System.out.println("iterator1: " + t);
    }
}

//Performance requirements: your deque implementation must support each deque operation (including construction) in 
//constant worst-case time and use space linear in the number of items currently in the deque.
//Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.

