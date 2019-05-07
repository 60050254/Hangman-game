package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class Client extends ClientGame{
    
    private Socket socket;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Scanner sc = new Scanner(System.in);
    private int port = 6666;
    private String message = "";
    private static String word = "";
    private static String asterisk = "";
    private static int count = 0;
    private static int score = 0;
   
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
     
        Client tcpClient = new Client();
	tcpClient.run();
        
    }
    public void run() throws ClassNotFoundException {
        
	try {
             
		this.socket = new Socket("127.0.0.1", port);
		this.oos = new ObjectOutputStream(this.socket.getOutputStream());
                System.out.print("");
                this.oos.writeObject("Hi");
                this.ois = new ObjectInputStream(this.socket.getInputStream());
                this.message = (String) ois.readObject();
                this.port = Integer.parseInt(message);
	        toPlayOrNotToPlay();
    
	} catch (IOException ex) {
                System.err.println("ERROR closing socket: ");
	}
    }
    protected void toPlayOrNotToPlay() throws IOException, ClassNotFoundException {
		String userInput = "";
                this.socket = new Socket("127.0.0.1", port);
                this.oos = new ObjectOutputStream(this.socket.getOutputStream());
                this.oos.writeObject("play");
                this.ois = new ObjectInputStream(this.socket.getInputStream());
                this.message = (String) ois.readObject();
                
		while (true) {
                    
                    if(count==0)System.out.print("Do you want to play? (y/n) ");
                        
                    else System.out.print("Do you want to play Continue? (y/n) ");
			userInput = sc.next();
			userInput = userInput.trim().toLowerCase();
			if (userInput.equals("y")) {
                                this.oos.writeObject("y");
                                this.word = (String) ois.readObject();
                                this.asterisk = (String) ois.readObject();
                                this.score = rungame(word,asterisk);
                                this.oos.writeObject("updatescore");
                                this.oos.writeObject(""+score);
                                count++;
                                if(count==15){
                                    System.out.println("----- End Game -----");
                                    System.out.println("Your score is > "+this.score);
                                    this.oos.writeObject("n");
                                    closeConnection();
                                    break;
                                }
			}
			else if(userInput.equals("n")){
                                System.out.println("Your score is > "+this.score);
				System.out.println(">Disconnected<");
                                this.oos.writeObject("n");
                                closeConnection();
                                break;
			}
		}
	}
    protected void closeConnection() {
		try {
			if (this.socket != null && !this.socket.isClosed()) {
				this.socket.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
