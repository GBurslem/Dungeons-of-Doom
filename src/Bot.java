
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;
import static java.lang.Thread.sleep;
public class Bot extends PlayGame{
	
	private Random random;
	private static final char [] DIRECTIONS = {'N','S','E','W'};
	
	/**
	 * Constructor for the bot.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Bot() throws UnknownHostException, IOException{
		super();
		random = new Random();
	}
	
	/**
	 * Bot selects an action which is returned to be processed by the Server.
	 * @param lastAnswer
	 * @return
	 */
	private String botAction(String lastAnswer){
		switch (lastAnswer.split(" ")[0]){
		case "":
			return "HELLO";
		case "GOLD:":
		case "FAIL":
			return "LOOK";
		default:
			return "MOVE " + DIRECTIONS[random.nextInt(4)];
		}
	}
	
	/**
	 * Action is selected by the bot then send to the Server, Server response is read and printed. 
	 */
	public void update() throws IOException{
		String answer = "";
		while (ClientLogic.gameRunning()){
			ClientLogic.Send(botAction(answer));
			answer = ClientLogic.Read(answer);
			printAnswer(answer);
			try {
			sleep(1000);
			} catch(InterruptedException e){
				
			}
		}
	}
	
	/**
	 * Main method for the Bot. Simply gets the game running when the Bot is launched. 
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String [] args) throws UnknownHostException, IOException {
		Bot game = new Bot();
		System.out.println("Do you want to load a specitic map?");
		System.out.println("Press enter for default map");
		game.update();
		
	}

}
