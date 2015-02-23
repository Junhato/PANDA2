import java.io.*;
import java.util.*;
import java.math.BigDecimal;
public class SpanningTree{
	public static void main(String args[]){
		SpanningTree spanningtree = new SpanningTree();
		String arg = args[0];
		String filename = args[1];
		try{
			Reader loadgraph = new Reader();
			loadgraph.read(filename);
			Graph graph = loadgraph.graph();

			switch(arg){
				case "-p1":
				 spanningtree.EdgeWeights(graph);
				 break;
				default:
				 System.out.println("The argument is not recognized");
			}
		}
		catch(IOException e){
			System.err.println("IOException");
		}
	}


	public void EdgeWeights(Graph graph){
		Set<Edge> edges = new HashSet<Edge>(graph.edges());
		double d = 0;
		for (Edge edge:edges){
			d += edge.weight();
		}
		BigDecimal x = new BigDecimal(d*1000);
		BigDecimal y = x.setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println("Total Cable Needed: " + y +"m");
	} 
}

