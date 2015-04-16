public class Receiver extends Thread
    {
        private ChatClient client;
        private Connection connection;
 
        private boolean active;
 
        public Receiver(ChatClient client, Connection connection) {
            this.client = client;
            this.connection = connection;
            active = true;
        }
 
        public void run(){
            String message;
            boolean received = true;
 
            do{
                message = connection.receive();
                received = (message != null);
                if(received){
                    client.process(message);
                }
            } while(active && received);
        }
        
    }