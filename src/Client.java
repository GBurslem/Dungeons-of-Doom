
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A Simple Socket client that connects to our socket server
 * @author George Burslem
 *
 */
public class Client implements IGameLogic{

    private String hostname;
    private int port;
	Socket s = new Socket("localhost", 40004);
    
	/**
	 * Constructor to set up the Client.
	 * @param hostname
	 * 		Host IP to be connected to.
	 * @param port
	 * 		Port number to be connected to.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
    public Client(String hostname, int port) throws UnknownHostException, IOException {
    	this.hostname = hostname;
    	this.port = port;
	}
	
    /**
     * Method for sending data to the server. 
     * @param input
     * 		String wanting to be sent to the Server. 
     * @throws IOException
     */
	public void Send(String input) throws IOException {
		PrintWriter dataToServer = new PrintWriter(s.getOutputStream(), true);
		// Printing to the Server. 
		dataToServer.println(input);
	}
	
	/**
	 * Method to read from the Server.
	 * @param answer
	 * @return
	 * @throws IOException
	 */
	public String Read(String answer) throws IOException {
		String input = "";
		String toClient = "";
		// Open input stream to allows the Client to read from the Server. 
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		// Continuous loop to check for input from the server. 
		while(true) {
			if (answer.equals("LOOK")) {
				// Ensures the whole map is printed when LOOK is entered instead of just one line. 
				for (int i = 0; i < 5; i++) {
					input = in.readLine();
					// Builds the map string. 
					toClient = toClient + input + "\n";
				}
			}
			else if (answer.equals("QUIT")) {
				// Disconnect current player from the game. 
				System.out.println("You have quit the game. You will now be disconnected.");
				System.exit(0);
			}
			else {
				toClient = in.readLine();
				if (toClient.equals("WIN")) {
					// Disconnect player if they win. 
					System.out.println("Congratulations!!! \n You have escaped the Dungeon of Dooom!!!!!! \n"
							+ "Thank you for playing!");
					System.exit(0);
				}
			}
			return toClient;
		}
	}
	
	/**
	 * Methods not used as this is all done Server side now. 
	 */
	@Override
	public void setMap(File file) {
	}

	@Override
	public String hello() {
		return null;
	}

	@Override
	public String move(char direction) {
		return null;
	}

	@Override
	public String pickup() {
		return null;
	}

	@Override
	public String look() {
		return null;
	}

	@Override
	public boolean gameRunning() {
		return true;
	}

	@Override
	public void quitGame() {		
	}
}

