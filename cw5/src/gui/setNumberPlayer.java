package solution;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.lang.*;
import java.awt.image.BufferedImage;
import javax.swing.JTextField;

public class setNumberPlayer extends JFrame{
	private JComboBox<Integer> numberField;
	private Integer[] numbers = {2, 3, 4, 5};
	private JButton numberButton;
	private JButton loadButton;
	private JTextField loadField;

	public setNumberPlayer(){
		numberField = new JComboBox<Integer>(numbers);
		//numberField.setMaximumSize(new Dimension(100, 20) );
		
		numberButton = new JButton("New Game");
		
		JPanel numberPanel = new JPanel();
		//numberPanel.setLayout(new FlowLayout());
		/*numberPanel.setMinimumSize(new Dimension(500, 200));
		numberPanel.add(new JLabel("How many are playing?"));
		numberPanel.add(numberField);
		numberPanel.add(numberButton);*/
		JPanel comboPanel = new JPanel();
		comboPanel.setMaximumSize(new Dimension(300, 50));
		
		loadButton = new JButton("Load privious game");
		loadField = new JTextField("");

		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		//this.setSize(1200, 900);
		this.setSize(300, 200);

		numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.PAGE_AXIS));

		//this.add(new JLabel("How many are playing?"));
		
		numberPanel.setMinimumSize(new Dimension(300, 200));
		numberField.setMaximumSize(new Dimension(100, 50));

		numberPanel.add(new JLabel("How many are playing?"));
		comboPanel.add(numberField);
		numberPanel.add(comboPanel);
		numberPanel.add(numberButton);

		numberPanel.add(loadButton);
		numberPanel.add(loadField);

		this.add(numberPanel);
		//this.add(numberButton);
		

	}

	public int getNumber(){
		return (int) numberField.getSelectedItem();
	}

	public void addNumberListener(ActionListener listenNumberButton) {

		numberButton.addActionListener(listenNumberButton);
	} 
	public void addLoadListener(ActionListener listenLoadButton){
		loadButton.addActionListener(listenLoadButton);
	}
}
