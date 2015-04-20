
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
    private java.util.Map<String, User> users;
    private java.util.Map<String, User> userAddr;
    private int id;

    /**
     * Constructor for objects of class ChatServer
     */
    public ChatServer(int port)
    {
        super(port);
        this.port = port;
        users = new java.util.HashMap<String, User>();
        userAddr = new java.util.HashMap<String, User>();
    }

    private User checkUser(String name){
        return users.get(name);
    }

    private User checkAddr(String ipPort){
        return userAddr.get(ipPort);
    }

    @Override
    public void processNewConnection(String ip, int port){
        send(ip, port, "OK READY");
    }

    @Override
    public void processMessage(String ip, int port, String msg){
        System.out.println(msg);
        User from=checkAddr(ip+":"+port);
        if(msg.matches("\\bUSER\\b\\s.\\w+")){
            System.out.println("OK user logged in " + ip);
            send(ip, port, "OK");
            User u = new User(ip, port, msg.split("USER ")[1]);
            users.put(msg.split("USER ")[1], u);
            userAddr.put(ip + ":" + port, u);
        } else if (msg.matches("\\bMSG\\b\\s.+\\s\\bTO\\b\\s.+")){
            id++;
            send(ip, port, "GOTMSG " + id);

            String mto = msg .split(" TO ")[0];
            String uname = msg.split(" TO ")[1];
            User to=checkUser(uname);

            mto = mto.split("MSG ")[1];

            if (to != null){
                send(to.ip(), to.port(), "MSG " + mto + " FROM " + from.name() + " WITH " + id);
            }
        } else if(msg.matches("BRD\\s.+")){
            id++;
            send(ip, port, "GOTBRD " + id);

            sendToAll(msg + " FROM " + from.name());
        } else if(msg.matches("\\bLST\\b")){
            java.util.Collection<User> u = users.values();
            StringBuilder m = new StringBuilder();
            m.append("LST ");
            for (User us:u){
                //System.out.println(us.name());
                m.append(us.name() + ";");
            }
            send(ip, port, m.toString());
        } else if(msg.matches("\\bQUIT\\b")){
            sendToAll("BRD User " + from.name() + " is leaving!");
            users.remove(from.name());
            userAddr.remove(ip + ":" + port);
        } else {
            send(ip, port, "ERR");
        }
    }

    @Override
    public void processClosedConnection(String ip, int port){
        User dc = checkAddr(ip + ":" + port);

        if (dc == null){
            sendToAll("User " + dc.name() + " timed out!");

            users.remove(dc.name());
            userAddr.remove(ip + ":" + port);
        }  

    }

    
    @Override
    public void send(String ip, int port, String msg){
    
    }
    
    @Override
    public void sendToAll(String msg){
       
    }
}
