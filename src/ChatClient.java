import java.math.BigInteger;
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

    private List ignor;

    private boolean connectionTrusted = false;

    public static boolean output = true; 

    private RSA rsaClientKey, rsaServerKey;
    public ChatClient()
    {
        //super(pIPAdresse,pPortNr);
        super();

        rsaClientKey = new RSA(2048);

        ignor = new List();

        ConnectDialog cd = new ConnectDialog();
        cd.setVisible(true);

        ip = cd.getIP();
        port = cd.getPort();
        name = cd.getName();

        connection = new Connection(ip,port);
        receiver = new Receiver(this, connection);
        receiver.start();

        sendClear("SEC");
        gui =  new ClientGUI(this);
    }

    public void process(String pMessage){
        //System.out.println("test");
        //output(pMessage);
        if(pMessage.matches("\\bSEC\\sKEY\\b\\s.+")){
            String key = pMessage.split("SEC KEY ")[1];
            BigInteger serverE = new BigInteger(key.split("\\$")[0]);
            BigInteger serverN = new BigInteger(key.split("\\$")[1]);

            rsaServerKey = new RSA(serverN,serverE);

            send("SEC KEY "+rsaClientKey.getE()+"$"+rsaClientKey.getN());
        }else{
            //Da der Public Key das einzige ist, was der Server unverschl?sselt senden soll, muss die nachricht hier dechiffriert werden!

            String withoutCmd = "";
            if(pMessage.length()>4){
                withoutCmd = pMessage.substring(4,pMessage.length()-1);
            }
            //
            pMessage = "" + rsaClientKey.decrypt(pMessage);

            if(pMessage.matches("\\bSEC\\sOK\\b")){
                String key = pMessage.split("SEC KEY ")[1];
                send("SEC TRUSTED");
                send("USER " + name);
                connectionTrusted = true;
            }else if(connectionTrusted){//Nur wenn die verbindung sicher ist, ist eine Kommunikation m?glich!
                if(pMessage.matches("\\bMSG\\b\\s.+\\bFROM\\b\\s.+\\bWITH\\b\\s.+")){
                    String msg = withoutCmd.split(" FROM ")[0];
                    String src = (withoutCmd.split(" FROM ")[1]).split(" WITH ")[0];
                    String id = withoutCmd.split(" WITH ")[1];

                    ignor.toFirst();
                    while(ignor.hasAccess()){
                        if(ignor.getObject().equals(src)){
                            return;
                        }
                    }

                    output(src+" TO ME: "+msg);

                    //connection.send("GOTMSG "+id);
                }else if(pMessage.matches("\\bBRD\\b\\s.+\\bFROM\\b\\s.+\\bWITH\\b\\s.+")){
                    String msg = withoutCmd.split(" FROM ")[0];
                    String src = (withoutCmd.split(" FROM ")[1]).split(" WITH ")[0];
                    String id = withoutCmd.split("WITH")[1];

                    while(ignor.hasAccess()){
                        if(ignor.getObject().equals(src)){
                            return;
                        }
                    }

                    output(src+" TO ALL: "+msg);

                    //connection.send("GOTBRD "+id);
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
        }
    }

    public void sendClear(String msg){
        connection.send(msg);
    }

    public void send(String msg){
        if(rsaServerKey == null){
            sendClear(msg);
            return;
        }
        msg = rsaServerKey.encrypt(msg);
        connection.send(msg);
    }

    private void output(String text){
        gui.write(text);
    }

    public void addToIgnor(String name){
        ignor.append(name);
    }

    public void deleteFromIgnor(String name){
        ignor.toFirst();
        while(ignor.hasAccess()){
            if(ignor.getObject().equals(name)){
                ignor.remove();
            }
        }
    }
}
