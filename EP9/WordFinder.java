import edu.princeton.cs.algs4.SET; 
import edu.princeton.cs.algs4.ST; 
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; 
import java.util.List;
import java.util.ArrayList;
public class WordFinder {     //Essas strings são compostas de palavras separadas por espaços. Não haverá pontuação ou caracteres especiais     SET<String>[] mySet;
	String[] myArray;  
	List<SET<String>> mySet;

	public WordFinder (String[] array) {
	myArray = array;  
	mySet = new ArrayList<SET<String>>(); 
	SET<String> tmpSet;  
	for (int i = 0, j; i < myArray.length; i++) {             
		String[] arr = myArray[i].split(" ");             
		tmpSet = new SET<String>();
		for ( String S : arr) {
			tmpSet.add(S);
			}         
		mySet.add(tmpSet);
		}  
	StdOut.println(mySet);   
	}
	
	public String getMax() {
	//retorna a palavra que se repete mais vezes nas strings dadas. Caso a palavra apareça mais de uma vez na mesma string, ignore.
		ST<String, Integer> myST = new ST<String,  Integer>();
		SET<String> tmpSet;
		for (int i = 0; i < mySet.size(); i++) {
			tmpSet = mySet.get(i);
			for (String S: tmpSet) {
				if (myST.contains(S)) {
					myST.put(S, myST.get(S) + 1);	
				}
				else 
					myST.put(S, 1);
			}
		}
		int max = 0;
		String maxString = null;
		for (String S: myST.keys())
			if (myST.get(S) >= max) {
				max = myST.get(S);
				maxString = S;
			}
		return maxString;
	}	

	public String containedIn(int a, int b) {
	//retorna uma palavra que apareça tanto na string de índice a do vetor quanto na string de índice b. 
	//Se não tiver nenhuma, retorne null. Se tiver mais de uma, retorne a que quiser.
		SET<String> tmpSet0 = mySet.get(a);
		SET<String> tmpSet00 = mySet.get(b);
		SET<String> tmpSet = tmpSet0.intersects(tmpSet00);
		if (tmpSet.isEmpty()) return null;
		for (String S: tmpSet)
			return S;
		return null;
	}

	public int[] appearsIn(String s) {
	// recebe uma palavra e retorna um vetor com os índices das strings do vetor inicial em que ela apareça.
	// Se não aparecer em nenhum, retorne um vetor vazio.
		int[] myInt= new int[myArray.length];
		int j = 0;
		for (int i = 0; i < myArray.length; i++) {             
			String[] arr = myArray[i].split(" ");             
			for (String S : arr) {
				if (S.equals(s))
					myInt[j++] = i;
			}       
		}	
		int [] ourInt = new int[j];
		for (int i = 0; i < j; i++)
			ourInt[i] = myInt[i];
		return ourInt;

	}
	public static void main(String[] args) {
		String[] array = new String[3];
		array[0] = "oi meu caro";
		array[1] = "olar nosso caro amigo";
		array[2] = "pega no meu caro";
		WordFinder myWordFinder = new WordFinder(array);
		int[] boom = myWordFinder.appearsIn("caro");
		for (int i = 0; i < boom.length; i++)
			StdOut.println(boom[i]);
	}	
}