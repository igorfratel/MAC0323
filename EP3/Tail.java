/** ***********************************************************************
 *  Compilation:  javac -Xlint Tail.java
 *  Execution:    java Tail
 *  Dependencies: StdIn.java StdOut.java 
 *  
 *
 * % java Tail testes/*
 * ==> testes/ind.txt<==
 * changed for light and transient causes; and accordingly all experience
 * hath shewn, that mankind are more disposed to suffer, while evils are
 * sufferable, than to right themselves by abolishing the forms to which
 * they are accustomed. But when a long train of abuses and usurpations,
 * pursuing invariably the same Object evinces a design to reduce them
 * under absolute Despotism, it is their right, it is their duty, to
 * throw off such Government, and to provide new Guards for their future
 * security.--Such has been the patient sufferance of these Colonies; and
 * such is now the necessity which constrains them to alter their former
 * Systems of Government.
 *
 * ==> testes/les-miserables.txt<==
 *
 *
 *  Most people start at our Web site which has the main PG search facility:
 *
 *       http://www.gutenberg.org
 *
 *  This Web site includes information about Project Gutenberg-tm,
 *  including how to make donations to the Project Gutenberg Literary
 *  Archive Foundation, how to help produce our new eBooks, and how to
 *  subscribe to our email newsletter to hear about new eBooks.
 *
 *  ==> testes/teste1.txt<==
 *  aaa bbbb aa aaa ccc dd
 *
 *  ==> testes/teste2.txt<==
 *  Como é bom estudar MAC0323!
 *  Como é bom estudar MAC0323!
 *
 *
 *************************************************************************/

// This class provides methods for reading strings and numbers from standard
// input, file input, URLs, and sockets.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
import edu.princeton.cs.algs4.In;

// This class provides methods for printing strings and numbers to standard output.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdOut; 


/** Está é classe é inspirada no Tarefa 04 do Paulo Felofiloff para 
 * a edição de 2014 de MAC0323: 
 *
 *     https://www.ime.usp.br/~pf/mac0323-2014/tarefas/T04.html
 *
 * Como diz a tarefa 
 *
 *    "Resolva o problema sem desperdiçar memória. 
 *     Use uma (ou mais) das ADTs que já estudamos." 
 *
 */
import java.util.Iterator;


public class Tail {
    // atributos de estado 
    public Queue myQueue;
    
    /** Construtores.
     * 
     */
    // Construtor que prepara para que o método lines()
    // retorne um objeto iterável com as 10 últimas linhas
    // do arquivo fileName. O valor 10 é o default.
    public Tail(String fileName) {
        // escreva seu método a seguir
    	this(fileName, 10);
    } 

    // Construtor que prepara para que o método lines()
    // retorne um objeto iterável com as k últimas linhas
    // do arquivo fileName.
    public Tail(String fileName, int k) {
        // escreva seu método a seguir
        if (k > 0)
            myQueue = new Queue(k);
        else
            myQueue = new Queue(1);
        In file = new In(fileName);
        while (file.hasNextLine() && k > 0) {
        	if (myQueue.size() < k){
        		myQueue.push(file.readLine());
        	}
        	else {
        		myQueue.pop();
        		myQueue.push(file.readLine());
        	}
        }

    }   
    /** Returns an iterable object containing the last k lines. 
     *  The value of k depends on the constructor.
     */
    public Iterable<String> lines() {
        // escreva seu método a seguir
        return myQueue;
    }
private class Queue implements Iterable<String> {
	private String[] myQueue;
	private int n;
	public Queue(int cap){
		myQueue = new String[cap];
	}
	public void push(String item){
		myQueue[n++] = item;
	}
	public String pop(){
		String tmp = myQueue[0];
		for (int i=1; i<n; i++)
			myQueue[i-1] = myQueue[i];
		n--;
		return tmp;
	}
	public QueueIterator iterator() {
    	return new QueueIterator();
	}	
	public int size(){
		return n;
	}
 private class QueueIterator implements Iterator<String> {
		private int index;
		private String[] tmpQueue = myQueue;
	    public boolean hasNext()  {
	    	if (index < n) 
	     		return tmpQueue[index] != null; 
	     	return false;                
     	}
	    public String next() {
	        if (hasNext())
	        	return tmpQueue[index++]; 
	        return null;
        }
	}
}
   /***************************************************************************
    *  métodos privados
    **************************************************************************/

    // apenas para o uso do unit test abaixo
    private static void use() {
        String message = "Use: java Tail [-n NUM] [FILE]\n" +
            "   Print the last 10 lines of each FILE to standard output.\n" +
            "   With more than one FILE, precede each with a header giving the file name.\n" +
            "   -n NUM: output the last NUM lines, instead of the last 10";
        StdOut.println(message);
    }
    
   /** Unit test
    */
    public static void main(String[] args) {
        if (args.length == 0) {
            use();
            return;
        }
        
        if (args[0].equals("-n")) {
            int k = Integer.parseInt(args[1]);
            for (int i = 2;  i < args.length; i++) {
                StdOut.println("==> " + args[i] + "<==");
                Tail tail = new Tail(args[i], k);
                for (String line: tail.lines()) {
                    StdOut.println(line);
                }
                StdOut.println();
            }
            
        }
        else {
            for (int i = 0;  i < args.length; i++) {
                StdOut.println("==> " + args[i] + "<==");
                Tail tail = new Tail(args[i]);
                for (String line: tail.lines()) {
                    StdOut.println(line);
                }
                StdOut.println();
            }
        }
    }
}

