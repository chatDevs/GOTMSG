import javax.swing.*;
import java.awt.event.*;
import java.io.*;
/**
 * GOTMSG-CHat-CLient
 * @author A.C.Hinrichs
 * @version 2015-04-16
 */
public class ChatClient implements ActionListener
{
    private Connection connection;
    private Receiver receiver;
    
    public static boolean output = true; 
    
    public ChatClient(String pIPAdresse, int pPortNr)
    {
        //super(pIPAdresse,pPortNr);
        super();
        
        connection = new Connection(pIPAdresse,pPortNr);
        receiver = new Receiver(this, connection);
        receiver.start();
    }
    
    public void process(String pMessage){
        System.out.println("test");
        System.out.println(pMessage);
        if(pMessage.matches("\\bMSG\\b\\s.+\\bFROM\\b\\s.+\\bWITH\\b\\s.+")){
            String ohneMsg = pMessage.substring(2,pMessage.length()-1);
            String msg = pMessage.split(" FROM ")[0];
            String src = (pMessage.split(" FROM ")[1]).split(" WITH ")[0];
            String id = pMessage.split("WITH")[1];
            
            System.out.println("("+id+") "+src+": "+msg);
            
            connection.send("GOTMSG "+id);
        }else if(pMessage.matches("\\bBRD\\b\\s.+\\bFROM\\b\\s.+\\bWITH\\b\\s.+")){
            String ohneMsg = pMessage.substring(2,pMessage.length()-1);
            String msg = pMessage.split(" FROM ")[0];
            String src = (pMessage.split(" FROM ")[1]).split(" WITH ")[0];
            String id = pMessage.split("WITH")[1];
            
            System.out.println("("+id+") "+src+": "+msg);
            
            connection.send("GOTBRD "+id);
        }else if(pMessage.matches("\\bLST\\b\\s.+")){
            String ohneLst = pMessage.substring(2,pMessage.length()-1);
            String[] nutzerliste = pMessage.split(" ");
            System.out.println("Liste der nutzer: ");
            for(String i:nutzerliste){
                System.out.println("     "+i);
            }
        }else if(output && pMessage.matches("\\bOK\\b")){
            System.out.println("Server bestätigt "+pMessage);
        }else if(output && pMessage.matches("\\bERR\\b")){
            System.out.println("Server fehler "+pMessage);
        }
    }
    
    public void actionPerformed(ActionEvent e){
        
    }
    
    public void send(String msg){
        connection.send(msg);
    }
}
