import solution.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.*;

import scotlandyard.*;

public class ScotlandYardApp {
	public static void main (String[] args) {
		gameView view = new gameView();
 		gameController controller = new gameController(view);
		view.setVisible(true);
	}
}

