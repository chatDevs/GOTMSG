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

    private String name;
    private String ip;
    private int port;

    public static boolean output = true; 
    public ChatClient()
    {
        //super(pIPAdresse,pPortNr);
        super();

        ConnectDialog cd = new ConnectDialog();
        cd.setVisible(true);

        ip = cd.getIP();
        port = cd.getPort();
        name = cd.getName();

        connection = new Connection(ip,port);
        receiver = new Receiver(this, connection);
        receiver.start();

        gui = new ClientGUI(this);
        send("USER "+name);
    }

    public void process(String pMessage){
        System.out.println("test");
        System.out.println(pMessage);
        String withoutCmd = "";
        if(pMessage.length()>4){
            withoutCmd = pMessage.substring(4,pMessage.length()-1);
        }
        if(pMessage.matches("\\bMSG\\b\\s.+\\bFROM\\b\\s.+\\bWITH\\b\\s.+")){
            String msg = withoutCmd.split(" FROM ")[0];
            String src = (withoutCmd.split(" FROM ")[1]).split(" WITH ")[0];
            String id = withoutCmd.split("WITH")[1];

            output(src+" TO ME: "+msg);

            connection.send("GOTMSG "+id);
        }else if(pMessage.matches("\\bBRD\\b\\s.+\\bFROM\\b\\s.+\\bWITH\\b\\s.+")){
            String msg = withoutCmd.split(" FROM ")[0];
            String src = (withoutCmd.split(" FROM ")[1]).split(" WITH ")[0];
            String id = withoutCmd.split("WITH")[1];

            output(src+" TO ALL: "+msg);

            connection.send("GOTBRD "+id);
        }else if(pMessage.matches("\\bLST\\b\\s.+")){
            String[] nutzerliste = withoutCmd.split(";");
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
