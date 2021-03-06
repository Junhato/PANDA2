package solution;

import scotlandyard.*;

import java.io.IOException;
import java.util.*;

public class ScotlandYardModel extends ScotlandYard {

    protected int numberOfDetectives;
    protected List<Boolean> rounds;
    private String graphFileName;
    protected Graph<Integer, Route> currentGraph;
    protected Map<Colour, Player> gameMembers;
    protected int currentRound;
    protected ArrayList<Colour> orderofPlayer;
    private boolean start = false;
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
	currentRound = 0;
	ScotlandYardGraphReader Reader = new ScotlandYardGraphReader();
	currentGraph = Reader.readGraph(graphFileName);
	this.numberOfDetectives = numberOfDetectives;
	gameMembers = new HashMap<Colour, Player>();
	orderofPlayer = new ArrayList<Colour>();
    }

    @Override
    protected Move getPlayerMove(Colour colour) {
	//Since I am not checking more here I assume that I give a list of only valid moves.
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
//testTheVisibilityOfTheRoundsShouldCorrespondToTheInputRounds
    @Override
    protected void play(MoveTicket move) {
	if (!getCurrentPlayer().equals(move.colour)) {
		System.out.println("Wrong player's move");
		System.exit(-1);
	}
	else {
		gameMembers.get(move.colour).setLocation(move.target);
		gameMembers.get(move.colour).removeTicket(move.ticket);
		if (!move.colour.equals(Colour.Black)) {
			gameMembers.get(Colour.Black).addTicket(move.ticket);
		}
		else {
			if(!move.ticket.equals(Ticket.SecretMove)) System.out.println(move.ticket.toString());
		}
	currentRound = currentRound + 1;			
	}
	nextPlayer();
    }

    @Override
    protected void play(MoveDouble move) {
	if (!moves.get(0).colour.equals(Colour.Black) || !moves.get(0).coulour.equals(moves.get(1).colour) || !getCurrentPlayer().equals(move.moves.get(0).colour)) {
		System.out.println("Wrong player's move");
		System.exit(-1);
	}
	else {
		gameMembers.get(move.colour).setLocation(move.moves.get(0).target());
		gameMembers.get(move.colour).removeTicket(move.moves.get(0).ticket());
		currentRound = currentRound + 1;
		gameMembers.get(move.colour).setLocation(move.moves.get(1).target());
		gameMembers.get(move.colour).removeTicket(move.moves.get(1).ticket());
		currentRound = currentRound + 1;
		if(!move.ticket.equals(Ticket.SecretMove)) System.out.println(move.moves.get(0).ticket.toString());
		if(!move.ticket.equals(Ticket.SecretMove)) System.out.println(move.moves.get(1).ticket.toString());
	}
	nextPlayer();
    }

    @Override
    protected void play(MovePass move) {
	if (getCurrentPlayer().equals(move.colour)) nextPlayer();
	else {
		System.out.println("Wrong player's move");
		System.exit(-1);
	}

    }

    @Override
    protected List<Move> validMoves(Colour player) {
	valid = new ArrayList<Move>();
	int currentPosition = getPlayerLocation(player);
	Map<Ticket, Integer> currenttickets =  gameMembers.get(player).getTickets();
	List<Edge> availableRoute = currentGraph.getEdges(Node(currentPosition));
	for (Edge e: availableRoute) {

		if (currenttickets.get(fromRoute(e.data())) != null && currenttickets.get(fromRoute(e.data())) != 0) {
			//check other player's location except Mr.X
			for (Colour c : orderofPlayer) {
				if (!c.equals(Colour.Black) && (Integer)e.target().equals(getPlayerLocation(c)))
					continue;
			}
			MoveTicket single = new MoveTicket(player, (Integer)e.target(), fromRoute(e.data()));
			valid.add(single);
			if (player.equals(Colour.Black) && currenttickets.get(SecretMove) != 0) {
				MoveTicket secret = new MoveTicket(player, (Integer)e.target, Ticket.SecretMove);
				valid.add(secret);
			}
		}
		if (player.equals(Colour.Black) && currenttickets.get(Ticket.DoubleMove) != 0) {
			for (Edge edge : currentGraph.getEdges(Node(e.target()))) {
			//can Mr.X go somewhere and come back as two moves?
			//if use the same routetype but only one ticket
				if (currenttickets.get(fromRoute(edge.data())) != 0 && currenttickets.get(fromRoute(edge.data())) != 0 ) {
					if (e.data.equal(edge.data()) && currenttickets.get(fromRoute(e.data())) -1 == 0) continue;
					for (Colour c : orderofPlayer) {
						if (!c.equals(Colour.Black) && (Integer)edge.target().equals(getPlayerLocation(c)))
						continue;
					}
					firstoption = new MoveTicket(player, (Integer)edge.target(), fromRoute(edge.data()));
					valid.add(new MoveDouble(player, single, firstoption));

					if (currenttickets.get(Ticket.SecretMove) != 0) {
						secondoption = new MoveTicket(player, (Integer)edge.target(), Ticket.SecretMove);
						valid.add(new MoveDouble(player, secret, secondoption));
					}
				}
			}
		}

	}
	//add a pass move
	if (!player.equals(Colour.Black)) valid.add(new MovePass(player));
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
	if (numberOfDetectives ==0 && !colour.equals(Colour.Black)) {
		System.out.println("The only player is Mr.X");
		return false;
	}
	if (orderofPlayer.isEmpty() && gameMembers.isEmpty() && !colour.equals(Colour.Black)) {
		System.out.println("The first player should be Mr.X");
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
	gameMembers.put(colour, new SYPlayer(location, tickets));
	orderofPlayer.add(colour);
	return true;
    }

    @Override
    public List<Colour> getPlayers() {
	return orderofPlayer;
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
	start = true;
	return true;
    }

    @Override
    public Colour getCurrentPlayer() {
	if (getRound() == 0 && start) {
		currentPlayer = orderofPlayer.indexOf(Colour.Black);
		start = false;
	}
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
