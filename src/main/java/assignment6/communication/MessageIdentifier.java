package assignment6.communication;

/**
 * The enum Message identifier.
 */
public enum MessageIdentifier {
  /**
   * Connect message message identifier.
   */
  CONNECT_MESSAGE("19","login"),
  /**
   * Connect response message identifier.
   */
  CONNECT_RESPONSE("20","ClientHandler"),
  /**
   * Disconnect message message identifier.
   */
  DISCONNECT_MESSAGE("21","logoff"),
  /**
   * Query connected users message identifier.
   */
  QUERY_CONNECTED_USERS("22","who"),
  /**
   * Query user response message identifier.
   */
  QUERY_USER_RESPONSE("23","ClientHandler"),
  /**
   * Broadcast message message identifier.
   */
  BROADCAST_MESSAGE("24","@all"),
  /**
   * Direct message message identifier.
   */
  DIRECT_MESSAGE("25","@user"),
  /**
   * Failed message message identifier.
   */
  FAILED_MESSAGE("26","ClientHandler"),
  /**
   * Send insult message identifier.
   */
  SEND_INSULT("27","!user"),
  /**
   * Disconnect response message identifier.
   */
  DISCONNECT_RESPONSE("28","ClientHandler");
  private String value;
  private String command;

  MessageIdentifier(String value) {
    this.value = value;
  }

  MessageIdentifier(String value, String command) {
    this.value = value;
    this.command = command;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Gets command.
   *
   * @return the command
   */
  public String getCommand() {
    return command;
  }
}
