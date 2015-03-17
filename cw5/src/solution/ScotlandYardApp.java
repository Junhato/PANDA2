import solution.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.*;

import scotlandyard.*;

public class ScotlandYardApp {
	public static void main (String[] args) {
		
 		gameStart start = new gameStart();
 		gameStartController startController = new gameStartController(start);
		start.setVisible(true);
	}
}
