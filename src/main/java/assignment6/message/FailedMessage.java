package assignment6.message;

import assignment6.cli.DisplayMessage;
import assignment6.communication.MessageIdentifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The type Broadcast message.
 */
public class FailedMessage implements Message {

  private final static MessageIdentifier identifier = MessageIdentifier.FAILED_MESSAGE;
  private String userName;
  private String content;

  /**
   * Instantiates a new Failed message.
   *
   * @param userName the user name
   * @param content  the content
   */
  public FailedMessage(String userName, String content) {
    this.userName = userName;
    this.content = content;
  }

  @Override
  public byte[] toBytes() {
    List<Byte> res = new ArrayList<>();
    char space = ' ';
    Integer length = userName.getBytes().length;
    res.add(Integer.valueOf(identifier.getValue()).byteValue());
    res.add((byte) space);
    res.add(length.byteValue());
    res.add((byte) space);
    Byte[] resArr = res.toArray(new Byte[0]);
    return ArrayUtils.addAll(ArrayUtils.toPrimitive(resArr), userName.getBytes());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(identifier.getValue());
    sb.append(DELIMIT);
    sb.append(userName.length());
    sb.append(DELIMIT);
    sb.append(userName);
    sb.append(DELIMIT);
    sb.append(content.length());
    sb.append(DELIMIT);
    sb.append(content);
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
    String content = Message.concateStringArr(tokens, 4);
    String sender= tokens[2];
    DisplayMessage display = new DisplayMessage(identifier.getCommand(), sender, content);
    return display.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FailedMessage)) {
      return false;
    }
    FailedMessage that = (FailedMessage) o;
    return Objects.equals(userName, that.userName) &&
        Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, content);
  }
}
