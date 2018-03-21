  /*************************************************************************
 *  Compilation:  javac MeuLinearProbingHashST.java
 *  Execution:    java MeuLinearProbingHashST <alfa inf> <alfa sup> <arquivo>
 *  
 *  Symbol table implementation with linear probing hash table.
 *
 *************************************************************************/

/**
   The LinearProbingHashST class represents a symbol table of generic
   key-value pairs. 

   It supports the usual put, get, contains, delete, size, and is-empty
   methods. It also provides a keys method for iterating over all of the
   keys. A symbol table implements the associative array abstraction:
   when associating a value with a key that is already in the symbol
   table, the convention is to replace the old value with the new
   value. 

   Unlike Map, this class uses the convention that values cannot
   be null—setting the value associated with a key to null is equivalent
   to deleting the key from the symbol table.
*/
import edu.princeton.cs.algs4.LinearProbingHashST; 

// The Queue class represents a first-in-first-out (FIFO) queue of generic items.
import edu.princeton.cs.algs4.Queue;

// Input. This class provides methods for reading strings and numbers from standard input,
// file input, URLs, and sockets.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/In.html
import edu.princeton.cs.algs4.In; // arquivo

// This class provides methods for printing strings and numbers to standard output.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdOut; 
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdRandom;

// Stopwatch. This class is a data type for measuring the running time (wall clock) of a program.
// https://www.ime.usp.br/~pf/sedgewick-wayne/algs4/documentation/index.html
import edu.princeton.cs.algs4.Stopwatch; // arquivo


public class MeuLinearProbingHashST<Key, Value> {
    // largest prime <= 2^i for i = 3 to 31
    // not currently used for doubling and shrinking
    // NOTA: Esses valores são todas as possíveis dimensões da tabela de hash.
    private static final int[] PRIMES = {
        7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
        32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
        8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
        536870909, 1073741789, 2147483647
    };

    private static final int INIT_CAPACITY = PRIMES[0];

    // limite inferior default para o fator de carga
    private static final double ALFAINF_DEFAULT = 0.125;
    
    // limite superior default para o fator de carga
    private static final double ALFASUP_DEFAULT = 0.5;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values

    // NOTA: indice na tabela de primos correspondente ao valor de 'm'
    private int iPrimes = 0;

    // NOTA: alfa é o fator de carga (= load factor) n/m
    //       no caso do tratamento de colisão por sondagem linear alfa é
    //       a porcentagem da tabela que está ocupada.
    //       alfaSup é o limite superior para o fator de carga.
    //       Usado no método put().
    private final double alfaSup;

    // NOTA: alfa é o fator de carga (= load factor) n/m
    //       no caso do tratamento de colisão por sondagem linear alfa é
    //       a porcentagem da tabela que está ocupada.
    //       alfaSup é o limite superior para o fator de carga.
    //       Usado no método delete().
    private final double alfaInf;
    

    /** 
    * Construtor: cria uma tabela de espalhamento 
    * com resolução de colisões por encadeamento. 
    */
    public MeuLinearProbingHashST() {
        this(INIT_CAPACITY, ALFAINF_DEFAULT, ALFASUP_DEFAULT);
    }

   /** 
    * Construtor: cria uma tabela de espalhamento 
    * com (pelo menos) m posições.
    */
    public MeuLinearProbingHashST(int m) {
        this(m, ALFAINF_DEFAULT, ALFASUP_DEFAULT);
    }

   /** 
    * Construtor: cria uma tabela de espalhamento 
    * em que a maior porcentagem de posiçõs preenchidas é 
    * alfaSup e a menor porcetagem é alfaInf (bem, se a tabela for 
    * muito pequena pode ser menor que alfaInf).
    */
    public MeuLinearProbingHashST(double alfaInf, double alfaSup) {
        this(INIT_CAPACITY, alfaInf, alfaSup);
    } 

    /** 
     * Construtor.
     *
     * Cria uma tabela de hash vazia com PRIMES[iPrimes] posições sendo
     * que iPrimes >= 0 e
     *         PRIMES[iPrimes-1] < m <= PRIMES[iPrimes]
     * (suponha que PRIMES[-1] = 0).
     * 
     * Além disso a tabela criada será tal que o fator de carga alfa
     * respeitará
     *
     *            alfaInf <= alfa <= alfaSup
     *
     * A primeira desigualdade pode não valer quando o tamanho da tabela
     * é INIT_CAPACITY.
     *
     * Pré-condição: o método supõe que alfaInf < alfaSup.
     */
    public MeuLinearProbingHashST(int m, double alfaInf, double alfaSup) {
        // TAREFA: veja o método original e faça as adaptações necessárias
        this.alfaSup = alfaSup;
        this.alfaInf = alfaInf;
        n = 0;
        for (int i = 0; i < PRIMES.length; i++) {
            if (PRIMES[i] >= m) {
                iPrimes = i;
                break;
            }
            else if (i == PRIMES.length - 1) {
                iPrimes = i;
                break;
            }
        }
        keys = (Key[])   new Object[PRIMES[iPrimes]];
        vals = (Value[]) new Object[PRIMES[iPrimes]];
        this.m = PRIMES[iPrimes];
    }
    
    // return the number of key-value pairs in the symbol table
    public int size() {
        return n;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // does a key-value pair with the given key exist in the symbol table?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /* return the value associated with the given key, null if no such value
     *
     * Supõe que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        //StdOut.println("enter birl");
        for (int i = hash(key); vals[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                //StdOut.println("exit birl");
                return vals[i];
            }
        }
        //StdOut.println("exit birl");
        return null;
    }
    
    /** 
     *
     * Redimensiona a tabela de hash de modo que ela tenha PRIMES[k]
     * listas e reinsere todos os itens na nova tabela.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     * Assim, o índice k corresponde ao valor PRIMES[k] que será o novo 
     * tamanho da tabela.
     */
    private void resize(int k) {
        // TAREFA: veja o método original e faça adaptação para que
        //         o tamanho da nova tabela seja PRIMES[k].
        if (k >= PRIMES.length || k < 0) return;
        iPrimes = k;
        MeuLinearProbingHashST<Key, Value> temp = new MeuLinearProbingHashST<Key, Value>(PRIMES[iPrimes], alfaInf, alfaSup);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m  = temp.m;
    }

    /**
     * put(): insert the key-value pair into the symbol table
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     */
    public void put(Key key, Value val) {
        // TAREFA: veja o método original e faça adaptação para que
        //         a tabela seja redimensionada se o fator de carga
        //         passar de alfaSup.
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= alfaSup*m) {
            //StdOut.println("birl");
            resize(iPrimes+1);
        }
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        n++;
    }


    // delete the key (and associated value) from the symbol table
    public void delete(Key key) {
        // TAREFA: veja o método original e adapte para que a tabela 
        //         seja redimensionada sempre que o fator de carga for menor que
        //         alfaInf.
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (m > INIT_CAPACITY && n < alfaInf*m) resize(iPrimes-1);

        assert check();
    }

    /**
     * return all of the keys as in Iterable
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (vals[i] != null) queue.enqueue(keys[i]);
        return queue;
    }

    /**
     * check():
     * integrity check - don't check after each put() because
     * integrity not maintained during a delete()
     */
    private boolean check() {
        // TAREFA: veja o método original e adapte para verificar que
        //         a tabela de hash está no máximo alfaSup% cheia.
        // check that hash table is at most 50% full
        if (m < alfaSup*n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }

    /********************************************************************
     *  ALGUNS MÉTODOS NOVOS
     *
     *********************************************************************/

    // retorna o tamanha da tabela de hash
    public int sizeST() {
        return m;
    } 

    /** 
     * maxCluster(): retorna o maior comprimento de um cluster.
     *
     * O custo médio de sondagem linear depende da maneira em que
     * as chaves são agrupadas em pedaços contínuos da tabela.
     * Esses pedaços são chamados de clusters.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */ 
    public int maxCluster() {
        // TAREFA
        int tmp = 0;
        int max = 0;
        for (int i = 0; i < m; i = i + 1) {
            if (keys[i] != null) {
                for (int j = i; j < m; j = j + 1) {
                    if (keys[j] != null) tmp++;
                    else {
                        i = j + 1;
                        break;
                    }
                }
            }
            if (tmp > max){
                max = tmp;
            }
            tmp = 0;
        }
        
        return max;
    }

    /** 
     * numCluster(): retorna o número de clusters.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     */ 
    public int numClusters() {
        // TAREFA
        int num = 0;
        boolean flag  = false;
        for (int i = 0; i < m; i = i + 1) {
            if (keys[i] != null) flag = true;
            if (flag == true && keys[i] == null) {
                num++;
                flag = false;
            }
        }
        return num;
    }
    /**
     * NOTA:
     * Proposição: Em uma tabela de hash com sondagem linear, m posições e
     *   n = alfa*m chaves, o número médio de sondagens (probes), supondo
     *   que a função de hashing satisfaz a hipótese do hashing uniforme, é
     *
     *            0.5 * (1 + 1/(1-alfa)) 
     *
     *   para buscas bem-sucedidas e
     *
     *            0.5 * (1 + a/(1-alfa)^2) 
     *
     *   para buscas malsucedidas.
     */


    /**
     * averageSearchHit(): retorna o custo médio de uma busca
     * bem-sucedida na tabela supondo que cada chave da tabela tem a
     * mesma probabilidade de ser buscada.
     *
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */
    public double averageSearchHit() {
        // TAREFA
        double cost = 0;
        for (Key T: keys())
            cost += probes(T);
        return cost/n;
        
    }

    private int probes(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        //StdOut.println("enter birl");
        for (int i = hash(key), probes = 1; vals[i] != null; i = (i + 1) % m, probes++) {
            if (keys[i].equals(key)) {
                //StdOut.println("exit birl");
                return probes;
            }
        }
        //StdOut.println("exit birl");
        return 0;
    }

    /**
     * averageSearchMiss(): retorna o custo médio de uma busca malsucedida 
     * (que é também o custo de uma inserção) na tabela supondo que a função de hashing 
     * satisfaz a hipótese de hashing uniforme.
     *
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */
    public double averageSearchMiss() {
        // TAREFA
        double cost = 0;
        int test = 0;
        //for (int i = 0; i < m; i++)
            //if (vals[i] == null) flag = 3;
        //StdOut.println("foi");
        for (int i = 0; i < (m - n); i++) {
            test = StdRandom.uniform(m);
            cost += failedProbes(test);
        }

        return (double) cost/ (double) (m - n);
    }

    private int failedProbes(int key) {
        //if (key == null) throw new IllegalArgumentException("argument to get() is null");
        //StdOut.println("enter birl");
        int probes = 1;
        for (int i = key; keys[i] != null; i = (i + 1) % m) {
            probes++;
        }
        //StdOut.println("exit birl");
        return probes;
    }


        
   /***********************************************************************
    *  Unit test client.
    *  Altere à vontade, pois este método não será corrigido.
    ***********************************************************************/
    public static void main(String[] args) {
        if (args.length != 3) {
            showUse();
            return;
        }
        
        String s;
        double alfaInf = Double.parseDouble(args[0]);
        double alfaSup = Double.parseDouble(args[1]);
        String fileName = args[2];

        //=========================================================
        // Testa LinearProbingingHashST
        In in = new In(fileName);
        
        // crie a ST
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
        
        // dispare o cronometro
        Stopwatch sw = new Stopwatch();

        // povoe a ST com palavras do arquivo
        StdOut.println("Criando a LinearProbingingHashST com as palavras do arquivo '" + args[2] + "' ...");
        while (!in.isEmpty()) {
            // Read and return the next line.
            String linha = in.readLine();
            String[] chaves = linha.split("\\W+");
            for (int i = 0; i < chaves.length; i++) {
                if (!st.contains(chaves[i])) {
                    st.put(chaves[i], 1);
                }
                else {
                    st.put(chaves[i], st.get(chaves[i])+1);
                }
            }
        }
        
        StdOut.println("Hashing com LinearProbingingHashST");
        StdOut.println("ST criada em " + sw.elapsedTime() + " segundos");
        StdOut.println("ST contém " + st.size() + " itens");
        in.close();

        //=================================================================================
        StdOut.println("\n=============================================");
        
        // reabra o arquivo
        in = new In(fileName);
        
        // crie uma ST
        MeuLinearProbingHashST<String, Integer> meuST = new MeuLinearProbingHashST<String, Integer>(alfaInf, alfaSup);

        // dispare o cronometro
        sw = new Stopwatch();

        // povoe  a ST com palavras do arquivo
        StdOut.println("Criando a MeuLinearProbingingHashST com as palavras do arquivo '" + args[2] + "' ...");
        while (!in.isEmpty()) {
            // Read and return the next line.
            String linha = in.readLine();
            String[] chaves = linha.split("\\W+");
            for (int i = 0; i < chaves.length; i++) {
                if (!meuST.contains(chaves[i])) {
                    meuST.put(chaves[i], 1);
                }
                else {
                    meuST.put(chaves[i], meuST.get(chaves[i])+1);
                }
            }
        }
        // sw.elapsedTime(): returns elapsed time (in seconds) since
        // this object was created.
        int n = meuST.size();
        int m = meuST.sizeST();
        double alfa = (double) n/m;
        int nClusters = meuST.numClusters();
        StdOut.println("Hashing com MeuLinearProbingingHashST");
        StdOut.println("ST criada em " + sw.elapsedTime() + " segundos");
        StdOut.println("ST contém " + n + " itens");
        StdOut.println("Tabela hash tem " + m + " posições");
        StdOut.println("Maior comprimento de um cluster é " + meuST.maxCluster());
        StdOut.printf("Número de clusters é %d (media = %.2f)\n", nClusters, (double) n / nClusters);
        StdOut.printf("Fator de carga (= n/m) = %.2f\n", (double) n/m);
        StdOut.printf("Custo médio de uma busca bem-sucedida = %.2f (%.2f)\n",
                      meuST.averageSearchHit(), 0.5*(1+1/(1-alfa)));
        StdOut.printf("Custo médio de uma busca malsucedida = %.2f (%.2f)\n",
                      meuST.averageSearchMiss(), 0.5*(1+1/((1-alfa)*(1-alfa))));
        in.close();
        
        // Hmm. Não custa dar uma verificada ;-)
        for (String key: st.keys()) {
            if (!st.get(key).equals(meuST.get(key))) {
                StdOut.println("Opss... " + key + ": " + st.get(key) + " != " + meuST.get(key));
            }
        }
    }


    private static void showUse() {
        String msg = "Uso: meu_prompt> java MeuLinearProbingingHashST <alfa inf> <alfa sup> <nome arquivo>\n"
            + "    <alfa inf>: limite inferior para o comprimento médio das listas (= fator de carga)\n"
            + "    <alfa sup>: limite superior para o comprimento médio das listas (= fator de carga)\n"
            + "    <nome arquivo>: nome de um arquivo com um texto para que uma ST seja\n"
            + "                    criada com as palavras nesse texto.";
        StdOut.println(msg);
    }

}

