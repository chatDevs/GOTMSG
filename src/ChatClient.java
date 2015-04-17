import javax.swing.*;
import java.awt.event.*;
import java.io.*;
/**
 * GOTMSG-CHat-CLient
 * @author A.C.Hinrichs
 * @version 2015-04-16
 */
public class ChatClient extends JFrame implements ActionListener
{
    private Connection connection;
    public ChatClient(String pIPAdresse, int pPortNr)
    {
        //super(pIPAdresse,pPortNr);
        connection = new Connection(pIPAdresse,pPortNr);
    }
    
    public void process(String pMessage){
        String[] msgParts = pMessage.split(" ");
        if(msgParts[0].equals("MSG")){
            //msgParts[2] ist die nachricht, asugeben!
            connection.send("GOTMSG "+msgParts[1]);
        }else if(msgParts[0].equals("BRD")){
            //msgParts[2] ist die nachricht, asugeben!
            connection.send("GOTBRD "+msgParts[1]);
        }
    }
    
    public void actionPerformed(ActionEvent e){
    
    }
}
