
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;



public class PlayGame {
	
	protected Client ClientLogic;
	protected Scanner userInput;
	protected static ClientGUI ui = new ClientGUI();

	/**
	 * Main method. Run by the user, and allows them to enter the game. 
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String [] args) throws UnknownHostException, IOException {
		PlayGame game = new PlayGame();
		ui.setupMap();
		game.selectMap(ui.getMap());
		game.update();
	}
	
	/**
	 * Constructor. Sets up the IP and port to be connected to. 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public PlayGame() throws UnknownHostException, IOException{
		ui.setupNetwork();
		ui.ipSelection();
		ui.portSelection();
		ClientLogic = new Client("localhost", 40004); //ui.ipSelection(), ui.portSelection()
		userInput = new Scanner(System.in);
	}
	
	/**
	 * Reads user input from the console.
	 * @return
	 */
	public String readUserInput(){
		return userInput.nextLine();
	}
	
	/**
	 * Main loop of the game while the client is still connected. Input is read from the user, sent to the server and then
	 * the response is read and printed. 
	 * @throws IOException
	 */
	public void update() throws IOException{
		String answer = "";
		while (ClientLogic.gameRunning()){
			// Display main game screen.
			ui.displayGame();
			// Retrieve the button that has been pressed. 
			answer =  ui.getUserInput();
			//Send input from user to server.
			ClientLogic.Send(answer);
			//Read response to be printed from the server. 
			answer = ClientLogic.Read(answer.toUpperCase());
			printAnswer(answer);
		}
	}
	
	/**
	 * Simple print method. 
	 * @param answer
	 * 		String to be printed. 
	 */
	protected void printAnswer(String answer) {
		// Update game screen.
		ui.serverResponse(answer);
	}
	
	/**
	 * Sets the map using the file path provided.
	 * @param mapName
	 * 		File path to map.
	 */
	public void selectMap(String mapName){
		ClientLogic.setMap(new File(mapName));
	}
	
	/**
	 * Parsing and Evaluating the User Input.
	 * @param readUserInput 
	 * 		Input the user has generated. 
	 * @return answer
	 * 		Answer that the user is attempting to send. 
	 */
	protected String parseCommand(String readUserInput) {
		
		String [] command = readUserInput.trim().split(" ");
		String answer = "FAIL";
		
		switch (command[0].toUpperCase()){
		case "HELLO":
			answer = hello();
			break;
		case "MOVE":
			if (command.length == 2 )
			answer = move(command[1].charAt(0));
			break;
		case "PICKUP":
			answer = pickup();
			break;
		case "LOOK":
			answer = look();
			break;
		case "QUIT":
			ClientLogic.quitGame();
		default:
			answer = "FAIL";
		}
		
		return answer;
	}

	/**
	 * Hello method not utilized now that the Server is being used. 
	 * @return
	 */
	public String hello() {
		return ClientLogic.hello();
	}

	/**
	 * Method method not utilized now that the Server is being used. 
	 * @return
	 */
	public String move(char direction) {
		return ClientLogic.move(direction);
	}

	/**
	 * Pickup method not utilized now that the Server is being used. 
	 * @return
	 */
	public String pickup() {
		return ClientLogic.pickup();
	}

	/**
	 * Look method not utilized now that the Server is being used. 
	 * @return
	 */
	public String look() {
		return ClientLogic.look();
	}

}
