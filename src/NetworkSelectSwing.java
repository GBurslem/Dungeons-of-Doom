/** This is part of the GUI is for entering the IP address you want to connect to.
 * 
 * 
 * @author George P Burslem
 * @version 1.0
 * @release 22/03/2016
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NetworkSelectSwing extends JFrame{
	
	JPanel panel = new JPanel();
	JLabel ipLbl = new JLabel("Enter Server IP: ");
	JLabel portLbl = new JLabel("Enter Port Number: ");
	JTextField ipField = new JTextField(10);
	JTextField portField = new JTextField(10);
	JButton loginButton = new JButton("Log In");
	boolean ipSet;
	boolean portSet;
	String ip;
	int port;
	
	/*
	 * Constructor for the window.
	 */
	public NetworkSelectSwing() {
		super("DoD - Network Selection");
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
	}
	
	public void displayNetworkSelectSwing() {
		panel.add(ipLbl);
		panel.add(ipField);
		panel.add(portLbl);
		panel.add(portField);
		panel.add(loginButton);
		setSize(400,300);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(panel);
		setVisible(true);
	}
	
	public void setNetworkDets() {
		ipSet = false;
		while (ipSet == false) {
			loginButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if ( !(ipField.getText().equals(null)) && !(portField.getText().equals(null)) ) {
					ip = ipField.getText();
					port = Integer.parseInt(portField.getText());
					ipSet = true;
					setVisible(false);
					}
				}
			});
		}
	}	
	
	public String getIP() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
}
