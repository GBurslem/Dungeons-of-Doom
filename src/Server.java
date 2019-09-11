
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server implements IGameLogic, Runnable {
	
	Socket listenAtSocket;
    private static Map map;
    private boolean active;
	protected Client clientLogic;
	private int[] playerPosition;
	private int collectedGold;
	private static ArrayList<Server> currentPlayers = new ArrayList<Server>();	
    
    /**
     * Creates a Server object and starts the server.
     *
     * @param args
     */
     public static void main(String[] args) {
         // Setting the default port number.
         int portNumber = 40004;
         try {
             // Initializing the Socket Server
             ServerSocket ssock = new ServerSocket(portNumber);
             map = new Map(new File("./maps","example_map.txt"));
             while (true) {
            	 Socket sock = ssock.accept();
            	 // Creates new thread for connecting Client. 
            	 new Thread(new Server(sock)).start();
             }
             } catch (IOException e) {
             e.printStackTrace();
         }
     }
     
    /**
     * Constructor. Creates a new socket for the client and initiates their player. 
     * @param newSocket
     */
    public Server(Socket newSocket) {
       this.listenAtSocket = newSocket;
       this.playerPosition = initiatePlayer();
       this.collectedGold = 0;
       // Adds new Players to player list.
       currentPlayers.add(this);
    }
    
	/**
	 * Finds a random position for the player in the map, avoiding walls and other players. 
	 * @return Return null; if no position is found or a position vector [y,x]
	 */
	private int[] initiatePlayer() {
		int[] pos = new int[2];
		Random rand = new Random();

		pos[0]=rand.nextInt(map.getMapHeight());
		pos[1]=rand.nextInt(map.getMapWidth());
		int counter = 1;
		while ( (map.lookAtTile(pos[0], pos[1]) == '#' || map.lookAtTile(pos[0], pos[1]) == 'P') && counter < map.getMapHeight() * map.getMapWidth()) {
			pos[1]= (int) ( counter * Math.cos(counter));
			pos[0]=(int) ( counter * Math.sin(counter));
			counter++;
		}
		return (map.lookAtTile(pos[0], pos[1]) == '#') ? null : pos;
	}
	
	/**
	 * Reads the map file and initializes a position for the player on the map. 
	 */
	@Override
	public void setMap(File file) {
		map.readMap(file);
		playerPosition = initiatePlayer();
		active = true;
	}

	/**
	 * Prints how many gold is still required to win.
	 */
	@Override
	public String hello() {
		return "GOLD: " + (map.getWin() - collectedGold);
	}

	/**
	 * Reads the users input direction while checking for collisions with other players.
	 */
	@Override
	public String move(char direction) {
		int[] newPosition = playerPosition.clone();
		switch (direction){
		case 'N':
			newPosition[0] -= checkCollisionNS(-1);
			break;
		case 'E':
			newPosition[1] += checkCollisionEW(1);
			break;
		case 'S':
			newPosition[0] += checkCollisionNS(1);
			break;
		case 'W':
			newPosition[1] -= checkCollisionEW(-1);
			break;
		default:
			return "FAIL";
		}
		
		if(map.lookAtTile(newPosition[0], newPosition[1]) != '#'){
			playerPosition = newPosition;
			
			if (checkWin())
				quitGame();
			
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}
	
	/**
	 * Calculates the new player position and determines whether it would result in a collision by moving north/south.
	 * @param pos
	 * @throws IOException 
	 */
	protected int checkCollisionNS(int change) {
		for (int count = 0; count < currentPlayers.size(); count++) {
			if ( (playerPosition[0] + change == getYPos(count)) && (playerPosition[1] == getXPos(count)) ) {
				collisionAlert();
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * Calculates the new player position and determines whether it would result in a collision by moving east/west.
	 * @param pos
	 */
	protected int checkCollisionEW(int change) {
		for (int count = 0; count < currentPlayers.size(); count++) {
			if ( (playerPosition[0] == getYPos(count)) && (playerPosition[1] + change == getXPos(count)) ) {
				collisionAlert();
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * Provides and alert to the user if the movement they attempted resulted in a collision. 
	 */
	protected void collisionAlert() {
		PrintWriter out;
		try {
			out = new PrintWriter(listenAtSocket.getOutputStream(), true);
			out.println("You cannot move there! A player occupies that space.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the player collected all GOLD and is on the exit tile.
	 * @return True if all conditions are met, false otherwise
	 * @throws IOException 
	 */
	protected boolean checkWin() {
		if (collectedGold >= map.getWin() && 
				map.lookAtTile(playerPosition[0], playerPosition[1]) == 'E') {
			PrintWriter out;
			try {
				// Let the client know when they have won.
				out = new PrintWriter(listenAtSocket.getOutputStream(), true);
				out.println("WIN");
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Close the server, game is over.
			System.out.print("Player won the game. Shutting server down...");
			System.exit(0);
		}
		return false;
	}
	
	/**
	 * Pickup function for getting Gold.
	 * @return
	 */
	@Override
	public String pickup() {
		if (map.lookAtTile(playerPosition[0], playerPosition[1]) == 'G') {
			collectedGold++;
			map.replaceTile(playerPosition[0], playerPosition[1], '.');
			return "SUCCESS, GOLD COINS: " + collectedGold;
		}

		return "FAIL" + "\n" + "There is nothing to pick up...";
	}

	/**
	 * Retrieves the X position of the player who is in position i in the array list.
	 * @param i
	 * 		Position of the desired player in the Array List.
	 * @return
	 * 		The X co-ordinate. 
	 */
	public int getXPos(int i) {
		return currentPlayers.get(i).playerPosition[1];
	}

	/**
	 * Retrieves the X position of the player who is in position i in the array list.
	 * @param i
	 * 		Position of the desired player in the Array List. 
	 * @return
	 * 		The Y co-ordinate.
	 */
	public int getYPos(int i) {
		return currentPlayers.get(i).playerPosition[0];
	}

	/** 
	 * Look method used by the player to see around them. This also has to take into account other players. This is done by first
	 * checking to see if the Players are in-range, so less than 3 grid spaces away. If they are, the position of the P is
	 * determined by the difference in position of the current player and the nearby player.
	 * 
	 *  @return
	 *  	The map around the player. 
	 */
	@Override
	public String look() {
		String output = "";
		char [][] lookReply = map.lookWindow(playerPosition[0], playerPosition[1], 5);
		lookReply[2][2] = 'P';
		for (int count = 0; count < currentPlayers.size(); count++) {
			if ( (((playerPosition[0] - getYPos(count)) < 3) && ((playerPosition[0] - getYPos(count)) > -3)) &&
					(((playerPosition[1] - getXPos(count)) < 3) && ((playerPosition[1] - getXPos(count)) > -3))
				) {
				lookReply[2 - (playerPosition[1] - getXPos(count))][2 - (playerPosition[0] - getYPos(count))] = 'P';
			}
		}
		// Construct the map in the output variable. 
		for (int i=0;i<lookReply.length;i++){
			for (int j=0;j<lookReply[0].length;j++){
					output += lookReply[j][i];
			}
			output += "\n";
		}
		return output;
	}
	
	@Override
	public boolean gameRunning() {
		return active;
	}
	
	/**
	 * Lets the server know when a Client disconnects.
	 */
	@Override
	public void quitGame() {
		System.out.println("Player has disconnected.");
		active = false;
	}

	/**
	 * Central method for the Server. Continues to listen for inputs by Clients, and if one is found the input is processed and
	 *  a response is set.
	 */
	@Override
	public void run() {
		try {
			// Get input from client.
			PrintWriter out = new PrintWriter(listenAtSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(listenAtSocket.getInputStream()));
			while(true) {
				String ans = in.readLine();
				String response = parseCommand(ans);
				out.println(response);
			}
		}
		catch (IOException e) {
			
		};
	}

	/**
	 * Takes the user input and processes it to determine what they input. 
	 * @param readUserInput
	 * 		Users input. 
	 * @return
	 */
	protected String parseCommand(String readUserInput) {
	try{
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
			quitGame();
		default:
			answer = "FAIL";
		}
		
		return answer;
	} catch	(NullPointerException e) {
		return null;
	}
}

}
