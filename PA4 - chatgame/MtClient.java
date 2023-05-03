import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * MTClient.java
 * 
 * <p>This program implements a simple multithreaded chat client.  It connects to the
 * server (assumed to be localhost on port 7654) and starts two threads:
 * one for listening for data sent from the server, and another that waits
 * for the user to type something in that will be sent to the server.
 * Anything sent to the server is broadcast to all clients.
 * 
 * <p>The MTClient uses a ClientListener whose code is in a separate file.
 * The ClientListener runs in a separate thread, recieves messages form the server,
 * and displays them on the screen.
 *  
 * <p>Data received is sent to the output screen, so it is possible that as
 * a user is typing in information a message from the server will be
 * inserted.
 */
public class MtClient {
  /**
   * main method.
   *
   * @params not used.
   */
  public static void main(String[] args) {
    try {
      String hostname = "localhost";
      int port = 9028;

      System.out.println("Connecting to server on port " + port);
      Socket connectionSock = new Socket(hostname, port);

      DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

      System.out.println("Connection made.");

      // Start a thread to listen and display data sent by the server
      ClientListener listener = new ClientListener(connectionSock);
      Thread theThread = new Thread(listener);
      theThread.start();


      // Read input from the keyboard and send it to everyone else.
      Scanner keyboard = new Scanner(System.in);
      Boolean cont = true;
      
      while (cont) {
        String data = keyboard.nextLine();
        if (data.equals("QUIT")) {
          cont = false;
        }
        serverOutput.writeBytes(data + "\n");
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
} // MtClient
