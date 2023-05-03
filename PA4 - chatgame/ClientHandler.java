import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ClientHandler.java
 *
 * <p>This class handles communication between the client
 * and the server. It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *
 */
public class ClientHandler implements Runnable {
  private Client client;
  private Socket connectionSock = null;
  private ArrayList<Client> clientList;

  ClientHandler(Client client, Socket sock, ArrayList<Client> clientList) {
    this.client = client;
    this.clientList = clientList; // Keep reference to master list
    this.connectionSock = sock;
  }

  public void addPoints(int points, Client client) {
    client.score += points;
  }

  /**
   * received input from a client.
   * sends it to other clients.
   */
  public void run() {
    try {
      System.out.println("Connection made with socket " + connectionSock);
      BufferedReader clientInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));

      // Send a message to all clients that a new user has joined
      for (Client c : clientList) {
        if (c.connectionSock != client.connectionSock) {
          DataOutputStream clientOutput = new DataOutputStream(
              c.connectionSock.getOutputStream());
              
          clientOutput.writeBytes(client.username + " has joined the chat.\n");
        }
      }
      while (true) {
        // Get data sent from a client
        String clientText = clientInput.readLine();
        if (clientText != null) {
          //Boolean answer = null;
          switch (clientText) {
            // check if user wants to quit
            case "QUIT":
              for (Client c : clientList) {
                if (c.connectionSock == client.connectionSock) {
                  quit(client);
                  return;
                }
              }
              break;
            // check if user wants to see who is in the chat
            case "Who?":
              who(client);
              break;
            // host asks for scores
            case "SCORES":
              if (client.username.equals("host")) {
                System.out.println("Host is asking for scores.");
                scores(client);
              }
              break;
            default:
              // host asks a question
              if (clientText.startsWith("Q:")) {
                if (client.username.equals("host")) {
                  System.out.println("Host is asking a question.");
                  // strip clientText of the Q: and send it to all clients
                  String question = clientText.substring(3);
                  askQuestion(client, question);
                  // and that they can now award points
                  DataOutputStream hostOutput = new DataOutputStream(
                      client.connectionSock.getOutputStream());
                  hostOutput.writeBytes("You can now award points.\n");
                  hostOutput.writeBytes("Choose which client to award points to ");
                  hostOutput.writeBytes("by typing 'A: <client name>'\n");
                }
                // host awarding points

              } else if (clientText.startsWith("A:")) {
                if (client.username.equals("host")) {
                  System.out.println("Host is awarding points.");
                  // strip clientText of the A: and award points to that user
                  String clientAnswer = clientText.substring(3);
                  awardPoints(clientAnswer);
                }
              } else {
                System.out.println(client.username + ": " + clientText);
                // Turn around and output this data
                // to all other clients except the one
                // that sent us this information
                for (Client c : clientList) {
                  if (c.connectionSock != client.connectionSock) {
                    DataOutputStream clientOutput = new DataOutputStream(
                        c.connectionSock.getOutputStream());
                    clientOutput.writeBytes(client.username + ": " + clientText + "\n");
                  }
                }
              }
              break;
          } 
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          // Remove from arraylist
          clientList.remove(client);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error in CH: " + e.toString());
      // Remove from arraylist
      clientList.remove(client);
    }
  }

  /**
   * Method removes the specified client from the list of active clients and closes its socket. 
   * If the client is currently in a chat room, it is removed from the chat room as well.
   *
   * @param client the client to disconnect from the server (must not be null)
   * @throws IOException if an I/O error occurs while closing the socket connection
   */
  public void quit(Client client) throws IOException {
    System.out.println(client.username + " has quit the chat.");
    clientList.remove(client);
    client.connectionSock.close();
    // inform other clients that user has quit the chat
    for (Client c : clientList) {
      if (c.connectionSock != client.connectionSock) {
        DataOutputStream clientOutput = new DataOutputStream(
            c.connectionSock.getOutputStream());
        clientOutput.writeBytes(client.username + " has quit the chat.\n");
      }
    }
  }

  /**
   * for the Who? call.
   *
   * @param client the client who is calling Who?
   * @throws IOException if an I/O error occurs while closing the socket connection
   */
  public void who(Client client) throws IOException {
    System.out.println(client.username + " has requested a list of users.");
    DataOutputStream clientOutput = new DataOutputStream(
        client.connectionSock.getOutputStream());
    clientOutput.writeBytes("Users in chat: \n");
    for (Client c : clientList) {
      if (c.connectionSock != client.connectionSock) {
        clientOutput.writeBytes(c.username + "\n");
      }
    }
  }

  /**
   * Host asks a question.
   *
   * @param question the question to ask
   * @throws IOException if an I/O error occurs while closing the socket connection
   */
  public void askQuestion(Client host, String question) throws IOException {
    // output the question to other clients
    for (Client c : clientList) {
      if (c.connectionSock != host.connectionSock) {
        DataOutputStream clientOutput = new DataOutputStream(
            c.connectionSock.getOutputStream());
        clientOutput.writeBytes("The has asked a question\n");
        //clientOutput.writeBytes("Reply with 'A: T' or 'A: F'\n");
        clientOutput.writeBytes("The host will see your answers and select the correct one\n");
        clientOutput.writeBytes(client.username + ": " + question + "\n");
      }
    }
  }

  /**
  * This is for A:.
  *
  * @param clientUser the client to be awarded points
  * @throws IOException if an I/O error occurs while closing the socket connection
  */
  public void awardPoints(String clientUser) {
    // award points to the correct client
    // let the client know they were correct
    // let other clients know who was correct
    for (Client c : clientList) {
      if (c.username.equals(clientUser)) {
        c.score++;
        System.out.println(c.username + " has been awarded a point.");
        try {
          DataOutputStream clientOutput = new DataOutputStream(
              c.connectionSock.getOutputStream());
          clientOutput.writeBytes("You were correct!\n");
          clientOutput.writeBytes("You have been awarded a point.\n");
        } catch (IOException e) {
          System.out.println("Error in awardPoints: " + e.toString());
        }
        // let other clients, besides host, know who was correct
      } else if (!c.username.equals("host")) {
        try {
          DataOutputStream clientOutput = new DataOutputStream(
              c.connectionSock.getOutputStream());
          clientOutput.writeBytes(clientUser + " was correct!\n");
        } catch (IOException e) {
          System.out.println("Error in awardPoints: " + e.toString());
        }
      }
    }
  }

  /**
  * scores command for the host.
  * give the host a list of users and their respective scores.
  * send the list of users and scores to each client.
  *
  * @param client the host
  * @throws IOException if an I/O error occurs while closing the socket connection
  */
  public void scores(Client client) throws IOException {
    System.out.println("Scores have been requested.");
    for (Client c : clientList) {
      if (!c.username.equals("host")) {
        // system.out.println() prints to the Server's console
        //System.out.println(c.username + "= " + c.score);
        DataOutputStream clientOutput = new DataOutputStream(
            c.connectionSock.getOutputStream());
        for (Client notHost : clientList) {
          if (!notHost.username.equals("host")) {
            clientOutput.writeBytes(notHost.username + ": " + notHost.score + "\n");
          }
        }
      }
    }
  }

} // ClientHandler for MtServer.java