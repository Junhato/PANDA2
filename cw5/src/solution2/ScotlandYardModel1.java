package solution;

import scotlandyard.*;

import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.Map;
import java.util.Set;

public class ScotlandYardModel1 extends ScotlandYard {
	protected Colour player = Colour.Black;
	protected List<Colour> players = new ArrayList<Colour>();
	protected Map<Colour, Integer> maplocation = new HashMap<Colour, Integer>();
	protected Map<Colour, Map<Ticket, Integer>> mapticket = new HashMap<Colour, Map<Ticket, Integer>>();
	protected Map<Colour, Player> mapplayer = new HashMap<Colour, Player>();
	protected int round = 0;
	protected List<Boolean> rs = new ArrayList<Boolean>();

	protected Graph graph;
	    public void ScotlandYardModel(int numberOfDetectives, List<Boolean> rounds, String graphFileName) throws IOException {
        super(numberOfDetectives, rounds, graphFileName);
	ScotlandYardGraphReader load = new ScotlandYardGraphReader();
	graph = load.readGraph(graphFileName);
	rs = rounds;
    }
	

    @Override
    protected Move getPlayerMove(Colour colour) {
        return null;
    }
	    @Override
    protected void nextPlayer() {
	int i = players.indexOf(player);
	if (i == players.size() -1) player = Colour.Black;
	else player = players.get(i+1);
    }

    @Override
    protected void play(MoveTicket move) {

    }

    @Override
    protected void play(MoveDouble move) {

    }

    @Override
    protected void play(MovePass move) {

    }

    @Override
    protected List<Move> validMoves(Colour player) {
	Object location = getPlayerLocation(player);
	System.out.println(location);
	List<Edge> edges = graph.getEdges(location);
	List<Move> moves = new ArrayList<Move>();
	for(Edge edge:edges){
		Route route = (Route) edge.data();
		Ticket ticket = Ticket.fromRoute(route);
		if (getPlayerTickets(player, ticket) > 0){

			Move target = new MoveTicket(player, (Integer) edge.other(location), ticket);
			moves.add(target);
		}
	}
        return moves;
    }

    @Override
    public void spectate(Spectator spectator) {

    }

    @Override
    public boolean join(Player player, Colour colour, int location, Map<Ticket, Integer> tickets) {
	if (players.contains(colour) == false && players.size() < 5){
		if(colour.equals(Colour.Black));
		else players.add(colour);
		maplocation.put(colour, location);
		mapticket.put(colour, tickets);
		mapplayer.put(colour, player);
		return true;
	}
        else return false;
    }

    @Override
    public List<Colour> getPlayers() {
        return players;
    }

    @Override
    public Set<Colour> getWinningPlayers() {
        return null;
    }

    @Override
    public int getPlayerLocation(Colour colour) {
	System.out.println(getRounds().get(round) );
        return maplocation.get(colour);
    }

    @Override
    public int getPlayerTickets(Colour colour, Ticket ticket) {
	Map<Ticket, Integer> map = mapticket.get(colour);
	return map.get(ticket);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public Colour getCurrentPlayer() {
	mapplayer.get(player).notify(getPlayerLocation(player), validMoves(player)); 
	if (player.equals(Colour.Black)) round ++;
	
        return player;
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public List<Boolean> getRounds() {
        return rs;
    }
	public void getValidMoves(){
		int location = getPlayerLocation(player);
		int mrx = getPlayerLocation(Colour.Black);
	}
		

}
