package solution;

import scotlandyard.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScotlandYardModel extends ScotlandYard {

    protected int numberOfDetectives;
    protected ArrayList<Boolean> rounds;
    private String graphFileName;
    protected Graph<Integer, Route> currentGraph;
    protected MrX _MrX;
    protected Map<Colour, Player> gameMembers;
    protected int currentRound;
    protected ArrayList<Colour> orderofPlayer;
    protected int currentPlayer;
    protected Move currentmove;
    protected ArrayList<Move> valid;

    public ScotlandYardModel(int numberOfDetectives, List<Boolean> rounds, String graphFileName) throws IOException {
        super(numberOfDetectives, rounds, graphFileName);
	if (numberOfDetectives < 0 || numberOfDetectives > 5) {
		System.out.println("Number of detectives entered is invalid");
		System.exit(-1);
	}
	//game initialization
	this.graphFileName = graphFileName;
	this.rounds = rounds;
	totalnbofRounds = rounds.size();
	currentGraph = readGraph(graphFileName);
	this.numberOfDetectives = numberOfDetectives;
	gameMembers = new HashMap<Colour, Player>();
	orderofPlayer = new ArrayList<Colour>();
    }

    @Override
    protected Move getPlayerMove(Colour colour) {
	if (!colour.equals(orderofPlayer.get(currentPlayer))) {
		System.out.println("Not this player's turn");
		return null;
	}
	if (valid.contains(gameMembers.get(colour).getMove())) {
		System.out.println("Invalid move");
		return null;
	}
	return currentmove;
    }

    @Override
    protected void nextPlayer() {
	currentPlayer = (currentPlayer + 1) % (numberOfDetectives+1);
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
	valid = new ArrayList<Move>();
	int currentPosition = getPlayerLocation(player);
	//case for Mr.X

	//case for detectives
	List<Edge<X, Y>> availableRoute = currentGraph.getEdges(Node(currentPosition));
	for (Edge<X, Y> e: availableRoute) {
		if (getTickets().get((Ticket)e.data) != 0)
		valid.add(new MoveTicket(player, (Integer)e.target, (Ticket)e.data));
	}
	//add a pass move
	valid.add(new MovePass(player));
	currentmove = player.notify(player.getLocation(), valid);
        return valid;
    }

    @Override
    public void spectate(Spectator spectator) {

    }

    @Override
    public boolean join(Player player, Colour colour, int location, Map<Ticket, Integer> tickets) {
        if (gameMembers.size() > numberOfDetectives + 1) {
		System.out.println("The game is already full");
		return false;
	}
	if (numberOfDetectives ==0 && !colour.equals(Black)) {
		System.out.println("The only player is Mr.X");
		return false;
	}
	for (Color c : gameMembers.keySet()) {
		if (c.equals(colour)) {
			System.out.println("This color already belong to a detective");
			return false;
		}
	// testing for two person starting at the same location don't know if it is necessary
		if (get(c).getLocation() == location) {
			System.out.println("There is already a detective at this location");
			return false;
		}
	}
	colour.equals(Black) ? gameMembers.put(colour, MrX.getInstance(location, tickets)) : gameMembers.put(colour, new Detective(location, tickets));
	orderofPlayer.add(colour);
        return true;
    }

    @Override
    public List<Colour> getPlayers() {
	ArrayList<Colour> currentPlayers = new ArrayList<Colour>();
	for (Color c : gameMembers.keySet()) {
		currentPlayers.add(c);
	} 
        return currentPlayers;
    }

    @Override
    public Set<Colour> getWinningPlayers() {
        return null;
    }

    @Override
    public int getPlayerLocation(Colour colour) {
	if (gameMembers.get(colour) != null) {
		return gameMembers.get(colour).getLocation();
	} 
        return -1;
    }

    @Override
    public int getPlayerTickets(Colour colour, Ticket ticket) {
	if (gameMembers.get(colour) != null) {
		return gameMembers.get(colour).getTickets().get(ticket);
	} 
        return -1;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public boolean isReady() {
	if (gameMembers.size() != numberOfDetectives + 1) return false;
	return true;
    }

    @Override
    public Colour getCurrentPlayer() {
	if (getRound() == 1) {
		return orderofPlayer.indexOf(Black);
	}
	currentPlayer = orderofPlayer.indexOf(currentRound % (numberOfDetectives+1) -1);
	return orderofPlayer.get(currentPlayer);
    }

    @Override
    public int getRound() {	
        return currentRound;
    }

    @Override
    public List<Boolean> getRounds() {
        return rounds;
    }
}
