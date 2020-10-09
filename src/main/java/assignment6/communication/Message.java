package assignment6.communication;

import java.util.Arrays;
import java.util.Objects;

/**
 * The type Message.
 */
public class Message {
  private MessageIdentifier messageIdentifier;
  private byte[] username;
  private int usernameSize;

  /**
   * Instantiates a new Message.
   *
   * @param messageIdentifier the message identifier
   * @param username          the username
   * @param usernameSize      the username size
   */
  public Message(MessageIdentifier messageIdentifier, byte[] username, int usernameSize) {
    this.messageIdentifier = messageIdentifier;
    this.username = username;
    this.usernameSize = usernameSize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Message message = (Message) o;
    return usernameSize == message.usernameSize &&
        messageIdentifier == message.messageIdentifier &&
        Arrays.equals(username, message.username);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(messageIdentifier, usernameSize);
    result = 31 * result + Arrays.hashCode(username);
    return result;
  }
}
