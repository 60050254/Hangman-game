package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

/**
 * This class implements java socket client
 * @author pankaj
 *
 */
public class Client extends ClientGame{
    
    private Socket socket;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Scanner sc = new Scanner(System.in);
    private int port = 6666;
    private String message = "";
    private static String word = "";
    private static String asterisk = "";
   
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
                //System.out.println(message);
                this.port = Integer.parseInt(message);
                
		//this.ois = new ObjectInputStream(socket.getInputStream());
	        toPlayOrNotToPlay();
                
                
	} catch (IOException ex) {
		//handleFatalException(ex);
                System.err.println("ERROR closing socket: ");
	}
    }
    protected void toPlayOrNotToPlay() throws IOException, ClassNotFoundException {
		String userInput = "";
                this.socket = new Socket("127.0.0.1", port);
                this.oos = new ObjectOutputStream(this.socket.getOutputStream());
                this.oos.writeObject("play");
                //System.out.println(port);
                this.ois = new ObjectInputStream(this.socket.getInputStream());
                this.message = (String) ois.readObject();
                
		while (true) {
                    
			System.out.print("Do you want to play? (y/n) ");
			userInput = sc.next();
			userInput = userInput.trim().toLowerCase();
			if (userInput.equals("y")) {
                                //System.out.println("ER1");
                                this.oos.writeObject("y");
                                //System.out.println("ER2");
                                this.word = (String) ois.readObject();
                                //System.out.println(word);
                                this.asterisk = (String) ois.readObject();
                                rungame(word,asterisk);
			}
			else if(userInput.equals("n")){
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
