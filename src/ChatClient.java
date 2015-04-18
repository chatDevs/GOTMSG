/**
 * GOTMSG-CHat-CLient
 * @author A.C.Hinrichs
 * @version 2015-04-18
 */
public class ChatClient
{
    private Connection connection;
    private Receiver receiver;
    private ClientGUI gui;
    
    public static boolean output = true; 
    
    public ChatClient(String pIPAdresse, int pPortNr)
    {
        //super(pIPAdresse,pPortNr);
        super();
        
        connection = new Connection(pIPAdresse,pPortNr);
        receiver = new Receiver(this, connection);
        receiver.start();
        
        gui = new ClientGUI(this);
    }
    
    public void process(String pMessage){
        System.out.println("test");
        System.out.println(pMessage);
        if(pMessage.matches("\\bMSG\\b\\s.+\\bFROM\\b\\s.+\\bWITH\\b\\s.+")){
            String ohneMsg = pMessage.substring(2,pMessage.length()-1);
            String msg = pMessage.split(" FROM ")[0];
            String src = (pMessage.split(" FROM ")[1]).split(" WITH ")[0];
            String id = pMessage.split("WITH")[1];
            
            output("("+id+") "+src+": "+msg);
            
            connection.send("GOTMSG "+id);
        }else if(pMessage.matches("\\bBRD\\b\\s.+\\bFROM\\b\\s.+\\bWITH\\b\\s.+")){
            String ohneMsg = pMessage.substring(2,pMessage.length()-1);
            String msg = pMessage.split(" FROM ")[0];
            String src = (pMessage.split(" FROM ")[1]).split(" WITH ")[0];
            String id = pMessage.split("WITH")[1];
            
            output("("+id+") "+src+": "+msg);
            
            connection.send("GOTBRD "+id);
        }else if(pMessage.matches("\\bLST\\b\\s.+")){
            String ohneLst = pMessage.substring(2,pMessage.length()-1);
            String[] nutzerliste = pMessage.split(";");
            output("Liste der nutzer: ");
            for(String i:nutzerliste){
                output("     "+i);
            }
        }else if(pMessage.matches("\\bOK\\b")){
            output("Server bestätigt "+pMessage);
        }else if(pMessage.matches("\\bERR\\b")){
            output("Server fehler "+pMessage);
        }
    }
    
    public void send(String msg){
        connection.send(msg);
    }
    
    private void output(String text){
        gui.write(text);
    }
}
