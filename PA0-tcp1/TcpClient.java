import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
/**
*  TCP Client Program
*  Connects to a TCP Server
*  Receives a line of input from the keyboard and sends it to the server
*  Receives a response from the server and displays it.
*
*  @author: Michael Fahy
*      Email:  fahy@chapman.edu
*      Date:  2/13/2021
*  @version: 3.2
*/

class TcpClient {
  public static void main(String[] argv) throws Exception {
    Socket clientSocket = null;

    try {
      clientSocket = new Socket("localhost", 6789);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }

    BufferedReader inFromServer =  new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
    String welcomeMessage = inFromServer.readLine();
    System.out.println("FROM SERVER: " + welcomeMessage);

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    String sentence = inFromUser.readLine();

    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    outToServer.writeBytes(sentence + '\n');

    String modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);

    clientSocket.close();

  }
}
