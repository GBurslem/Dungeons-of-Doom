/** This is part of the GUI is for entering the IP address you want to connect to.
 * 
 * 
 * @author George P Burslem
 * @version 1.0
 * @release 22/03/2016
 */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MapSelectSwing extends JFrame{
	
	JPanel panel = new JPanel();
	JLabel mapLbl = new JLabel("Choose Map: ");
	JButton loginButton = new JButton("Enter");
	boolean mapSet;
	String map;
	private String[] maps = {"Default"};
	JComboBox mapCmbBox = new JComboBox(maps);

	
	/*
	 * Constructor for the window.
	 */
	public MapSelectSwing() {
		super("DoD - Map Selection");
		panel.setLayout(new FlowLayout());
	}
	
	public void displayMapSelectSwing() {
		panel.add(mapLbl);
		panel.add(mapCmbBox);
		panel.add(loginButton);
		setSize(400,65);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(panel);
		setVisible(true);
	}
	
	public void setMap() {
		mapSet = false;
		while (mapSet == false) {
			loginButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!(mapCmbBox.getSelectedItem().toString().equals(null))) {
					map = mapCmbBox.getSelectedItem().toString();
					mapSet = true;
					setVisible(false);
					}
				}
			});
		}
	}	
	
	public String getMap() {
		return map;
	}
}
