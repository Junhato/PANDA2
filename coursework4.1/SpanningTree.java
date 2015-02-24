import java.io.*;
import java.util.*;
import java.math.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

public class SpanningTree {

	public static Graph newgraph(String source) {

		Graph g = new Graph();
		try {
		Reader load = new Reader();
		load.read(source);
		g = load.graph();
		}
		catch(IOException e) {
		System.err.println("IOException");
		}
		return g;
	}

	public static double roundtotwo(double value) {

		BigDecimal number = new BigDecimal(value);
		number = number.setScale(2, RoundingMode.HALF_UP);
		return number.doubleValue();
	}

	public static void main(String args[]) {

		SpanningTree st = new SpanningTree();
		
		String arg = args[0];
		String file = args[1];

			Graph graph = newgraph(file);
			Graph govgraph = newgraph(file);
			Graph comgraph = newgraph(file);
			Graph csgraph = newgraph(file);
		
			switch (arg) {
				case "-p1":
				double result = st.test1(graph);
				System.out.println("Total Cable Needed: " + roundtotwo(result * 1000) + "m");
				break;

				case "-p2":
				weightchange(govgraph, 4500, 4000, 1000, 5000);
				weightchange(comgraph, 0.2, 0.5, 1, 0);
				weightchange(csgraph, (1/0.2), (1/0.6), (1/0.9), 0);
				st.test2(govgraph, comgraph, csgraph);
				break;
				
				case "-p3":

				Graph graph1 = newgraph(file);
				weightchange(graph1, 4500, 4000, 1000, 5000);
				govgraph = st.test3(graph1);

				Graph graph2 = newgraph(file);
				weightchange(graph2, 0.2, 0.5, 1, 0);
				comgraph = st.test3(graph2);

				Graph graph3 = newgraph(file);
				weightchange(graph3, (1/0.2), (1/0.6), (1/0.9), 0);
				csgraph = st.test3(graph3);
				st.test2(govgraph, comgraph, csgraph); 
				break;

				default:
				System.out.println("The argument is not recognized");
			 }
	}

	public double test1(Graph graph) {

		double sum = 0;
		for (Edge edge : graph.edges()) {
			sum = sum + edge.weight();		
		}
		return sum;
	}

	public static void weightchange(Graph g, double factor1, double factor2, double factor3, double constant) {

		for (Edge e : g.edges()) {

			switch (e.type()) {

				case LocalRoad:
				e.setweight(factor1 * e.weight() + constant);
				break;

				case MainRoad:
				e.setweight(factor2 * e.weight());
				break;

				case Underground:
				e.setweight(factor3 * e.weight());
				break;
                      
				default:
				System.out.
					
					println("Should never get here...");
				break;
			}
		}
	}

	public void test2(Graph g1, Graph g2, Graph g3) {

		double cs = test1(g3);
		int idays = (int)cs;
		double minutes = cs - idays;
		minutes = (minutes * 1440) + 60;
		SimpleDateFormat dateformat = new SimpleDateFormat("EEE d MMMMM yyyy hh:mm");
		String date = "Sun 15 February 2014 00:00";
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateformat.parse(date));
		}
		catch (ParseException ex) {
			System.err.println("error");
		}
		calendar.add(Calendar.DATE, idays);
		calendar.add(Calendar.MINUTE, (int)(minutes));
		date = dateformat.format(calendar.getTime());

		System.out.println("Price: " + String.format("%.2f", roundtotwo(test1(g1))));
		System.out.println("Hours of Disrupted Travel: " + roundtotwo(test1(g2)) + "h");
		System.out.println("Completion Date: " + date.toString());
	}

	public void updatedistance(Node current, Graph graph) {

		Node connected;

		for (Edge e : graph.edges()) {

			if (e.id1().equals(current.name())) {
				connected = graph.find(e.id2());
				if (connected == null) continue;
			}
			else if (e.id2().equals(current.name())) {
				connected = graph.find(e.id1());
				if (connected == null) continue;
			}
			else continue;

			if (connected.distance() > e.weight()) connected.setdistance(e.weight());
		}
	}
	
	public Node min(Graph graph) {

		double min = 10000;
		Node closest = new Node("");
		for (Node n : graph.nodes()) {
			if (n.distance() < min) {
				min = n.distance();
				closest	= n;
			}			
		}
		return closest;
	}

	public Graph test3(Graph g) {

		Graph mst = new Graph();
			
		Node now = g.find("7");
		Edge link;

		while (g.nodes().size() != 0) {
			System.out.println(now.name());
			updatedistance(now, g);	
			mst.nodes().add(now);
			//i should always be able to find this edge...
			link = g.findedge(now, min(g));
			g.nodes().remove(now);
			if (link != null) {
			mst.edges().add(link);
			g.edges().remove(link);
			}
			now = min(g);
		}

	return mst;
	}
}
