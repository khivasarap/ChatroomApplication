package assignment6.message;

import assignment6.cli.DisplayMessage;
import assignment6.communication.MessageIdentifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The type Disconnect response.
 */
public class DisconnectResponse implements Message{
  private final static MessageIdentifier identifier=MessageIdentifier.DISCONNECT_RESPONSE;
  private String userName;
  private final static String SENDER="SERVER";
  private final static String CONTENT="Logged off";

  /**
   * Instantiates a new Disconnect response.
   *
   * @param userName the user name
   */
  public DisconnectResponse(String userName) {
    this.userName = userName;
  }


  /**
   * Display content string.
   *
   * @param rawInput the raw input
   * @return the string
   */
  public static String displayContent(String rawInput) {
    DisplayMessage display= new DisplayMessage(identifier.getCommand(),SENDER,CONTENT);
    return display.toString();
  }
  @Override
  public byte[] toBytes() {
    List<Byte>res=new ArrayList<>();
    char space=' ';
    res.add(Integer.valueOf(identifier.getValue()).byteValue());
    res.add((byte)space);
    res.add((byte)space);
    Byte[]resArr=res.toArray(new Byte[0]);
    return ArrayUtils.addAll(ArrayUtils.toPrimitive(resArr));
  }

  @Override
  public String toString() {
    StringBuilder sb=new StringBuilder();
    sb.append(identifier.getValue());
    sb.append(DELIMIT);
    sb.append(userName.length());
    sb.append(DELIMIT);
    sb.append(userName);
    sb.append(DELIMIT);
    sb.append(CONTENT);
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DisconnectResponse)) {
      return false;
    }
    DisconnectResponse that = (DisconnectResponse) o;
    return Objects.equals(userName, that.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }
}
