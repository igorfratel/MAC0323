import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.SET;
import java.util.Iterator;
public class CBSPaths {
	EdgeWeightedDigraph airNet;

	public CBSPaths(int V, int E) {
		airNet = new EdgeWeightedDigraph(V, E);

	}
	public void addEdge(String A, String B, int T) {
		//StdOut.println(A);
		DirectedEdge e = new DirectedEdge(A.hashCode(), B.hashCode(), (double)T);
		airNet.addEdge(e);

	}

	public boolean isSafe(String A, String MinPub, String[] executives) {
		DijkstraSP MinPubDik = new DijkstraSP(airNet, MinPub.hashCode());
		double distForMinPub = MinPubDik.distTo(A.hashCode());
		MinPubDik = null;
		for (int i = 0; i < executives.length; i++) {
			DijkstraSP myDik = new DijkstraSP(airNet, executives[i].hashCode());
			double dist = myDik.distTo(A.hashCode());
			if (dist > distForMinPub) return false;
		}
		return true;
	}

	public static void main(String[] args) {
		int [] NMK = new int[3]; 
		NMK[0] = StdIn.readInt();
		NMK[1] = StdIn.readInt();
		NMK[2] = StdIn.readInt();
		CBSPaths myCBS = new CBSPaths(NMK[0] + 999999, NMK[1]);
		SET<String> cities = new SET<String>();
		for (int i = 0; i < NMK[1]; i++) {
			String A = StdIn.readString();
			String B = StdIn.readString();
			cities.add(A);
			cities.add(B);
			int T = StdIn.readInt();
			myCBS.addEdge(A, B, T);
			//StdOut.println("BIRL");
		}
		String [] executives = new String[NMK[2]];
		for (int i = 0; i < NMK[2]; i++) {
			executives[i] = StdIn.readString();
		}
		boolean flag = false;
		String MinPub = StdIn.readString();
		for (String T: cities) {
			if (myCBS.isSafe(T, MinPub, executives)) {
				StdOut.println(T);
				flag = true;
			}
		}
		if (flag == false) StdOut.println("VENHA COMIGO PARA CURITIBA");
	} 
}