package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadRespond implements Runnable{
    
    private ServerSocket server;
    private Socket socket;
    private int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private int i=0;
    private String message ="";
    private static String[] words = {};
    private int no = 0;
    private static String word = "";
    private static String asterisk = "";
    
    public MultiThreadRespond(int port,String[] words,int no){
        this.port = port;
        try{
           this.words = words;
           //System.out.println(words[2]);
           this.no = no;
           server = new ServerSocket(port);
        }catch(Exception e){
            
        }

    }

    @Override
    public void run(){
        while(true){
            try{
                if(i==0) {
                    socket = server.accept();
                    this.ois = new ObjectInputStream(socket.getInputStream());
                    this.oos = new ObjectOutputStream(socket.getOutputStream());
                }
                //System.out.println(socket);
                //System.out.println("ER1");
               
                this.message = (String) ois.readObject();
                System.out.println("Message Received Client "+no+" : "+message);
                
                if(message.equalsIgnoreCase("play")){
                    System.out.println("Client "+no+" : Connected");
                    //System.out.println("Message Received: " + message);
                    oos.writeObject(">Connect Succes<");
                }
                
                if(message.equalsIgnoreCase("y")){
                    System.out.println("Client "+no+" : Playing..");
                    this.word = words[(int) (Math.random() * words.length)];
                    System.out.println("Client "+no+" get the word is > "+word);
                    this.asterisk = new String(new char[word.length()]).replace("\0", "-");
                    //System.out.println(asterisk);
                    //System.out.println(word+" "+asterisk);
                    oos.writeObject(word);
                    oos.writeObject(asterisk);
                }
                
                if(message.equalsIgnoreCase("n")) {
                    System.out.println("Client "+no+" : Stop Playing..");
                    socket.close();
                    break;
                }
                
                i++;
                        
                //ois.close();
                //oos.close();
                //socket.close();
            }catch(Exception e){

            }

        }
    }

}
