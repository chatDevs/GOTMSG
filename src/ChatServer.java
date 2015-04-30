import java.math.BigInteger;
import java.net.*;
import java.io.*;
import org.apache.commons.codec.binary.BaseNCodec;
/**
 * Ein ''einfacher'' Chatserver.
 *
 * 
 * Protokoll: 
 * @author Georg Dorndorf, Adrian Hinrichs
 * @version 16.04.2015
 */
public class ChatServer
{
    private class Loop extends Thread{
        private ChatServer server;

        public Loop(ChatServer server){
            this.server =  server;
        }

        public void run(){
            while (true) 
            {
                try
                {
                    Socket cSocket = server.socket.accept();
                    Connection connection = new Connection(cSocket, server);

                    server.addCon(connection);
                    connection.start();
                }

                catch (Exception shit)
                {
                    System.err.println("shit went wrong " + shit);
                }    
            }
        }
    }

    private class Connection extends Thread{
        private Socket s;
        private BufferedReader vomHost;
        private PrintWriter zumHost;
        private int port;

        private ChatServer server;

        public Connection(Socket socket, ChatServer server) {
            s = socket;
            this.server = server;
            port=s.getLocalPort();
            try {
                //Objekt zum Versenden von Nachrichten ueber den Socket erzeugen
                zumHost = new PrintWriter(s.getOutputStream(), true);
                //Objekt zum Empfangen von Nachrichten ueber das Socketobjekt erzeugen
                vomHost= new BufferedReader(new InputStreamReader(s.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run(){
            String lNachricht;

            while (!this.isClosed())
            {
                lNachricht = this.receive();
                if (lNachricht == null)
                {
                    if (!this.isClosed())
                    {
                        server.closeConnection(this.getRemoteIP(), this.getRemotePort());
                    }
                }
                else
                    server.processMessage(this.getRemoteIP(), this.getRemotePort(), lNachricht);
            }
        }

        /** 
         * receive
         */
        public String receive() {
            try {
                return vomHost.readLine();
            }
            catch ( IOException e) {
                System.out.println("connection fucked up");
            }
            return null;
        }

        public void send(String nachricht) {
            zumHost.println(nachricht);
            zumHost.flush();
        }

        public boolean isConnected() {return s.isConnected();}

        public boolean isClosed() {return s.isClosed();}

        public String getRemoteIP() {return "" + s.getInetAddress();}

        public String getLocalIP() {return "" + s.getLocalAddress();}

        public int getRemotePort() {return s.getPort();}

        public int getLocalPort() {return s.getLocalPort();}

        public void close() {
            try {
                s.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Socket verbindungsSocket() {
            return s;
        }
    }

    private final int port;
    private java.util.Map<String, User> users; // suche nach nam,e
    private java.util.Map<String, User> userAddr;   // suche nach addresse
    private java.util.Map<String, Connection> connections; // man muss die verbindungen selber verwalten weil ......
    private int id;

    public RSA rsa;
    private final int L = 2048; //sicherheit wird durch höhere schlüssellänge erhöht

    private ServerSocket socket;
    private Loop loop;
    /**
     * Constructor for objects of class ChatServer
     */
    public ChatServer(int port)
    {
        this.port = port;
        this.rsa = new RSA(L);
        try
        {
            socket = new ServerSocket(port);
            connections = new java.util.HashMap<String, Connection>();
            loop = new Loop(this);
            loop.start();
        }

        catch (Exception pFehler)
        {
            System.err.println("Fehler beim \u00D6ffnen der Server: " + pFehler);
        }   
        users = new java.util.HashMap<String, User>();
        userAddr = new java.util.HashMap<String, User>();
    }

    private User checkUser(String name){
        return users.get(name);
    }

    private User checkAddr(String ipPort){
        return userAddr.get(ipPort);
    }

    public void processNewConnection(String ip, int port){
        System.out.println("OK READY " + ip + ":" + port);
        //connections.get(ip + ":" + port).send("OK READY");
    }

    public void processMessage(String ip, int port, String msg){
        System.out.println(msg);
        User from=checkAddr(ip+":"+port);

        if(from != null && from.rsaReady()){
            msg = rsa.decrypt(msg);
            System.out.println("dec" + msg);
        }

        if(msg.matches("\\bUSER\\b\\s.\\w+")){
            if(from != null && from.name() == "authenticating"){
                from.name(msg.split("USER ")[1]);
            } else{
                System.out.println("OK user logged in " + ip);
                send(ip, port, "OK");
                User u = new User(ip, port, msg.split("USER ")[1]);
                users.put(msg.split("USER ")[1], u);
                userAddr.put(ip + ":" + port, u);
            }
        } else if (msg.matches("\\bMSG\\b\\s.+\\s\\bTO\\b\\s.+")){
            id++;
            send(ip, port, "GOTMSG " + id);

            String mto = msg .split(" TO ")[0];
            String uname = msg.split(" TO ")[1];
            User to=checkUser(uname);
            if(to == null ){
                send(ip, port,"ERR unknown destination");
            } else {
                mto = mto.split("MSG ")[1];

                if (to != null){    
                    send(to.ip(), to.port(), "MSG " + mto + " FROM " + from.name() + " WITH " + id);
                }
            }
        } else if(msg.matches("BRD\\s.+")){
            id++;
            send(ip, port, "GOTBRD " + id);

            sendToAll(msg + " FROM " + from.name() + " WITH " + id);
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
        } else if (msg.matches("\\bNCK\\b\\s\\.\\+")){

        } else if(msg.matches("\\bSEC\\b\\s\\bTRUSTED\\b")) {
            send(ip, port, "SEC OK");

        } else if(msg.matches("\\bSEC\\b")){
            System.out.println("IP: " + ip + ":" + port + " requested secure chat!");
            //rsa = new RSA(2048);
            User u = new User(ip, port);
            u.enableRsa();
            userAddr.put(ip + ":" + port, u);
            users.put(ip + ":" + port, u);
            send(ip, port, "SEC KEY " + rsa.getE().toString() + "$" + rsa.getN().toString());
        } else if(msg.matches("\\bSEC\\b\\s\\bKEY\\b\\s\\d+\\$\\d+")){
            System.out.println("IP: " + ip + ":" + port + " send the key!");

            String n = msg.split(" ")[1];
            String[] m = n.split("\\$");

            RSA r =  new RSA(new BigInteger(m[1]), new BigInteger(m[0]));
            User u = userAddr.get(ip + ":" + port);
            u.rsa(r);

            send(ip, port, "SEC OK");
        }  else {
            send(ip, port, "ERR unknown command");
        }
    }

    public void processClosedConnection(String ip, int port){
        User dc = checkAddr(ip + ":" + port);

        if (dc == null){
            sendToAll("User " + dc.name() + " timed out!");

            users.remove(dc.name());
            userAddr.remove(ip + ":" + port);
        }  

    }

    public void send(String ip, int port, String msg){
        Connection lSerververbindung = connections.get(ip + ":" + port);
        if (lSerververbindung != null){
            User u = userAddr.get(ip + ":" + port);
            if( u!= null && u.rsa() != null){
                String m = u.rsa().encrypt(msg);
                lSerververbindung.send(m);
            } else {
                lSerververbindung.send(msg);
            }

        } else {
            System.err.println("ERROR you lil shit: IP " +ip + " mit Port " + port + " nicht vorhanden.");
        }
    }

    public void addCon(Connection connection){
        connections.put(connection.getRemoteIP() + ":" + connection.getRemotePort(), connection);
        processNewConnection(connection.getRemoteIP(), connection.getRemotePort());
    }

    public void closeConnection (String ip, int port)
    {
        Connection connection = connections.get(ip + ":" + port);
        if (connection != null)
        {   this.processClosedConnection(ip, port);
            connection.close();
            connections.remove(ip + ":" + port);

        }
        else{
            System.err.println("some other shit went horrible wrong: IP " + ip + " mit Port " + port + " nicht vorhanden.");
        }
    }

    public void sendToAll(String msg)
    {        
        for(Connection c : connections.values())
        {
            c.send(msg);
        }   
    }
}
