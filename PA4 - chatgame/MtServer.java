import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * MTServer.java
 *
 * <p>This program implements a simple multithreaded chat server.  Every client that
 * connects to the server can broadcast data to all other clients.
 * The server stores an ArrayList of sockets to perform the broadcast.
 *
 * <p>The MTServer uses a ClientHandler whose code is in a separate file.
 * When a client connects, the MTServer starts a ClientHandler in a separate thread
 * to receive messages from the client.
 *
 * <p>To test, start the server first, then start multiple clients and type messages
 * in the client windows.
 *
 */

public class MtServer {
  // Maintain list of all client sockets for broadcast
  //private ArrayList<Socket> socketList;
  private ArrayList<Client> clientList;

  public MtServer() {
    //socketList = new ArrayList<Socket>();
    clientList = new ArrayList<Client>();
  }

  private void getConnection() {
    // Wait for a connection from the client
    try {
      System.out.println("Waiting for client connections on port 9028");
      ServerSocket serverSock = new ServerSocket(9028);
      // This is an infinite loop, the user will have to shut it down
      // using control-c
      while (true) {

        Socket connectionSock = serverSock.accept();
        BufferedReader clientInput = new BufferedReader(new 
            InputStreamReader(connectionSock.getInputStream()));
        
        // prompt the client to enter in a username
        DataOutputStream clientOutput = new DataOutputStream(connectionSock.getOutputStream());

        boolean isUnique = false;
        String username = "";
        while (!isUnique) {
          clientOutput.writeBytes("Enter a username: \n");
          username = clientInput.readLine();
          // Check if the username is unique by comparing it with the Client objects in the array
          isUnique = true;
          for (Client client : clientList) {
            if (client != null && client.username.equals(username)) {
              clientOutput.writeBytes("Username already taken\n");
              clientOutput.writeBytes("Please enter a different username.\n");
              isUnique = false;
              break;
            }
          }
        }
        clientOutput.writeBytes("username accepted\n");
        if (username.equals("host")) {
          clientOutput.writeBytes("You are the host\n");
          clientOutput.writeBytes("You can use the SCORES command for the scores of all players");
          clientOutput.writeBytes(" and to send each players score to them\n");
          clientOutput.writeBytes("To ask a question, begin a message with 'Q: '");
          clientOutput.writeBytes("and then type your question\n");
        }
      
        clientOutput.writeBytes("List of Possible Commands:\n");
        clientOutput.writeBytes("Who?: list all users in the chat\n");
        clientOutput.writeBytes("QUIT: quit the chat\n");


        // Create a new Client object and add it to the list
        Client newClient = new Client(connectionSock, username);
        clientList.add(newClient);

        // Notify all clients that a new user has joined
        System.out.println(username + " has joined the chat");

        // Send to ClientHandler the Client object and arraylist of all clients
        ClientHandler handler = new ClientHandler(newClient, connectionSock, this.clientList);
        Thread theThread = new Thread(handler);
        theThread.start();
      }
      // Will never get here, but if the above loop is given
      // an exit condition then we'll go ahead and close the socket
      //serverSock.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    MtServer server = new MtServer();
    server.getConnection();
  }
} // MtServer
