package assignment6.communication;

import assignment6.message.BroadcastMessage;
import assignment6.message.ConnectResponse;
import assignment6.message.DirectMessage;
import assignment6.message.DisconnectResponse;
import assignment6.message.FailedMessage;
import assignment6.message.InsultMessage;
import assignment6.message.QueryResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.cli.ParseException;
import problem1.InvalidCharacterException;
import problem1.InvalidFileFormatException;
import problem1.NoCorrespondingTerminalException;
import problem1.SentenceGenerator;

/**
 * The type Client handler.
 */
public class ClientHandler extends Thread {

  //initialize socket and input stream
  private boolean keepRunning = true;
  private Socket socket;
  private int counter;
  private OutputStream serverOut;
  private InputStream serverInput;
  private static Integer MAX_LIMIT = 10;


  /**
   * Instantiates a new Client handler.
   *
   * @param socket       the socket
   * @param counter      the counter
   * @param outputStream the output stream
   * @param inputStream  the input stream
   */
  public ClientHandler(Socket socket, int counter, OutputStream outputStream, InputStream inputStream) {
    this.socket = socket;
    this.counter = counter;
    this.serverOut = outputStream;
    this.serverInput = inputStream;
  }

  /**
   * Process read.
   */
  public void processRead() {
      while (this.socket.isConnected() && keepRunning) {
        try {
          byte[] readBuffer = new byte[2000];
          int num = serverInput.read(readBuffer);
          if (num > 0) {
            byte[] arrayBytes = new byte[num];
            System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
            String received = new String(arrayBytes, StandardCharsets.UTF_8);
            System.out.println(received);
            String DELIMITER=" ";
            String[] receivedMessages = received.split(DELIMITER);
            try {
              processConnectMessage(receivedMessages);
              processDisconnectMessage(receivedMessages);
              processBroadcastMessage(receivedMessages);
              processQueryConnectedUsers(receivedMessages);
              processDirectMessages(receivedMessages);
              processInsultMessages(receivedMessages);
            } catch (ArrayIndexOutOfBoundsException a){
              System.out.println("Less arguments specified for messages!");
            } catch (InvalidCharacterException e) {
              e.printStackTrace();
            } catch (ParseException e) {
              e.printStackTrace();
            } catch (InvalidFileFormatException e) {
              e.printStackTrace();
            } catch (NoCorrespondingTerminalException e) {
              e.printStackTrace();
            } catch (ChatRoomFullException e) {
              System.out.println(e.getMessage());
              String username = (e.getMessage().split("! ")[1]).split(" ")[0];
              Server.activeClients.put(username, this); // Add temporarily, will be removed as part of Disconnect
              String[] disconnectFormat = {MessageIdentifier.DISCONNECT_MESSAGE.getValue(),
                  String.valueOf(username.length()), username};
              processDisconnectMessage(disconnectFormat);
            }
          } else {
            try {
              notify();
            } catch (IllegalMonitorStateException im) {
              //Do nothing, wait for other clients to join
            }
          }
        } catch (SocketException se) {
          //Client's socket is closed, thus make an exit for Client
          System.exit(0);
        } catch (IOException i) {
          i.printStackTrace();
        }
      }
  }

  /**
   * Process write.
   *
   * @param clientHandler the client handler
   * @param content       the content
   */
  public void processWrite(ClientHandler clientHandler, String content) {
    if (clientHandler != null && Server.activeClients.contains(clientHandler)) {
      try {
        clientHandler.serverOut.write(content.getBytes(
            StandardCharsets.UTF_8));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void processConnectMessage(String[] receivedMessages)
      throws ArrayIndexOutOfBoundsException, ChatRoomFullException {
    String messageIdentifier = receivedMessages[0];
    if (MessageIdentifier.CONNECT_MESSAGE.getValue().equals(messageIdentifier)) {
      String username = receivedMessages[2];
      if (username != null && !username.isEmpty()) {
        if (Server.activeClients.size() < MAX_LIMIT) {
          Server.activeClients.put(username, this);
          System.out.println("Connected " + username);
          String message =
              "login successful. There are " + Server.activeClients.size() + " connected clients";
          ConnectResponse connectResponse=new ConnectResponse(true,message);
          processWrite(this, connectResponse.toString());
        } else {
          throw new ChatRoomFullException("Currently Chat Room is full! "+ username +" Please try later!");
        }
        if(!Server.activeClients.containsKey(username)){
         generateFailedMessage(username, "Failed Message: Connect failed for user: "+ username);
        }
      }
    }
  }

public void processDisconnectMessage(String[] receivedMessages)
    throws ArrayIndexOutOfBoundsException, IOException {
  String messageIdentifier = receivedMessages[0];
  if(MessageIdentifier.DISCONNECT_MESSAGE.getValue().equals(messageIdentifier)) {
    String username = receivedMessages[2];
    if (username != null && !username.isEmpty()) {
      System.out.println("Disconnecting " + username);
      DisconnectResponse disconnectResponse=new DisconnectResponse(username);
      processWrite(this, disconnectResponse.toString());
      Server.activeClients.remove(username);
      keepRunning = false;
    }
  }
}

private void processBroadcastMessage(String[] receivedMessages) throws ArrayIndexOutOfBoundsException{
  String messageIdentifier = receivedMessages[0];
  if(MessageIdentifier.BROADCAST_MESSAGE.getValue().equals(messageIdentifier)) {
    String senderUserName = receivedMessages[2];
    String message = concateStringArr(receivedMessages,4);
    if (senderUserName != null && !senderUserName.isEmpty()
        && message != null && !message.isEmpty()) {
      if(Server.activeClients.containsKey(senderUserName)) {
        Server.activeClients.forEach((k, v) -> {
          synchronized (v.getSocket()) {
            try {
              BroadcastMessage broadcastMessage = new BroadcastMessage(senderUserName, message);
              v.serverOut.write(broadcastMessage.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
      } else {
            generateFailedMessage(senderUserName, "Failed Message: Invalid Sender Name");
        }
    }
  }
}

private void processQueryConnectedUsers(String[] receivedMessages) throws ArrayIndexOutOfBoundsException {
  String messageIdentifier = receivedMessages[0];
  if (MessageIdentifier.QUERY_CONNECTED_USERS.getValue().equals(messageIdentifier)) {
    String userRequesting = receivedMessages[2];
    if (userRequesting != null && !userRequesting.isEmpty()) {
      if (Server.activeClients.containsKey(userRequesting)) {
        QueryResponse queryResponse=new QueryResponse(userRequesting,Server.activeClients);
        processWrite(this, queryResponse.toString());
      }
    }
  }
}

private void processDirectMessages(String[] receivedMessages) throws ArrayIndexOutOfBoundsException{
  String messageIdentifier = receivedMessages[0];
  if (MessageIdentifier.DIRECT_MESSAGE.getValue().equals(messageIdentifier)) {
    String senderName = receivedMessages[2];
    String recipientName = receivedMessages[4];
    String message = concateStringArr(receivedMessages,6);
    if (senderName != null && !senderName.isEmpty() && recipientName != null
        && !recipientName.isEmpty() && message != null && !message.isEmpty()) {
      if (Server.activeClients.containsKey(recipientName) &&
          Server.activeClients.containsKey(senderName)) {
        Server.activeClients.forEach((k, v) -> {
          if (k.equals(recipientName)) {
            synchronized (v.getSocket()) {
              try {
                DirectMessage directMessage=new DirectMessage(senderName,recipientName,message);
                v.serverOut.write(directMessage.toString().getBytes(StandardCharsets.UTF_8));
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        });
      } else {
        if(!Server.activeClients.containsKey(recipientName)){
          generateFailedMessage(recipientName, "Failed Message:Invalid Recipient Name");
        }
        if(!Server.activeClients.containsKey(senderName))
        generateFailedMessage(senderName, "Failed Message: Invalid Sender Name");
      }
    }
  }
}

  private void generateFailedMessage(String senderName, String message) {
    try {
      FailedMessage failedMessage=new FailedMessage(senderName,message);
      this.serverOut.write(failedMessage.toString().getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void processInsultMessages(String[] receivedMessages)
      throws ArrayIndexOutOfBoundsException, IOException, InvalidFileFormatException, ParseException, NoCorrespondingTerminalException, InvalidCharacterException {
    String messageIdentifier = receivedMessages[0];
    if (MessageIdentifier.SEND_INSULT.getValue().equals(messageIdentifier)) {
        String senderUserName = receivedMessages[2];
        String recipientName = receivedMessages[4];
        File grammarFile = new File("Grammar/insult_grammar.json");
        String message = SentenceGenerator.generateSentence(grammarFile).toString();
        if (senderUserName != null && !senderUserName.isEmpty() &&
        !recipientName.isEmpty() && message != null && !message.isEmpty()) {
          message=senderUserName+" -> "+recipientName+" "+message;
          String finalMessage = message;
          Server.activeClients.forEach((k, v) -> {
              synchronized (v.getSocket()) {
                try {
                  BroadcastMessage broadcastMessage=new BroadcastMessage(senderUserName,
                      finalMessage);
                  v.serverOut.write(broadcastMessage.toString().getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
          });
        }
      }
  }

  @Override
  public void run() {
    try {
      while (keepRunning) {
        serverInput = this.socket.getInputStream();
        serverOut = this.socket.getOutputStream();
        processRead();
      }
    } catch (IOException io) {
      io.printStackTrace();
    } finally {
      this.stop();
    }
  }

  private static String concateStringArr(String[] tokens, int start) {
    String res = "";
    for (int i = start; i < tokens.length; i++) {
      if (i != tokens.length - 1) {
        res += tokens[i] + " ";
      } else {
        res += tokens[i];
      }
    }
    return res;
  }

  /**
   * Gets socket.
   *
   * @return the socket
   */
  public Socket getSocket() {
    return socket;
  }

  public boolean isKeepRunning() {
    return keepRunning;
  }

  public void setKeepRunning(boolean keepRunning) {
    this.keepRunning = keepRunning;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientHandler that = (ClientHandler) o;
    return keepRunning == that.keepRunning &&
        counter == that.counter &&
        Objects.equals(socket, that.socket) &&
        Objects.equals(serverOut, that.serverOut) &&
        Objects.equals(serverInput, that.serverInput);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keepRunning, socket, counter, serverOut, serverInput);
  }
}
