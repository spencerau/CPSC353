import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
*  TCP Client Program.
*  Receives two sentences of input from the keyboard and
*  stores them in separate variables.
*  Connects to a TCP Server.
*  Waits for a Welcome message from the server.
*  Sends the first sentence to the server.
*  Receives a response from the server and displays it.
*  Sends the second sentence to the server.
*  Receives a second response from the server and displays it.
*  Closes the socket and exits.

*  author1: Ewan Shen
*  Email:  ewshen@chapman.edu
*  Date:  2/14/23
*  -
*  author2: Spencer Au
*  Email: spau@chapman.edu 
*/

class Email {

  public static void main(String[] argv) throws Exception {
    // Get user input
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    final String fromAd = inFromUser.readLine();
    final String toAd = inFromUser.readLine();
    final String from = inFromUser.readLine();
    final String to = inFromUser.readLine();
    final String subject = inFromUser.readLine();

    ArrayList<String> body = new ArrayList<>();
    String temp = inFromUser.readLine();
    while (!temp.equals(".")) {
      body.add(temp);
      temp = inFromUser.readLine();
    }

    // Connect to the server
    Socket clientSocket = null;

    try {
      clientSocket = new Socket("smtp.chapman.edu", 25);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }
    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
    BufferedReader inFromServer =  new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));

    // Exchange messages with the server
    // Recive and display the Welcome Message
    String welcomeMessage = inFromServer.readLine();
    System.out.println("SERVER: " + welcomeMessage);
    
    // Hello message
    outToServer.println("HELO icd.chapman.edu");
    System.out.println("CLIENT: HELO icd.chapman.edu");
    System.out.println("SERVER: " + inFromServer.readLine());

    // from email address
    outToServer.println("MAIL FROM: " + fromAd);
    System.out.println("CLIENT: MAIL FROM: " + fromAd);
    System.out.println("SERVER: " + inFromServer.readLine());

    // to email address
    outToServer.println("RCPT TO: " + toAd);
    System.out.println("CLIENT: RCPT TO: " + toAd);
    System.out.println("SERVER: " + inFromServer.readLine());

    outToServer.println("DATA");
    System.out.println("CLIENT: DATA");
    System.out.println("SERVER: " + inFromServer.readLine());

    // sender
    outToServer.println("From: " + from);
    System.out.println("CLIENT: From: " + from);

    // recipient
    outToServer.println("To: " + to);
    System.out.println("CLIENT: To: " + to);

    // subject
    outToServer.println("Subject: " + subject);
    System.out.println("CLIENT: Subject: " + subject);

    // body of email
    for (int i = 0; i < body.size(); i++) {
      outToServer.println(body.get(i));
      System.out.println("CLIENT: " + body.get(i));
    }
    // end body
    outToServer.println(".");
    System.out.println("CLIENT: .");
    System.out.println("SERVER: " + inFromServer.readLine());

    outToServer.println("QUIT");
    System.out.println("CLIENT: QUIT");
    System.out.println("SERVER: " + inFromServer.readLine());

    // Close the socket connection

    clientSocket.close();
  }
}
