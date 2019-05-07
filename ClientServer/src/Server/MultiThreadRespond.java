package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    private static int score = 0;
    private ArrayList<String> myArrList;
    
    public MultiThreadRespond(int port,String[] words,int no,int scoren){
        this.port = port;
        myArrList = new ArrayList<String>();
        try{
           this.score = score;
           this.words = words;
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

                this.message = (String) ois.readObject();
                System.out.println("Message Received Client "+no+" : "+message);
                
                if(message.equalsIgnoreCase("play")){
                    System.out.println("Client "+no+" : Connected");
                    oos.writeObject(">Connect Succes<");
                }
                
                else if(message.equalsIgnoreCase("y")){
                    if(i==1) { System.out.println("Client "+no+" : Playing.."); }
                    
                    for(int j=0;j<1;j++){
                        this.word = words[(int) (Math.random() * words.length)];
                        this.asterisk = new String(new char[word.length()]).replace("\0", "-");
                        for(int k=0;k<myArrList.size();k++){
                            if(myArrList.get(k).equals(this.word)){
                                k=myArrList.size();
                                j=-1;
                            }
                        }
                    }
                    myArrList.add(this.word);
                    System.out.println("Client "+no+" get the word is > "+word);
                    oos.writeObject(word);
                    oos.writeObject(asterisk);
                }
                
                else if(message.equalsIgnoreCase("updatescore")){
                    this.message = (String) ois.readObject();
                    this.score = Integer.parseInt(this.message);
                }
                
                else if(message.equalsIgnoreCase("n")) {
                    System.out.println("Client "+no+" total score is > "+this.score);
                    System.out.println("Client "+no+" : Stop Playing..");
                    socket.close();
                    break;
                }
                this.i++;
                
            }catch(Exception e){

            }

        }
    }

}
