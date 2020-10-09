package assignment6.cli;

import assignment6.communication.InvalidMessageFormat;
import assignment6.communication.MessageIdentifier;
import assignment6.message.BroadcastMessage;
import assignment6.message.ConnectMessage;
import assignment6.message.ConnectResponse;
import assignment6.message.DirectMessage;
import assignment6.message.DisconnectMessage;
import assignment6.message.DisconnectResponse;
import assignment6.message.FailedMessage;
import assignment6.message.InsultMessage;
import assignment6.message.QueryMessage;
import assignment6.message.QueryResponse;

/**
 * The type Cli.
 */
public class Cli {

  /**
   * Parse user input string.
   *
   * @param userName the user name
   * @param input    the input
   * @return the string
   */
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
  }

  /**
   * Parse response string.
   *
   * @param rawRes the raw res
   * @return the string
   */
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

  /**
   * Display command line options.
   */
  public static void displayCommandLineOptions() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Chat Room Command Options:\n");
    buffer.append("?: show command line options\n");
    buffer.append("login: CONNECT_MESSAGE to the server\n");
    buffer.append("logoff: sends a DISCONNECT_MESSAGE to the server\n");
    buffer.append("who: sends a QUERY_CONNECTED_USERS to the server\n");
    buffer.append("@user: sends a DIRECT_MESSAGE to the specified user to the server\n");
    buffer.append(
        "@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected\n");
    buffer.append(
        "!user: sends a SEND_INSULT message to the server, to be sent to the specified user");
    System.out.println(buffer.toString());
  }

  /**
   * Print welcome.
   *
   * @param userName the user name
   */
  public static void printWelcome(String userName) {
    System.out.println("Hi, " + userName);
    Cli.displayCommandLineOptions();
  }

  /**
   * Concate string arr string.
   *
   * @param tokens the tokens
   * @param start  the start
   * @return the string
   */
  public static String concateStringArr(String[] tokens, int start) {
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
}
