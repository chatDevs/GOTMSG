import java.net.Socket;
/**
 * Write a description of class User here.
 * 
 * @author Georg Dorndorf, Adrian Hinrichs
 * @version 16.04.2015
 */
public class User
{
    private String ip;
    private int port;
    private String name;
    private Socket socket;
    
    /**
     * Constructor for objects of class User
     */
    public User(String ip, int port, String name, Socket socket)
    {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.socket = socket;
    }
    
    public User(String ip, int port, String name)
    {
        this.ip = ip;
        this.port = port;
        this.name = name;
    }
    
    public String ip(){
        return ip;
    }
    
    public int port(){
        return port;
    }
    
    public void name(String name){
        this.name = name;
    }
    
    public String name(){
        return name;
    }
    
    public Socket socket(){
        return socket;
    }
}
