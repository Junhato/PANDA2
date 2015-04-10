package player;

import scotlandyard.Move;
import scotlandyard.Player;
import scotlandyard.ScotlandYardView;

import scotlandyard.*;
import java.io.*;

import java.util.*;
import java.util.Random;
import java.util.Set;

/**
 * The RandomPlayer class is an example of a very simple AI that
 * makes a random move from the given set of moves. Since the
 * RandomPlayer implements Player, the only required method is
 * notify(), which takes the location of the player and the
 * list of valid moves. The return value is the desired move,
 * which must be one from the list.
 */
public class RandomPlayer implements Player{
	protected ScotlandYardView view;
	protected String graphFilename;
	protected Map<Move, Integer> scores = new HashMap<Move, Integer>();
	protected Map<Integer, List<Integer>> coordinateMap = new HashMap<Integer, List<Integer>>();
	protected Graph currentGraph;
	
    public RandomPlayer(ScotlandYardView view, String graphFilename) throws IOException{
        //TODO: A better AI makes use of `view` and `graphFilename`.
        this.view = view;
        this.graphFilename = graphFilename;
        readFile();
        ScotlandYardGraphReader Reader = new ScotlandYardGraphReader();
		this.currentGraph = Reader.readGraph(graphFilename);
        
    }

    @Override
    public Move notify(int location, Set<Move> moves) {
        //TODO: Some clever AI here ...
		Colour player = view.getCurrentPlayer();
		/*if(player.equals(Colour.Black)){
			Move choosenMove = score(moves);
			if(choosenMove != null) return choosenMove;
		}*/
        int choice = new Random().nextInt(moves.size());
        for (Move move : moves) {
            if (choice == 0) {
                return move;
            }
            choice--;
        }

        return null;
    }
    //AI for MrX
    public Move score(Set<Move> moves){
		List<Colour> players= view.getPlayers();
		
		for(Move move:moves){
			scores.put(move, 0);
		}
		
		distance(moves, players);
		freedom(moves);
		overall(moves);
		
        Move choosenMove = rankMove(moves);
        System.out.println(choosenMove);
        return choosenMove;
	}
	//read pos.text
    public void readFile(){
		File file = new File("../resources/pos.txt");	
			Scanner in = null;
			try 
			{
				in = new Scanner(file);
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println(e);
			}

			
			// get the number of nodes
			String topLine = in.nextLine();
		
			int numberOfNodes = Integer.parseInt(topLine);
			
			
			for(int i = 0; i < numberOfNodes; i++)
			{
				String line = in.nextLine();
		   
				String[] parts = line.split(" ");
				List<Integer> pos = new ArrayList<Integer>();
				pos.add(Integer.parseInt(parts[1]));
				pos.add(Integer.parseInt(parts[2]));
			//System.out.println(pos);
				
				int key = Integer.parseInt(parts[0]);
			//System.out.println(key);

				coordinateMap.put(key, pos);
			}
	}
	//get target of move
	public int targetPos(Move move){
		int target = 0;
			if (move instanceof MoveTicket) {
				target = ((MoveTicket) move).target;
			}
			else if(move instanceof MoveDouble){
				target = ((MoveDouble) move).move2.target;
			}
			else{
				target = view.getPlayerLocation(Colour.Black);
			}
		return target;
	}
    //measure distance to each ditective
    //take the shortest distance 
    public void distance(Set<Move> moves, List<Colour> players){
		for(Move move:moves){
			int target = targetPos(move);
			List<Integer> pos = coordinateMap.get(target);
			int distance = scores.get(move);

			for(Colour player:players){
				int location = view.getPlayerLocation(player);
				List<Integer> co = coordinateMap.get(location);
				if(co != null){
					double value = Math.sqrt((pos.get(0)-co.get(0))*(pos.get(0)-co.get(0))+(pos.get(1)-co.get(1))*(pos.get(1)-co.get(1)));
					distance += (int) value;
					if(move instanceof MoveDouble){
						distance = distance * 2/3;
					}
					if(distance != 0 && (scores.get(move) == 0 || distance < scores.get(move))){
						scores.put(move, distance);
					}
				}
			}
		
		}
	}
	//freedom of move
	//count how many possible moves in next turn
	public void freedom(Set<Move> moves){
		for (Move move:moves){
			int target = targetPos(move);
			
			Set<Edge> edges = currentGraph.getEdges(target);
			int score = 0;
			for(Edge edge:edges){
				Ticket ticket = Ticket.fromRoute((Route)edge.data());
				if(view.getPlayerTickets(Colour.Black, ticket) != 0) score += 1;
				
			}
			score += scores.get(move);
			scores.put(move, score);
		}
	}
	//overall position on the board
	//check how close to the corner
	public void overall(Set<Move> moves){
		for (Move move:moves){
			int target = targetPos(move);
			List<Integer> pos = coordinateMap.get(target);
			int score = 0;
			int upLeft = pos.get(0) + pos.get(1);
			int downLeft = pos.get(0) + 800 - pos.get(1);
			int upRight = 1000 - pos.get(0) + pos.get(1);
			int downRight = 1000 - pos.get(0) + 800 - pos.get(1);
			score = upLeft;
			if(score > downLeft) score = downLeft;
			if(score > upRight) score = upRight;
			if(score > downRight) score = downRight;
			score = score/2;
			score += scores.get(move);
			scores.put(move, score);
		}
	}
	//choose move with highest score
	public Move rankMove(Set<Move> moves){
		Move choice = null;
		int score = 0;
		for(Move move:moves){
			if(scores.get(move) > score){
				choice = move;
				score = scores.get(move);
			}
		}
		return choice;
	}
	
			
		
}
