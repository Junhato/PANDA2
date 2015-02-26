import java.io.*;
import java.util.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;
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
		SimpleDateFormat f = new SimpleDateFormat("EEE d MMMMM yyyy hh:mm", Locale.UK);
		int m = (int)(days*24*60);

		Calendar speed = Calendar.getInstance();
		String date = "Sun 15 February 2014 00:00";
		try{
			speed.setTime(f.parse(date));
		}
		catch(ParseException ex){
			System.err.println("error");
		}
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
		double w;
		nset.add(n);
		double s = 1000;
		for(Edge edge:edges){
			w = edge.weight();
			//update the distance
			if(edge.id1().equals(n.name())){
				Node x = graph.find(edge.id2());
				if(x.distance() == 0 || w < x.distance()){
					x.change(w, edge);
				}
				/*if (nset.contains(x) == false && (x.distance() < small.distance() || small.equals(n))){
					small = x;
				}*/
			if(x.distance()<s){
				s = x.distance();
				small = x;
			}
			}
			else if(edge.id2().equals(n.name())){
				Node x = graph.find(edge.id1());
				if(x.distance() == 0 || w < x.distance()){
					x.change(w, edge);
				}
					
				/*if (nset.contains(x) == false && (x.distance() < small.distance() || small.equals(n))){
					small = x;
				}*/
			if(x.distance()<s) {
				s = x.distance();
				small = x;

			}
		}
			}
		//choose the next node to visit

			
		
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
	//	for(Node node:nodes){
			small = graph.find("137");
			//nset.clear();
			//repeat the prim algorithem number of nodes times
			//while(nset.size() != graph.nodes().size()){
			for(int i=0; i<graph.nodeNumber(); i++){			
				Node x = small;
				Search(small, graph);
				//System.out.println(small);
				//if(x.equals(small)) break;
			/*	for(Node n:nodes){
					if(n.lastedge() != null){
					eset.add(n.lastedge());
					}
				}

				rute(graph, eset);
				InterestGroups(eset);*/
				
				
			}
		//}
		//make set of edge for new graph
		for(Node n:nodes){
			if(n.lastedge() != null){
				eset.add(n.lastedge());
			}
		}
		//Set<Edge> es = rute(graph, eset);
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

	public Set<Edge> getedge(String id1, String id2, Graph graph){
		Set<Edge> edges = new HashSet<Edge>();
		for(Edge edge:graph.edges()){
			if (id1.equals(edge.id1()) && id2.equals(edge.id2())){
				edges.add(edge);
			}
		}
		return edges;
	}
	
	public Set<Edge> rute(Graph graph, Set<Edge> es){
		String[] underground = {
		"79 93 77 94 77 78 78 79 93 94 94 95 77 95",
		"67 79 63 65 65 67 63 79 63 64 64 65 65 66 66 67",
		"153 185 153 184 184 185 153 167 167 168 168 184 ",
		"1 46 1 46 1 9",
		"46 74 46 58 58 74 45 46 45 58 58 74",
		"13 46 34 46 22 34 22 23 13 23 46 47 34 47",
		"13 89 13 14 14 15 15 29 29 55 55 89 15 16 55 71 71 89",
		"46 79 46 61 61 62 62 79",
		"67 89 67 68",
		"79 111 79 98 98 110 110 111",
		"67 111 100 111 83 101 100 101 100 112 111 112",
		"89 140 88 89 115 126 126 140",
		"89 128 89 105 108 108 135 128 135 135 143 128 143",
		"128 140 128 142 133 140 141 142 133 141",
		"140 153 140 154 153 154",
		"111 153 130 139 139 153",
		"111 163 111 124 123 124 123 163 123 137 137 147",
		"128 185 128 187 128 172 172 187 187 198",
		};
		String[] mainroad = {
		"1 58 1 8 8 18 18 31 3144 44 58",
		"77 94 77 95 94 95",
		"46 58 45 46 45 58",
		"58 77 58 59 59 76 76 77",
		"46 78 46 61 61 78",
		"34 46 46 47 34 47",
		"63 65 63 64 64 65",
		"22 65 22 35 35 65",
		"3 22 3 11 11 22",
		"3 23 3 12 12 23",
		"23 67 23 37 37 50 50 67",
		"65 67 65 66 66 67",
		"52 67 51 52 51 67",
		"13 52 25 39 39 52 ",
		"41 52 40 41 40 52",
		"15 41 15 28 28 41",
		"15 29 15 16 16 29",
		"7 42 7 17 17 42",
		"52 86 52 69 69 86",
		"41 87 41 54 54 70 70 87",
		"87 105 87 88",
		"55 89 55 71 71 89",
		"72 105 72 90 90 105",
		"72 107 71 91 91 107",
		"105 107 105 106 106 107",
		"107 161 107 119 119 136",
		"161 199 161 174 174 175 171 175 171 199",
		"128 199 128 188 188 199",
		"128 161 128 160 160 161",
		"128 135 128 143 135 143",
		"116 142 116 118 118 142",
		"86 116 86 104 104 116",
		"86 102 86 103 102 103",
		"102 127 102 115 115 127",
		"142 157 142 158 157 158",
		"157 185 157 170 170 185",
		"185 187 187 198",
		"128 187 128 172 172 187",
		"82 140 82 101 101 114 114 132 132 140",
		"82 100 81 82 81 100",
		"63 100 63 80 80 100",
		"156 184 156 169 169 184",
		"153 184 153 167 167 168 168 184",
		"100 111 100 112 111 112",
		"124 153 124 138 138 152 152 153",
		"180 184 180 193 193 194 194 195 184 196",
		"180 190 190 192",
		"165 191 165 179 179 191",
		"123 165 123 149 149 165",
		"163 191 163 178 178 191",
		"163 176 163 177 176 177",
		"123 144 123 137 137 147 146 147 145 146 144 145",
		"122 144 121 122 120 121 120 144"	
		};
		for (int i=0; i<underground.length; i++){
			String line = underground[i];
			String[] names = line.split(" ");
			Set<Edge> edges = getedge(names[0], names[1], graph);
			for(int n=2; n<(names.length - 1);){
				for(Edge e:edges){
					if(e.type().equals(Edge.EdgeType.Underground)) e.addbetween(getedge(names[n], names[n+1], graph));

				}
				n += 2;
			}
		}
		for(Edge e:new ArrayList<Edge>(es)){
			if(e.type().equals(Edge.EdgeType.Underground) && e.between() != null){
				for(Edge x:new ArrayList<Edge>(e.between())){
					if(x.type().equals(Edge.EdgeType.LocalRoad) || x.type().equals(Edge.EdgeType.MainRoad)) es.remove(x);

				}
			}
		}

		for (int t=0; t<mainroad.length; t++){
			String line2 = mainroad[t];
			String[] names2 = line2.split(" ");
			Set<Edge> edges2 = getedge(names2[0], names2[1], graph);
			for(int f=2; f<(names2.length - 1);){
				for(Edge e2:edges2){
					if(e2.type().equals(Edge.EdgeType.MainRoad)) e2.addbetween(getedge(names2[f], names2[f+1], graph));

				}
				f += 2;
			}
		}
		for(Edge e2:new ArrayList<Edge>(es)){
			if(e2.type().equals(Edge.EdgeType.MainRoad) && e2.between() != null){
				for(Edge x2:new ArrayList<Edge>(e2.between())){
					if(x2.type().equals(Edge.EdgeType.LocalRoad)) es.remove(x2);
				}
			}
		}
		return es;
	}
		
}

