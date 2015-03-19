import solution.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.*;

import scotlandyard.*;

public class ScotlandYardApp {
	public static void main (String[] args) {
		setNumberPlayer setNumber = new setNumberPlayer();
		setNumberController ncontroller = new setNumberController(setNumber);
		setNumber.setVisible(true);
	}
}
