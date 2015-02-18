import java.io.*;
import java.util.*;

public class GraphSearch2{
	public static void main(String args[]){
		GraphSearch2 graphsearch = new GraphSearch2();
		
		String arg = args[0];
		String file = args[1];
		try{
			Reader load = new Reader();
			load.read(file);
			Graph graph = load.graph();
		
			switch (arg){
				case "-p1":
				 graphsearch.test1(graph);
				 break;
				case "-p2":
				 graphsearch.test2(graph, args[2]);
				 break;
				case "-p3":
				 graphsearch.test3(graph);
				 break;
				case "-p4":
				 graphsearch.test4(graph, args[2]);
				 break;
				default:
				 System.out.println("The argument is not recognized");
			 }
		 }
		 catch(IOException e){
			System.err.println("IOException");
		}
	}
	
	public void test1(Graph graph){

		//get list of nodes
		Set<Node> nodes = graph.nodes();
		ArrayList <Integer> nodeList = new ArrayList<Integer>();
		//change name of node to value
		for(Node n:nodes){
			int node = Integer.parseInt(n.name());
			nodeList.add(node);
		}
		//sort nodes
		Collections.sort(nodeList);
		//print for nodes to print out
		ArrayList<String> print = new ArrayList<String>();
		for(int e : nodeList){
			String t = String.valueOf(e);
			print.add(t);
		}
		//make string having node and neighbour		
		for(Node node:nodes){
			int i = 0;
			int x = Integer.parseInt(node.name());	
			while(x != nodeList.get(i)){
				i++;
			}
			ArrayList <Integer> neighbourList = new ArrayList<Integer>();
		
			for(Node n:(node.neighbours())){
				int y = Integer.parseInt(n.name());
				neighbourList.add(y);
			}
			Collections.sort(neighbourList);
			String s = new String(node.name());
			for(int num:neighbourList){
				s = s + " " + num;
			}

			print.set(i, s);
			
		}
		//print nodes with new line
		String a = new String();
		for (String b:print){
			if (b.equals(print.get(0))){
				a = b;
			}
			else {
				a = a + "\n" + b;
			}
		} 
		System.out.println(a);
	}
		

	public static Set<Node> neighbourSearch(Graph graph, int n)
	{
		// create the output list
		Set<Node> output = new HashSet<Node>();
		
		// get list of nodes	
		Set<Node> node = graph.nodes();
		
		// do the search and fill the output
		for (Node k:node){
			if ((k.degree()) >= n) output.add(k);
		}
		
		//
		
		
		return output;
	}
	public void test2(Graph graph, String s){
		try{
			int n = Integer.parseInt(s);

			int num = (neighbourSearch(graph, n).size());
			System.out.println("Number of nodes with at least " + n + " neighbours: " + num);
		}
		catch(Exception e){
			System.err.println("Something wrong");
		}
	}
	public static Set<Node> findFullyConnectedNeighbours(Graph graph){
		Set<Node> output = new HashSet<Node>();
		Set<Node> nodes = graph.nodes();
		for(Node n:nodes){
			Set<Node> neighbour = new HashSet<Node>(n.neighbours());
			neighbour.add(n);
			String tf = "t";
			for (Node t:neighbour){
				Set<Node> set = new HashSet<Node>(t.neighbours());
				set.add(t);		
				if (set.containsAll(neighbour) == false){
					tf = "f";
					break;
				}
			}
			if(tf.equals("t")){
				output.add(n);
			}		
		}
		return output;
	}
	public void test3(Graph graph){
		int c = (findFullyConnectedNeighbours(graph)).size();
		System.out.println("number of nodes with fully connected neighbours: " + c);
	}
	public static int findNumberOfCliques(Graph graph, int n){
	
			Set<Node> nodes = findFullyConnectedNeighbours(graph);
			Set<Node> set = new HashSet<Node>();
			for(Node k: nodes){
				if (k.degree() >= n-1) {
					for(Node h:k.neighbours()){
						set.add(h);
					}
					set.add(k);
				}
			}
			int output = set.size();
			return output;
		
	}
	public void test4(Graph graph, String s){
		try{
			int n = Integer.parseInt(s);
			System.out.println("Number of cliques of size " + n +": " + findNumberOfCliques(graph, n));
		}
		catch(Exception e){
			System.err.println("Something wrong");
		}
	}
}
			
