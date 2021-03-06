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
	protected Map<Integer, List<Integer>> coordinateMap = new HashMap<Integer, List<Integer>>();
	protected Graph currentGraph;
    public RandomPlayer(ScotlandYardView view, String graphFilename){
        //TODO: A better AI makes use of `view` and `graphFilename`.
        this.view = view;
        this.graphFilename = graphFilename;
        readFile();
        ScotlandYardGraphReader Reader = new ScotlandYardGraphReader();
		//this.currentGraph = Reader.readGraph(graphFilename);
        
    }

    @Override
    public Move notify(int location, Set<Move> moves) {
        //TODO: Some clever AI here ...
		score(location, moves);
        int choice = new Random().nextInt(moves.size());
        for (Move move : moves) {
            if (choice == 0) {
                return move;
            }
            choice--;
        }

        return null;
    }
    //print current board score
    public void score(int location, Set<Move> moves){
		//Colour player = view.getCurrentPlayer();
		List<Colour> players = view.getPlayers();
		int score = 0;
		score += distance(moves, location, players);
		score += freedom(moves);
		score += overall(moves, location);
        System.out.println(score);
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

    //distance to detectives
    public int distance(Set<Move> moves, int currentLocation, List<Colour> players){
		List<Integer> pos = coordinateMap.get(currentLocation);
		int score = 0;
			for(Colour player:players){
				int location = view.getPlayerLocation(player);
				List<Integer> co = coordinateMap.get(location);
				if(co != null && pos != null){
					double value = Math.sqrt((pos.get(0)-co.get(0))*(pos.get(0)-co.get(0))+(pos.get(1)-co.get(1))*(pos.get(1)-co.get(1)));
					score += (int) value;
					
					
				}
			}
		return score;
	}
	//freedom of move
	public int freedom(Set<Move> moves){
		return moves.size();
	}
	//overall position on the board
	public int overall(Set<Move> moves, int location){
			List<Integer> pos = coordinateMap.get(location);
			int score = 0;
			if(pos != null){
				int upLeft = pos.get(0) + pos.get(1);
				int downLeft = pos.get(0) + 800 - pos.get(1);
				int upRight = 1000 - pos.get(0) + pos.get(1);
				int downRight = 1000 - pos.get(0) + 800 - pos.get(1);
			
				score = upLeft;
				if(score > downLeft) score = downLeft;
				if(score > upRight) score = upRight;
				if(score > downRight) score = downRight;
			}
			return score;
	}		
}
