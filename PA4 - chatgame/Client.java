import java.net.Socket;
import java.util.ArrayList;

/**
 * Client.java
 *
 * <p>
 * This class creates a client object that stores the
 * username of the client and the clients socket number
 * </p>
 * 
 */

public class Client {

  public Socket connectionSock = null;

  public String username = "";

  public ArrayList<Client> clientList;

  public int score = 0;

  Client(Socket sock, String username) {

    this.connectionSock = sock;

    this.username = username;

  }

}

