import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
*  UDP Client Program
*  Connects to a UDP Server
*  Receives a line of input from the keyboard and sends it to the server
*  Receives a response from the server and displays it.
*
*  @author: Spencer Au
*     email: spau@chapman.edu
*  @author: Ewan Shen
*     email: ewshen@chapman.edu
*     date: 2/4/2019
*  @version: 3.1
*
*/


class MyUdpClient {
  public static void main(String[] args) throws Exception {

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    DatagramSocket clientSocket = new DatagramSocket();

    InetAddress ipAddress = InetAddress.getByName("icd.chapman.edu");

    String sentence = "initialization";
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];
    DatagramPacket sendPacket;
    DatagramPacket receivePacket;
    String modifiedSentence;

    while (sentence != "Goodbye") {
      System.out.println("Type a Sentence");
      sentence = inFromUser.readLine();
      sendData = sentence.getBytes();
      sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 9876);
      clientSocket.send(sendPacket);
      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      modifiedSentence = new String(receivePacket.getData());
      System.out.println("FROM SERVER: " + modifiedSentence);
      if (sentence.equals("Goodbye")) break;
    }
    clientSocket.close();
  }
}
