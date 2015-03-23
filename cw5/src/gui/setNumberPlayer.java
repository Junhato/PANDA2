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
	private Integer[] numbers = {2, 3, 4, 5, 6};
	private JButton numberButton;
	private JButton loadButton;
	private JTextField loadField;

	public setNumberPlayer(){
		Box box = Box.createVerticalBox();
		Box boxh1 = Box.createHorizontalBox();
		Box boxh2 = Box.createHorizontalBox();
		Box boxv = Box.createVerticalBox();
		
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
		comboPanel.setMaximumSize(new Dimension(100, 30));

		JPanel loadPanel = new JPanel();
		loadPanel.setMaximumSize(new Dimension(300, 50));

		loadButton = new JButton("Load privious game");
		loadField = new JTextField("");
		loadField.setMaximumSize(new Dimension(100, 30));
		
		//loadPanel.add(loadField);
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		//this.setSize(1200, 900);
		this.setSize(300, 200);

		numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.PAGE_AXIS));

		//this.add(new JLabel("How many are playing?"));
		
		numberPanel.setMinimumSize(new Dimension(300, 200));
		numberField.setMaximumSize(new Dimension(100, 50));

		numberPanel.add(new JLabel("How many are playing?"));
		boxh1.add(numberPanel);
		boxh1.add(numberField);
		//boxvl.add(numberButton);
		
		boxh2.add(loadButton);
		boxh2.add(loadField);

		box.add(boxh1);
		box.add(boxh2);
		boxv.add(box);
		boxv.add(numberButton);

		/*comboPanel.add(numberField);
		numberPanel.add(comboPanel);
		numberPanel.add(numberButton);

		numberPanel.add(loadButton);
		numberPanel.add(loadPanel);*/
		
		this.add(boxv);
		//this.add(numberButton);
		
	}

	public int getNumber(){
		return (int) numberField.getSelectedItem();
	}

	public String getSaveNumber(){
		return loadField.getText();
	}

	public void addNumberListener(ActionListener listenNumberButton) {

		numberButton.addActionListener(listenNumberButton);
	} 
	public void addLoadListener(ActionListener listenLoadButton){
		loadButton.addActionListener(listenLoadButton);
	}
}
