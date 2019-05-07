package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {

    private static ServerSocket server;
    private static int port = 6666;
    private static String[] words = {"batman","conan","superman","shazam","dumbo","bumblebee","godzilla","avatar","titanic","frozen","minions","jurassic","avengers","aquaman"};
    private static String word = "";
    private static String asterisk = "";
    private static int score = 0;
        
    public static void main(String args[]) throws ClassNotFoundException {
        
        int no =1;
        Thread thread = null;
        try {
            server = new ServerSocket(port);
            System.out.println("ServerTCP is now running...");

            while(true){
                System.out.println("Waiting for the client request");
                Socket socket = server.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String message = (String) ois.readObject();
                Random rand = new Random();
                int newPort = rand.nextInt(9000)+1000;
                MultiThreadRespond mr = new MultiThreadRespond(newPort,words,no,score);
                thread = new Thread(mr);
                thread.start();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(""+newPort);
                no++;

            }
        } catch (IOException ex) {
            try {
                server.close();
            } catch (IOException e) {
                System.err.println("ERROR closing socket: " + e.getMessage());
            }
        }
    }
    
}
