import java.io.*;
import java.util.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
				case "-p2":
				 Set<Edge> edges = graph.edges();
				 spanningtree.InterestGroups(edges);
				 break;
				case "-p3":
				 spanningtree.MinimumSpanningTree(graph);
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
	
	public double price(Set<Edge> edges){
		double price = 0;
		for (Edge edge:edges){
			if (edge.type().equals(Edge.EdgeType.LocalRoad)){
				price += (edge.weight())*4500 + 5000;
			}
			else if (edge.type().equals(Edge.EdgeType.MainRoad)){
				price += (edge.weight())*4000;
			}
			else if (edge.type().equals(Edge.EdgeType.Underground)){
				price += (edge.weight())*1000;
			}
		}
		
		return price;
	}

	public double hours(Set<Edge> edges){
		double hours = 0;
		for (Edge edge:edges){
			if (edge.type().equals(Edge.EdgeType.LocalRoad)){
				hours += (edge.weight())*0.2 ;
			}
			else if (edge.type().equals(Edge.EdgeType.MainRoad)){
				hours += (edge.weight())*0.5;
			}
			else if (edge.type().equals(Edge.EdgeType.Underground)){
				hours += (edge.weight())*1;
			}
		}
		return hours;
	}
	
	public String speed(Set<Edge> edges){
		double days = 0;
		for (Edge edge:edges){
			if (edge.type().equals(Edge.EdgeType.LocalRoad)){
				days += (edge.weight())*10/2 ;
			}
			else if (edge.type().equals(Edge.EdgeType.MainRoad)){
				days += (edge.weight())*10/6;
			}
			else if (edge.type().equals(Edge.EdgeType.Underground)){
				days += (edge.weight())*10/9;
			}
		}
		SimpleDateFormat f = new SimpleDateFormat("EEE d MMM yyyy HH:mm");
		int m = (int)(days*24*60);

		Calendar speed = Calendar.getInstance();
		speed.set(2014, 2, 15, 0, 0);
		speed.add(Calendar.MINUTE, m);
		String s = f.format(speed.getTime());
		return s;
		}

	public void InterestGroups(Set<Edge> edges){
		BigDecimal price = new BigDecimal(price(edges));
		BigDecimal hours = new BigDecimal(hours(edges));
		
		System.out.println(price.setScale(2, BigDecimal.ROUND_HALF_UP));
		System.out.println(hours.setScale(2, BigDecimal.ROUND_HALF_UP));
		//i didnt get right for third one
		System.out.println(speed(edges));

	}
	private Set<Node> nset = new HashSet<Node>();
	private Node small;
	public void Search(Node n, Graph graph){
		Set<Edge> edges = graph.edges();
		int w = n.distance();
		nset.add(n);
		small = n;
		for(Edge edge:edges){
			w += edge.weight();
			//update the distance
			if(edge.id1().equals(n.name())){
				Node x = graph.find(edge.id2());
				if(x.distance() == 0 || w < x.distance()){
					x.change(w, edge);
				}
				/*if (nset.contains(x) == false && (x.distance() < small.distance() || small.equals(n))){
					small = x;
				}*/
			}
			else if(edge.id2().equals(n.name())){
				Node x = graph.find(edge.id1());
				if(x.distance() == 0 || w < x.distance()){
					x.change(w, edge);
				}
					
				/*if (nset.contains(x) == false && (x.distance() < small.distance() || small.equals(n))){
					small = x;
				}*/


			}
		}
		//choose the next node to visit
		for(Node node:graph.nodes()){
			if((small.equals(n) || node.distance()<small.distance()) && nset.contains(node) == false && node.distance() != 0){
				small = node;
			}
		}
		/*if(small.equals(n) && n.lastedge() != null){
	
			Node id1 = graph.find(n.lastedge().id1());
			Node id2 = graph.find(n.lastedge().id2());
			
			if(n.equals(id1)){
				if((n.lastedge()).equals(id2.lastedge())){
					small = n;
				}
				else{
					small = id2;
				}
			}
			else if(n.equals(id2)){
				if((n.lastedge()).equals(id2.lastedge())){
					small = n;
				}
				else{

					small = id1;
				}
			}
		}*/
			
	}
	
	public void MinimumSpanningTree(Graph graph){
		Set<Edge> eset = new HashSet<Edge>();
		Set<Node> nodes = graph.nodes();
		double p = 0;
			small = graph.find("7");
			//repeat the prim algorithem number of nodes times
			for(int i = 0; i <= graph.nodeNumber(); i++){
				Node x = small;
				Search(small, graph);
				//System.out.println(small);
				//if(x.equals(small)) break;
				
			}
		
		//make set of edge for new graph
		for(Node n:nodes){
			if(n.lastedge() != null){
				eset.add(n.lastedge());
			}
		}
		//create the newgraph
		InterestGroups(eset);
		graph.edges().clear();
		graph.edges().addAll(eset);
		Writer newgraph = new Writer();
		try{
			newgraph.write("newgraph.txt", graph);
		}
		catch(IOException e){
			System.err.println("IOException2");
		}
	}


		
}

