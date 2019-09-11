/** This is the GUI used by the client and implements the DungeonsUI.
 * 
 * 
 * @author George P Burslem
 * @version 1.0
 * @release 22/03/2016
 */

public class ClientGUI implements DungeonsUI{
	
	//Allows methods to utilize their corresponding windows. 
	NetworkSelectSwing networkWindow = new NetworkSelectSwing();
	MapSelectSwing mapWindow = new MapSelectSwing();
	GameWindowSwing gameWindow = new GameWindowSwing();
	
	/*
	 * Displays network screen and waits for login to be pressed. 
	 */
	public void setupNetwork() {
		networkWindow.displayNetworkSelectSwing();
		networkWindow.setNetworkDets();
	}
	
	/*
	 * Returns IP address that has been entered. 
	 */
	public String ipSelection() {
		return networkWindow.getIP();
	}
	
	/*
	 * Returns the port number that has been entered. 
	 */
	public int portSelection() {
		return networkWindow.getPort();
	}

	/*
	 * Displays the map selection screen and awaits selection. 
	 */
	public void setupMap() {
		mapWindow.displayMapSelectSwing();
		mapWindow.setMap();
	}
	
	/*
	 * Returns the map selected by the user. 
	 */
	public String getMap() {
		return mapWindow.getMap();
	}
	
	public void displayGame() {
		gameWindow.displayGameWindow();
		gameWindow.setUserInput();
	}
	
	public String getUserInput() {
		return gameWindow.getUserInput();
	}
	
	public void serverResponse(String answer) {
		gameWindow.updateScreen(answer);
	}
}
