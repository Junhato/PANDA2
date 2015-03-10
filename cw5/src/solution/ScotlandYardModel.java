package solution;

import scotlandyard.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScotlandYardModel extends ScotlandYard {

    protected int numberOfDetectives;
    protected List<Boolean> rounds;
    private String graphFileName;
    protected Graph<Integer, Route> currentGraph;
    protected MrX _MrX;
    protected HashMap<Colour, Player> gameMembers;

    public ScotlandYardModel(int numberOfDetectives, List<Boolean> rounds, String graphFileName) throws IOException {
        super(numberOfDetectives, rounds, graphFileName);
	if (numberOfDetectives < 0 || numberOfDetectives > 5) {
		System.out.println("Number of detectives entered is invalid");
		System.exit(-1);
	}
	//game initialization
	this.graphFileName = graphFileName;
	this.rounds = rounds;
	currentGraph = readGraph(graphFileName);
	this.numberOfDetectives = numberOfDetectives;
	gameMembers = new HashMap<Colour, Player>();
    }

    @Override
    protected Move getPlayerMove(Colour colour) {
        return null;
    }

    @Override
    protected void nextPlayer() {

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
        return null;
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
		if (get(c).location == location) {
			System.out.println("There is already a detective at this location");
			return false;
		}
	}
	colour.equals(Black) ? gameMembers.put(colour, MrX.getInstance(location, tickets)) : gameMembers.put(colour, new Detective(location, tickets));
        return true;
    }

    @Override
    public List<Colour> getPlayers() {
	
        return null;
    }

    @Override
    public Set<Colour> getWinningPlayers() {
        return null;
    }

    @Override
    public int getPlayerLocation(Colour colour) {
        return 0;
    }

    @Override
    public int getPlayerTickets(Colour colour, Ticket ticket) {
        return 0;
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
        return null;
    }

    @Override
    public int getRound() {
        return 0;
    }

    @Override
    public List<Boolean> getRounds() {
        return null;
    }
}
