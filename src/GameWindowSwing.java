/** This is part of the GUI is for entering the IP address you want to connect to.
 * 
 * 
 * @author George P Burslem
 * @version 1.0
 * @release 22/03/2016
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class GameWindowSwing extends JFrame{
	
	JFrame main = new JFrame();
	JPanel panelActions = new JPanel();
	JPanel panelMove = new JPanel();
	JPanel panelScreen = new JPanel();
	JButton helloBtn = new JButton("HELLO");
	JButton lookBtn = new JButton("LOOK");
	JButton pickupBtn = new JButton("PICKUP");
	JButton northBtn = new JButton("NORTH");
	JButton southBtn = new JButton("SOUTH");
	JButton eastBtn = new JButton("EAST");
	JButton westBtn = new JButton("WEST");
	JButton quitBtn = new JButton("QUIT");
	JLabel moveLbl = new JLabel("MOVE");
	JTextArea gameScreen = new JTextArea(30,30);
	JScrollPane scrollScreen = new JScrollPane(gameScreen);
	boolean inputEntered;
	String userInput;
	String newline = "\n";
	
	/*
	 * Constructor for the window.
	 */
	public GameWindowSwing() {
		super("Dungeons of Dooom");
		panelActions.setLayout(new GridBagLayout());
		panelMove.setLayout(new BorderLayout());
		panelScreen.setLayout(new FlowLayout());
	}
	
	public void displayGameWindow() {
		setSize(500,400);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		scrollScreen.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
		panelActions.setBackground(Color.blue);
		panelMove.setBackground(Color.orange);
		panelScreen.setBackground(Color.red);
	

		GridBagConstraints gbcActions = new GridBagConstraints();
		gbcActions.gridx = 0;
		gbcActions.gridy = 0;
		panelActions.add(helloBtn, gbcActions);
		gbcActions.gridx = 1;
		gbcActions.gridy = 0;
		panelActions.add(lookBtn, gbcActions);
		gbcActions.gridx = 0;
		gbcActions.gridy = 1;
		panelActions.add(pickupBtn, gbcActions);
		gbcActions.gridx = 1;
		gbcActions.gridy = 1;
		panelActions.add(quitBtn, gbcActions);
		
		panelMove.add(moveLbl, BorderLayout.CENTER);
		panelMove.add(northBtn, BorderLayout.NORTH);
		panelMove.add(eastBtn, BorderLayout.EAST);
		panelMove.add(southBtn, BorderLayout.SOUTH);
		panelMove.add(westBtn, BorderLayout.WEST);
		
		panelScreen.add(scrollScreen);
		
		gameScreen.setEditable(true);
	
		pack();
		setVisible(true);
		
	}
	
	public void setUserInput() {
		inputEntered = false;
		while (inputEntered == false) {
			helloBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userInput = "HELLO";
					inputEntered = true;
					}
			});
			lookBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userInput = "LOOK";
					inputEntered = true;
					}
			});
			pickupBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userInput = "PICKUP";
					inputEntered = true;
					}
			});
			northBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userInput = "MOVE NORTH";
					inputEntered = true;
					}
			});
			southBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userInput = "MOVE SOUTH";
					inputEntered = true;
					}
			});
			eastBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userInput = "MOVE EAST";
					inputEntered = true;
					}
			});
			westBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userInput = "MOVE WEST";
					inputEntered = true;
					}
			});
			quitBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userInput = "QUIT";
					inputEntered = true;
					setVisible(false);
					}
			});
		}
	}	
	
	public String getUserInput() {
		return userInput.toUpperCase();
	}
	
	public void updateScreen(String answer) {
		gameScreen.append(answer + newline);
	}
}
