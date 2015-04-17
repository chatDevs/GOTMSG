
/**
 * Ein einfacher Chatserver.
 * 
 * Protokoll: 
 * @author Georg Dorndorf, Adrian Hinrichs
 * @version 16.04.2015
 */
public class ChatServer extends Server
{
    private final int port;
    private java.util.Map<String, String> users;
    
    /**
     * Constructor for objects of class ChatServer
     */
    public ChatServer(int port)
    {
        super(port);
        this.port = port;
        users = new java.util.HashMap<String, String>();
        
        
    }
    
    private String checkUser(String ipPort){
        return users.get(ipPort);
    }
    
    @Override
    public void processNewConnection(String ip, int port){
        send(ip, port, "+OK READY");
    }
    
    @Override
    public void processMessage(String ip, int port, String msg){
        if(msg.matches("USER\\s.\\w+")){
            
        } else if (msg.matches("MSG\\s.+\\sTO\\s\\w+")){
        
        } else if(msg.matches("BRD\\s.+")){
        
        } else if(msg.matches("\\bLIST\\b")){
        
        } else if(msg.matches("\\bQUIT\\b")){
        
        } else {
        
        }
    }
    
    @Override
    public void processClosedConnection(String ip, int port){
        
    }
}
