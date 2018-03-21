Neste exercício programa vocês farão mais um contador de palavras. Dessa vez, trata-se de um utilitário que receberá várias strings com várias palavras, e deverá dar informações variadas.

Mais precisamente, vocês deverão criar uma classe WordFinder. Essa classe deverá receber, em seu construtor, um vetor de Strings. Essas strings são compostas de palavras separadas por espaços. Não haverá pontuação ou caracteres especiais. Os seguintes métodos deverão ser implementados em WordFinder:

String getMax(): retorna a palavra que se repete mais vezes nas strings dadas. Caso a palavra apareça mais de uma vez na mesma string, ignore.
String containedIn(int a, int b): retorna uma palavra que apareça tanto na string de índice a do vetor quanto na string de índice b. Se não tiver nenhuma, retorne null. Se tiver mais de uma, retorne a que quiser.
int[] appearsIn(String s): recebe uma palavra e retorna um vetor com os índices das strings do vetor inicial em que ela apareça. Se não aparecer em nenhum, retorne um vetor vazio.
Aconselhamos a utilização de uma HashTable do Sedgewick (como a SeparateChainingHashST.java) para estruturar este problema. Você também pode utilizar uma HashTable caseira ou qualquer outra estrutura, mas provavelmente vai te dar bem mais trabalho.