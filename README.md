# Assignment 6

Group Assignment 6 by Pooja and Tao.

## Get Started:

Step 1 : Start the **Server** by running the main method in **Server.java**. This is the server instance which will keep doing server.accept() to accept Clients.

Step 2: Start the **Client** by running the main method in **Client.java**.

Enter hostname, Enter port(on which the server is listening = 5000), Enter username

`Example`

```bash
Enter hostname:
127.0.0.1
Enter port:
5000
Enter username:
tao
```

A welcome message will be displayed to user: Hello, **<username>**, with Chat Room Protocol details:

**Chat Room Command Options:**
?: show command line options
login: CONNECT_MESSAGE to the server
logoff: sends a DISCONNECT_MESSAGE to the server
who: sends a QUERY_CONNECTED_USERS to the server
@user: sends a DIRECT_MESSAGE to the specified user to the server
@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected
!user: sends a SEND_INSULT message to the server, to be sent to the specified user



**Example:**

```
Client1:
Enter hostname:
127.0.0.1
Enter port:
5000
Enter username:
pooja
Hi, pooja
Chat Room Command Options:
?: show command line options
login: CONNECT_MESSAGE to the server
logoff: sends a DISCONNECT_MESSAGE to the server
who: sends a QUERY_CONNECTED_USERS to the server
@user: sends a DIRECT_MESSAGE to the specified user to the server
@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected
!user: sends a SEND_INSULT message to the server, to be sent to the specified user
Hi, pooja
Chat Room Command Options:
?: show command line options
login: CONNECT_MESSAGE to the server
logoff: sends a DISCONNECT_MESSAGE to the server
who: sends a QUERY_CONNECTED_USERS to the server
@user: sends a DIRECT_MESSAGE to the specified user to the server
@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected
!user: sends a SEND_INSULT message to the server, to be sent to the specified user
login
 (ClientHandler) SERVER: login successful. There are 1 connected clients
 (@user) tao->pooja: How are you?
@all I am good
 (@all) pooja: I am good
 (@all) tao: tao -> pooja You freeze-dried mountain of freeze-dried lizard guts. May a freeze-dried platoon of crazed gnats viciously dance upon your heinie. You are so revolting that even a twinkie would not want to worship you. With the force of Alah' s fist, may a freeze-dried platoon of crazed gnats viciously dance upon your heinie.

```



```
Client2:
Enter hostname:
127.0.0.1
Enter port:
5000
Enter username:
tao
Hi, tao
Chat Room Command Options:
?: show command line options
login: CONNECT_MESSAGE to the server
logoff: sends a DISCONNECT_MESSAGE to the server
who: sends a QUERY_CONNECTED_USERS to the server
@user: sends a DIRECT_MESSAGE to the specified user to the server
@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected
!user: sends a SEND_INSULT message to the server, to be sent to the specified user
Chat Room Command Options:
?: show command line options
login: CONNECT_MESSAGE to the server
logoff: sends a DISCONNECT_MESSAGE to the server
who: sends a QUERY_CONNECTED_USERS to the server
@user: sends a DIRECT_MESSAGE to the specified user to the server
@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected
!user: sends a SEND_INSULT message to the server, to be sent to the specified user
login
 (ClientHandler) SERVER: login successful. There are 2 connected clients
who tao
 (ClientHandler) SERVER: There is 1 other users: pooja
@user pooja How are you? 
(@all) pooja: I am good
!user pooja
 (@all) tao: tao -> pooja You freeze-dried mountain of freeze-dried lizard guts. May a freeze-dried platoon of crazed gnats viciously dance upon your heinie. You are so revolting that even a twinkie would not want to worship you. With the force of Alah' s fist, may a freeze-dried platoon of crazed gnats viciously dance upon your heinie.

```

In case if ChatRoom has more than 10 Clients, **ChatRoomFullException** will be generated and Client will be added only if some previously connected Client exists the room.

## Classes:

### Intro

There are three Packages in this project, **Communication**, **Cli**,and **Message**. 

**Communication** contains all the `server` and `client` class and it's the main extrance of our program. **Cli** contains utilization class to parse user input and protocol, and **Message** is where we implement detailed logic for every concrete message in the protocol of the spec.

### Communication Package:

#### Server:

This initializes the instance of Server. 

When the server starts up, it starts listening on an available port. Maintains a ConcurrentHashMap to handle multiple clients.

```java
static ConcurrentHashMap<String, ClientHandler> activeClients = new ConcurrentHashMap<String, ClientHandler>();
```

#### Client: 

Creates Client for communication with the server.

Has 2 threads of it's own: **Read** thread and **Write** thread to ensure Client can always do parallely:

1.  write to output stream (Send to server user message)
2. read from input stream (Receive from server responses which are other Client's messages)

#### ClientHandler:

The client handler handles up to 10 clients connected at a single time. It is responsible for listening for messages from the client as well as sending messages to the client according to protocol. 

For various message Types, it will handle the response accordingly as per protocol and writes to output stream for the Client to read.

Example (Direct Message):

```java

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
```

### Cli package

#### Cli:

Cli plays role of parsing the user command input and raw protocol content received from server.

- parse user input

  It parse user input by function `parseUserInput`. Basically, this function receives user input and, if it is valid,  generate an instance of corresponding message class in message package. Using toString method in message class, the method transfer the user input into valid protocol content and return it.

  ```java
  public static String parseUserInput(String userName, String input) {
      String DELIMITER = " ";
      String[] tokens = input.split(DELIMITER);
      String command = tokens[0];
      if (command.equals("?")) {
        Cli.displayCommandLineOptions();
        return "";
      }
      MessageIdentifier commandEnum = null;
      for (MessageIdentifier item : MessageIdentifier.values()) {
        if (command.equals(item.getCommand())) {
          commandEnum = item;
        }
      }
      byte[] res = null;
      if (commandEnum == null) {
        throw new InvalidMessageFormat(
            "Please send message in valid commands as per protocol of chat room!");
      }
      try {
        switch (commandEnum) {
          case CONNECT_MESSAGE: {
            return new ConnectMessage(userName).toString();
          }
          case DISCONNECT_MESSAGE: {
            return new DisconnectMessage(userName).toString();
          }
          case QUERY_CONNECTED_USERS: {
            return new QueryMessage(userName).toString();
          }
          case BROADCAST_MESSAGE: {
            String content = concateStringArr(tokens, 1);
            return new BroadcastMessage(userName, content).toString();
          }
          case DIRECT_MESSAGE: {
            String recipent = tokens[1];
            String content = concateStringArr(tokens, 2);
            return new DirectMessage(userName, recipent, content).toString();
          }
          case SEND_INSULT: {
            String recipent = tokens[1];
            return new InsultMessage(userName, recipent).toString();
          }
        }
        throw new InvalidMessageFormat("Please enter valid commands as per protocol of chat room!");
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new InvalidMessageFormat("Less arguments specified in commands!");
      }
  ```

- Parse Server response

  The `parseResponse` method is used by `Client` to parse raw protocol and transfer it into more human readable format, for example the Direct Message in protocol is like this:

  ```bash
  25 4 test 4 test 4 test
  ```

  And this method transfer it into

  ```bash
   (@user) test->test: test
  ```

  and Print it on the screen.

  This method is based on displayContent method of several message class.

  ``` java
  public static String parseResponse(String rawRes) {
      String DELIMITER = " ";
      String[] token = rawRes.split(DELIMITER);
      String valueCode = token[0];
      MessageIdentifier messageIdentifier = null;
      for (MessageIdentifier iter : MessageIdentifier.values()) {
        if (iter.getValue().equals(valueCode)) {
          messageIdentifier = iter;
        }
      }
      if (messageIdentifier == null) {
        throw new InvalidMessageFormat(
            "Received invalid response");
      }
      switch (messageIdentifier) {
        case CONNECT_RESPONSE:
          return ConnectResponse.displayContent(rawRes);
        case DIRECT_MESSAGE:
          return DirectMessage.displayContent(rawRes);
        case BROADCAST_MESSAGE:
          return BroadcastMessage.displayContent(rawRes);
        case QUERY_USER_RESPONSE:
          return QueryResponse.displayContent(rawRes);
        case DISCONNECT_RESPONSE:
          return DisconnectResponse.displayContent(rawRes);
        case FAILED_MESSAGE:
          return FailedMessage.displayContent(rawRes);
      }
      throw new InvalidMessageFormat(
          "Received invalid response");
    }
  ```

### Message Package

We encapsulate all the method to parse protocol and user input in corresponding class of Message Pacakge. An example of `ConnectResponse` would be used to illustrate how the class in message package works.

`ConnectResponse` (Other code unrelated to implementation logic is omitted)

```java

public class ConnectResponse implements Message {

  private final static MessageIdentifier identifier = MessageIdentifier.CONNECT_RESPONSE;
  private final static String SENDER = "SERVER";
  private boolean isSuccess;
  private String msg;

  /**
   * Instantiates a new Connect response.
   *
   * @param isSuccess the is success
   * @param msg       the msg
   */
  public ConnectResponse(boolean isSuccess, String msg) {
    this.isSuccess = isSuccess;
    this.msg = msg;
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(identifier.getValue());
    sb.append(DELIMIT);
    sb.append(isSuccess);
    sb.append(DELIMIT);
    sb.append(msg.length());
    sb.append(DELIMIT);
    sb.append(msg);
    return sb.toString();
  }
  /**
   * Display content string.
   *
   * @param rawInput the raw input
   * @return the string
   */
  public static String displayContent(String rawInput) {
    String[] tokens = rawInput.split(DELIMIT);
    String content = Message.concateStringArr(tokens, 3);
    DisplayMessage display = new DisplayMessage(identifier.getCommand(), SENDER, content);
    return display.toString();
  }
}
```

There are three methods of a typical message class, **Constructor**, **toString**,**displayContent**.

- Constructor

  Build the message and get all the information necessary according to protocol

- toString

  Transfer this message into protocol format

- displayContent

  Receives a message in protocol format, and transfer it into an instance of `DisplayMessage` class, which is a class for output user-friendly string output.

## Other Attemption

![image-20200413171610112](https://tva1.sinaimg.cn/large/007S8ZIlgy1gdszd23wr6j311a0lmdiv.jpg)

We tried to use a library called `lanterna` to support message rendering in terminal user interface. However, in test phrase, it was found only supports unix-terminal by our setting. Therefore this feature was abandoned. But we still like to mention it in README as a valuable attemption.