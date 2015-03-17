package solution;

import scotlandyard.*;

public class SYSpectator implements Spectator {

//public Spectator;
public Move currentmove;
public int location;

@Override
public void notify(Move move) {
	 currentmove = move;
	 // location = getPlayerLocation(move.colour);
}

}
