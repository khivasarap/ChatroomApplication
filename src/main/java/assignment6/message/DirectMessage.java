package assignment6.message;

import assignment6.cli.DisplayMessage;
import assignment6.communication.MessageIdentifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The type Direct message.
 */
public class DirectMessage implements Message {

  private final static MessageIdentifier identifier = MessageIdentifier.DIRECT_MESSAGE;
  private String userName;
  private String recipentName;
  private String content;

  /**
   * Instantiates a new Direct message.
   *
   * @param userName     the user name
   * @param recipentName the recipent name
   * @param content      the content
   */
  public DirectMessage(String userName, String recipentName, String content) {
    this.userName = userName;
    this.recipentName = recipentName;
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


  /**
   * Display content string.
   *
   * @param rawInput the raw input
   * @return the string
   */
  public static String displayContent(String rawInput) {
    String[] tokens = rawInput.split(DELIMIT);
    String content = Message.concateStringArr(tokens, 6);
    String recipent= tokens[4];
    String sender= tokens[2];
    DisplayMessage display = new DisplayMessage(identifier.getCommand(), sender+"->"+recipent, content);
    return display.toString();
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
    sb.append(recipentName.length());
    sb.append(DELIMIT);
    sb.append(recipentName);
    sb.append(DELIMIT);
    sb.append(content.length());
    sb.append(DELIMIT);
    sb.append(content);
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DirectMessage)) {
      return false;
    }
    DirectMessage that = (DirectMessage) o;
    return Objects.equals(userName, that.userName) &&
        Objects.equals(recipentName, that.recipentName) &&
        Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, recipentName, content);
  }
}
