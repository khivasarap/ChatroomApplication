package assignment6.communication;

import assignment6.cli.Cli;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * The type Client.
 */
public class Client {

  private Socket socket = null;
  private OutputStream outputStream = null;
  private InputStream inputStream = null;
  private String userName;
  private String DELIMITER=" ";

  /**
   * Instantiates a new Client.
   *
   * @param hostName   the host name
   * @param portNumber the port number
   * @param username   the username
   */
// constructor to put ip address and port
  public Client(String hostName, int portNumber, String username) {
    try {
      socket = new Socket(hostName, portNumber);
      if(socket!=null) {
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        //Send username
        outputStream.write(username.getBytes(StandardCharsets.UTF_8));
        userName = username;
        Cli.printWelcome(username);
      }
    } catch (ConnectException | UnknownHostException u){
      System.out.println("Invalid hostname or port number");
    } catch (IOException io) {
      io.printStackTrace();
    }
  }

  /**
   * Create read thread.
   */
  public void createReadThread() {
    Thread readThread = new Thread(() -> {
      while (socket.isConnected()) {
        try {
          //in case a insult sentence were too long
          byte[] readBuffer = new byte[2000];
          int num = inputStream.read(readBuffer);
          if (num > 0) {
            byte[] arrayBytes = new byte[num];
            System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
            String received = new String(arrayBytes, StandardCharsets.UTF_8);
            //get responseCode
            String responseCode = received.split(DELIMITER)[0];
            //output messages on screen
            System.out.println(Cli.parseResponse(received));
            //if is disconnect response, stop the program
            if(responseCode.equals(MessageIdentifier.DISCONNECT_RESPONSE.getValue())){
              this.outputStream.close();
              this.inputStream.close();
              this.socket.close();
            }
            //if is connect response, and status is false, stop the program
            if(responseCode.equals(MessageIdentifier.CONNECT_RESPONSE.getValue())){
              boolean status=Boolean.parseBoolean(received.split(DELIMITER)[1]) ;
              if(!status){
                this.outputStream.close();
                this.inputStream.close();
                this.socket.close();
              }
            }
          }
        } catch (SocketException se) {
          System.exit(0);

        } catch (IOException i) {
          i.printStackTrace();
        }
      }
    });
    readThread.start();
  }

  /**
   * Create write thread.
   */
  public void createWriteThread() {
    Thread writeThread = new Thread(() -> {
      while (socket.isConnected()) {
        try {
          BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
          String userMessage = inputReader.readLine();
          if (userMessage != null && userMessage.length() == 1 && userMessage.equals("?")) {
            Cli.displayCommandLineOptions();
          } else if (userMessage != null && userMessage.length() > 0) {
            synchronized (socket) {
              String protocolString = Cli.parseUserInput(userName, userMessage);
              outputStream.write(protocolString.getBytes(StandardCharsets.UTF_8));
            }
          }
        } catch (InvalidMessageFormat m) {
          System.out.println(m.getMessage());
        } catch (IOException i) {
          i.printStackTrace();
        }
      }
    });
    writeThread.start();
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws IOException the io exception
   */
  public static void main(String[] args) throws IOException {
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    Client client = getConnectInfoFromUser(inputReader);
    if(client!=null && client.socket!=null) {
      client.createReadThread();
      client.createWriteThread();
    }
  }

  private static Client getConnectInfoFromUser(BufferedReader input) throws IOException {
    System.out.println("Enter hostname:");
    //String hostname = "127.0.0.1";
    String hostname = input.readLine();
    System.out.println("Enter port:");
   // String portNumber = "5000";
    String portNumber = input.readLine();
    System.out.println("Enter username:");
    String username = input.readLine();
    return new Client(hostname, Integer.parseInt(portNumber), username);
  }

  //Added for testing purpose only

  public InputStream getInputStream() {
    return inputStream;
  }

  public OutputStream getOutputStream() {
    return outputStream;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Client client = (Client) o;
    return Objects.equals(socket, client.socket) &&
        Objects.equals(outputStream, client.outputStream) &&
        Objects.equals(inputStream, client.inputStream) &&
        Objects.equals(userName, client.userName) &&
        Objects.equals(DELIMITER, client.DELIMITER);
  }

  @Override
  public int hashCode() {
    return Objects.hash(socket, outputStream, inputStream, userName, DELIMITER);
  }
}
