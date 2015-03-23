package solution;

import scotlandyard.*;

import java.io.IOException;
import java.util.*;

public class ScotlandYardModel extends ScotlandYard {
    //Spectators??
    protected int numberOfDetectives, currentPlayer, currentRound;
    protected List<Boolean> rounds;
    protected String graphFileName;
    protected Graph<Integer, Route> currentGraph;
    protected Map<Colour, SYPlayer> gameMembers;
    protected ArrayList<Colour> orderofPlayer;
    protected boolean isWinnerMrX;
    protected Move currentmove;
    protected List<String> MrXTrace;
    protected SYPlayer MrX;
    protected ArrayList<Spectator> spectators;
    protected ArrayList<Integer> initiallocation;

    public ScotlandYardModel(int numberOfDetectives, List<Boolean> rounds, String graphFileName) throws IOException {
        super(numberOfDetectives, rounds, graphFileName);
	if (numberOfDetectives < 0 || numberOfDetectives > 5) {
		System.out.println("Number of detectives entered is invalid");
	}
	else {
	//game initialization
	this.graphFileName = graphFileName;
	this.rounds = rounds;
	currentRound = 0;
	ScotlandYardGraphReader Reader = new ScotlandYardGraphReader();
	currentGraph = Reader.readGraph(graphFileName);
	this.numberOfDetectives = numberOfDetectives;
	gameMembers = new HashMap<Colour, SYPlayer>();
	orderofPlayer = new ArrayList<Colour>();
	spectators = new ArrayList<Spectator>();
	MrXTrace = new ArrayList<String>() ;
	currentPlayer = 0;
	isWinnerMrX = false;
	initiallocation = new ArrayList<Integer>();
	}
    }

    @Override
    protected Move getPlayerMove(Colour colour) {
	if (colour == null) {
		System.out.println("Oops, problem happened");
		return null;
	}
	//add a pass move
	List<Move> listofvalidmoves = validMoves(colour);
	if (listofvalidmoves.isEmpty() && !colour.equals(Colour.Black)) listofvalidmoves.add(new MovePass(colour));
	SYPlayer playing = gameMembers.get(colour);
	currentmove = playing.notify(playing.getLocation(true), listofvalidmoves);
	//Since I am not checking more here I assume that I give a list of only valid moves
	if (!colour.equals(getCurrentPlayer())) {
		System.out.println("Not this player's turn");
		return null;
	}
	if (!listofvalidmoves.contains(playing.getMove())) {
		System.out.println("Invalid move");
		return null;
	}
//the last move in the list should be the pass move
	if (currentmove == null) return listofvalidmoves.get(listofvalidmoves.size() - 1);
	return currentmove;
    }
    @Override
    protected void nextPlayer() {

	currentPlayer = (currentPlayer + 1) % (numberOfDetectives + 1);
    }

    protected void previousPlayer() {
	if (currentPlayer == 0) currentPlayer = numberOfDetectives;
	else currentPlayer = currentPlayer - 1;
    }
    @Override
    protected void play(MoveTicket move) {
	if (!getCurrentPlayer().equals(move.colour)) {
		System.out.println("Wrong player's move");
	}
	else {
		SYPlayer current = gameMembers.get(move.colour);
		current.setLocation(move.target, rounds.get(currentRound));
		current.removeTicket(move.ticket);
		if (!move.colour.equals(Colour.Black)) {
			MrX.addTicket(move.ticket);
		}
		else {
			if(!move.ticket.equals(Ticket.SecretMove)) System.out.println(move.ticket.toString());
			else MrX.removeTicket(move.ticket);
			//to notify spectator with correct location of Mr.X
			if (!rounds.get(currentRound)) move = new MoveTicket(move.colour, getPlayerLocation(move.colour), move.ticket);
			currentRound = currentRound + 1;
			MrXTrace.add(move.toString());
		}			
	}
	for (Spectator s : spectators) {
		s.notify(move);
	}
    }

    @Override
    protected void play(MoveDouble move) {
	MoveTicket first = (MoveTicket)move.moves.get(0);
	MoveTicket second = (MoveTicket)move.moves.get(1);

	for (Spectator s: spectators) {
		s.notify(move);
	}
	play(first);
	play(second);
	MrX.removeTicket(Ticket.DoubleMove);
    }

    @Override
    protected void play(MovePass move) {
	if (!getCurrentPlayer().equals(Colour.Black) && getCurrentPlayer().equals(move.colour)) {
		for (Spectator s : spectators) {
			s.notify(move);
		}
	}
	else {
		System.out.println("Wrong player's move");
	}
    }

    protected boolean occupied(int location, Colour colour) {
	    for (Colour c : orderofPlayer) {
		if (!c.equals(Colour.Black) && !c.equals(colour)) {
			if (location == getPlayerLocation(c))
				return true;
			}
		}
    return false;	    
    }

    @Override
    protected List<Move> validMoves(Colour player) {
	List<Move> valid = new ArrayList<Move>();
	int currentPosition = gameMembers.get(player).getLocation(true);
	Map<Ticket, Integer> currenttickets = gameMembers.get(player).getTickets();
	List<Edge<Integer, Route>> availableRoute = currentGraph.getEdges(currentPosition);
	for (Edge e: availableRoute) {
		int nbofticket = currenttickets.get(Ticket.fromRoute((Route)e.data()));
		if (currenttickets.get(Ticket.fromRoute((Route)e.data())) != null && nbofticket != 0) {
			int destination = (currentPosition == (int)e.source()) ? (int)e.target() : (int)e.source();
			//check other player's location except Mr.X
			if (occupied(destination, player)) continue;

			MoveTicket single = new MoveTicket(player, destination, Ticket.fromRoute((Route)e.data()));
			valid.add(single);
			MoveTicket secret = null;
			if (player.equals(Colour.Black) && currenttickets.get(Ticket.SecretMove) != 0) {
				secret = new MoveTicket(player, destination, Ticket.SecretMove);
				valid.add(secret);
			}
		//make double moves	
		if (player.equals(Colour.Black) && currenttickets.get(Ticket.DoubleMove) != 0) {
			int newPosition = destination;
			for (Edge edge : currentGraph.getEdges(destination)) {
				
				destination = (newPosition == (int)edge.source()) ? (int)edge.target() : (int)edge.source();
			//can Mr.X go somewhere and come back as two moves?
				if (currenttickets.get(Ticket.fromRoute((Route)edge.data())) != 0) {
					if (e.data().equals(edge.data()) && nbofticket ==1) continue;
					if (occupied(destination, player)) continue;

					MoveTicket firstoption = new MoveTicket(player, destination, Ticket.fromRoute((Route)edge.data()));
					valid.add(new MoveDouble(player, single, firstoption));

					if (secret != null) valid.add(new MoveDouble(player, secret, firstoption));
					MoveTicket secondoption = null;
					if (currenttickets.get(Ticket.SecretMove) != 0) {
						secondoption = new MoveTicket(player, destination, Ticket.SecretMove);
						valid.add(new MoveDouble(player, single, secondoption));
					}
					if (secret != null && secondoption != null && ((int)currenttickets.get(Ticket.SecretMove) -1 > 0))
						valid.add(new MoveDouble(player, secret, secondoption));
				}
			}
		}
		}
	}
	
        return valid;
    }

    @Override
    public void spectate(Spectator spectator) {
	spectators.add(spectator);
    }

    @Override
    public boolean join(Player player, Colour colour, int location, Map<Ticket, Integer> tickets) {
        if (gameMembers.size() >= numberOfDetectives + 1) {
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
	for (Colour c : gameMembers.keySet()) {
		if (c.equals(colour)) {
			System.out.println("This colour already belong to a detective");
			return false;
		}
	// testing for two person starting at the same location don't know if it is necessary
		if (gameMembers.get(c).getLocation(true) == location) {
			System.out.println("There is already a detective at this location");
			return false;
		}
	}
	if (colour.equals(Colour.Black)) {
		MrX = new SYPlayer(player, location, tickets, true);
		gameMembers.put(colour, MrX);
	}
	else {
		gameMembers.put(colour, new SYPlayer(player, location, tickets));
	}
	orderofPlayer.add(colour);
	initiallocation.add(location);
	return true;
    }

    @Override
    public List<Colour> getPlayers() {
	return orderofPlayer;
    }

    @Override
    public Set<Colour> getWinningPlayers() {
	Set<Colour> winner = new HashSet<Colour>();
        if (!isGameOver()) return winner;
	if (isWinnerMrX) {
		winner.add(Colour.Black);
	}
	else {
		for (Colour c : orderofPlayer) {
			if(!c.equals(Colour.Black))
			winner.add(c);
		}
	}
	return winner;
    }

    @Override
    public int getPlayerLocation(Colour colour) {
	if (gameMembers.get(colour) != null) {
		return gameMembers.get(colour).getLocation(rounds.get(currentRound));
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
	int counter = 0;
	if(!isReady()) return false;
	if (getCurrentPlayer().equals(Colour.Black) && validMoves(Colour.Black).isEmpty()) return true;
	if (!getCurrentPlayer().equals(Colour.Black)
	&&  getPlayerLocation(getCurrentPlayer()) == gameMembers.get(Colour.Black).getLocation(true)) return true;
	if (((currentRound + 1) == rounds.size()) && currentPlayer == 0) {
		isWinnerMrX = true;
		return true;
	}
	for (Colour c : orderofPlayer) {
		if (!c.equals(Colour.Black)) {
		Map<Ticket, Integer> currentTickets = gameMembers.get(c).getTickets();
			for (Ticket t : currentTickets.keySet()) {
				if (currentTickets.get(t) != 0) counter++;
			}
		}
	}
	if (counter == 0) {
		isWinnerMrX = true;
		return true;
	}
	for (Colour c : orderofPlayer) {
		if (!c.equals(Colour.Black)) {

			if (!validMoves(c).isEmpty()) return false;
		}
	}
	isWinnerMrX = true;
	return true;
    }

    @Override
    public boolean isReady() {
	if (gameMembers.size() != numberOfDetectives + 1) return false;
	return true;
    }

    @Override
    public Colour getCurrentPlayer() {

	return orderofPlayer.get(this.currentPlayer);
    }

    @Override
    public int getRound() {	
        return currentRound;
    }

    public void setRound(int round) {
	currentRound = round;
   }

    @Override
    public List<Boolean> getRounds() {
        return rounds;
    }
    	//add method
	public Map<Colour, SYPlayer> getMembers(){
		return gameMembers;
	}

}
