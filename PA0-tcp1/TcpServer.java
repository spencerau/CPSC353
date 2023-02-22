import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
*  TCP Server Program.
*  Listens on a TCP port
*  Receives a line of input from a TCP client
*  Returns an upper case version of the line to the client
*
*  @author: Michael Fahy
*      Email:  fahy@chapman.edu
*      Date:  2/13/2021
*  @version: 3.2
*/

class TcpServer {

  public static void main(String[] argv) throws Exception {
    String welcomeMessage = "Welcome. Please type a sentence.\n";
    String clientSentence;
    String capitalizedSentence;

    ServerSocket welcomeSocket = null;

    try {
      welcomeSocket = new ServerSocket(6789);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }

    while (true) {
      System.out.println("Waiting for Client to connect.");
      Socket connectionSocket = welcomeSocket.accept();
      BufferedReader inFromClient = new BufferedReader(
          new InputStreamReader(connectionSocket.getInputStream()));
      DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream());
      outToClient.writeBytes(welcomeMessage);
      System.out.println("Welcome message sent.");

      clientSentence = inFromClient.readLine();
      System.out.println(clientSentence);
      capitalizedSentence = clientSentence.toUpperCase() + '\n';
      System.out.println(capitalizedSentence);
      outToClient.writeBytes(capitalizedSentence);

      connectionSocket.close();
    }
  }
}
