
/**
 * 
 * @author MEL
 * @version 2012-05-02
 */
public class EchoServer extends Server


{
    private final String ENDE = "*bye*"; // final bedeutet, dass das eine Konstante ist, nicht mehr veränderbar
    /**
     * Constructor for objects of class EchoServer
     */
    public EchoServer()
    {
        super(2000); //hört auf Port 2000 - man sollte immer Ports über 1000 benutzen, um nicht mit Standard-Server Diensten zu kollidieren
               
    }

    /**
     * Diese Methode wird aufgerufen, wenn eine Verbindungs-Anfrage von einem Client kommt.
     * wenn die Methode durchgeführt wurde, steht die verbindung.
     * die methode ist in server abstract und muss daher überschrieben werden.
     * 
     */
    public void processNewConnection(String pClientIP, int pClientPort)
    {
        this.send(pClientIP, pClientPort, "Willkommen " + pClientIP + "auf Port " + pClientPort + " beim Echo-Server!");  // sendet Nachricht an Client
        System.out.println("Willkommen " + pClientIP + "auf Port " + pClientPort + " beim Echo-Server!"); // gibt alles auf der Server-Konsole aus
    }
    
    /**
     * Diese Methode wird aufgerufen, wenn eine Anfrage von einem Client kommt
     * Auch sie überschreibt die Methode der abtrakten Serverklasse und gibt (weil ich ja ein Echo-Server bin) 
     * einfach die Nachricht, die ich bekommen habe, wieder zurück
     */
    public void processMessage(String pClientIP, int pClientPort, String pMessage){
        if (pMessage.equals(ENDE)){
            this.closeConnection(pClientIP, pClientPort);   // hier wird die Verbindung beendet. Methode aus der Oberklasse.
        }
        else{
            this.send(pClientIP, pClientPort, pClientIP + " " + pClientPort + ": " + pMessage); // sendet Nachricht an Client
            System.out.println(" " + pClientPort + ": " + pMessage); // gibt alles auf der Server-Konsole aus
        }
            
    }
    
    
    /**
     * Diese Methode wird aufgerufen, wenn die Verbindung beendet wird.
     */
    public void processClosedConnection (String pClientIP, int pClientPort){
        this.send(pClientIP, pClientPort, pClientIP + " " + pClientPort + " auf Wiedersehen. ");
        System.out.println(pClientIP + " " + pClientPort + " aus Wiedersehen. ");
        this.send(pClientIP, pClientPort, ENDE);
    }
}
