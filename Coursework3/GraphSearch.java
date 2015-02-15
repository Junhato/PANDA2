import java.util.*;
import java.io.*;

public class GraphSearch {

	public GraphSearch() {
	}

	private void BubbleSort(Node[] array) {

		Node temp;
		for (int j=0; j< array.length; j++) {
			for (int i=0; i< array.length -i; i++) {
				if (Integer.parseInt(array[i].name()) > Integer.parseInt(array[i+1].name())) {
				temp = array[i];
				array[i] = array[i+1];
				array[i+1] = temp;
				}
			}
		}
	}
	private void print(Graph graph) {

		Set<Node> nodes = graph.nodes();

		Node[] aCopy = nodes.toArray(new Node[nodes.size()]);
		BubbleSort(aCopy);

		for (int i = 0; i < aCopy.length; i++) {
			Node[] Neighbours = aCopy[i].neighbours().toArray(new Node[aCopy[i].degree()]);
			BubbleSort(Neighbours);
			System.out.print(aCopy[i].name() + " ");
			for (int k = 0; k < Neighbours.length; k++) {
				System.out.print(Neighbours[k].name() + " ");
			}
		System.out.println("");

		}
	}

	private static Set<Node> neighbourSearch(Graph graph, int n) {
	// create the output list
	Set<Node> output = new HashSet<Node>();
	
	// do the search and fill the output
	for (Node node : graph.nodes()) {
            if (node.degree() == n) {
		    output.add(node);
	    }
        }
	return output;
	}

	private static boolean findneighbour(Node node, Set<Node> nodes) {
		for (Node n : nodes) {
			if (n.name().equals(node.name())) return true;
		}
		return false;
	}
	
	private static Set<Node> findFullyConnectedNeighbours(Graph graph) {
		Set<Node> output = new HashSet<Node>();
		Iterator itrtg = graph.nodes().iterator();

		while (itrtg.hasNext()) {
		int count = 0;
		Node anode = (Node)itrtg.next();

		Iterator itrtn = anode.neighbours().iterator();
		Node aneighbour = (Node)itrtn.next();
			if (anode.degree() == aneighbour.degree()) {
				while(itrtn.hasNext() && findneighbour((Node)itrtn.next(), aneighbour.neighbours())) {
					count = count + 1;
				}
				if (count == (anode.neighbours().size() - 1)) {
					output.add(anode);
				}
			}
		}
	return output;
	}

	public static int findNumberOfCliques(Graph graph, int n) {
		int number = 0;
		Iterator itrtg = graph.nodes().iterator();

		while (itrtg.hasNext()) {
		int count = 0;
		Node anode = (Node)itrtg.next();

		Iterator itrtn = anode.neighbours().iterator();
		Node aneighbour = (Node)itrtn.next();
				while(itrtn.hasNext()) {
					Node temp = (Node)itrtn.next();
					if (findneighbour(temp, aneighbour.neighbours())) count = count + 1;
				}
				if ((count == (anode.neighbours().size() - 1)) && count >= n) {
					number = number + 1;
				}
		}
	return number;
	}

	public static void main(String[] args) {
		
		GraphSearch test = new GraphSearch();
		Reader reader = new Reader();
		try { reader.read(args[1]); }
		catch (IOException ioe) {System.err.println("invalid file");}
		Graph graph = reader.graph();

		if (args[0].equals("-p1")) {

			test.print(graph);
			}

		else if (args[0].equals("-p2")) {

			Set<Node> result = test.neighbourSearch(graph, Integer.parseInt(args[2]));
			System.out.println("Number of nodes with at least " + args[2] + " neighbours: " + result.size());
		}

		else if (args[0].equals("-p3")) {

			Set<Node> result = test.findFullyConnectedNeighbours(graph);
			System.out.println("number of nodes with fully connected neighbours: " + result.size());
		}

		else if (args[0].equals("-p4")) {

			Set<Node> result = test.neighbourSearch(graph, Integer.parseInt(args[2]));
			System.out.println("Number of cliques of size " + args[2] + " : " + test.findNumberOfCliques(graph, Integer.parseInt(args[2])));
		}
	}
}
