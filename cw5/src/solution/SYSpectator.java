package solution;

import scotlandyard.*;

public class SYSpectator implements Spectator {

public Move playedMove;
public Colour currentPlayer;

@Override
public void notify(Move move) {
	 playedMove = move;
	 currentPlayer = move.colour;
}

}
