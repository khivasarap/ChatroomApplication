package assignment6.message;

import assignment6.cli.DisplayMessage;
import assignment6.communication.MessageIdentifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The type Connect message.
 */
public class ConnectMessage implements Message{
  private final static MessageIdentifier identifier=MessageIdentifier.CONNECT_MESSAGE;
  private String userName;

  /**
   * Instantiates a new Connect message.
   *
   * @param userName the user name
   */
  public ConnectMessage(String userName) {
    this.userName = userName;
  }

  @Override
  public byte[] toBytes() {
    List<Byte>res=new ArrayList<>();
    char space=' ';
    Integer length=userName.getBytes().length;
    res.add(Integer.valueOf(identifier.getValue()).byteValue());
    res.add((byte)space);
    res.add(length.byteValue());
    res.add((byte)space);
    Byte[]resArr=res.toArray(new Byte[0]);
    return ArrayUtils.addAll(ArrayUtils.toPrimitive(resArr),userName.getBytes());
  }



  @Override
  public String toString() {
    StringBuilder sb=new StringBuilder();
    sb.append(identifier.getValue());
    sb.append(DELIMIT);
    sb.append(userName.length());
    sb.append(DELIMIT);
    sb.append(userName);
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ConnectMessage)) {
      return false;
    }
    ConnectMessage that = (ConnectMessage) o;
    return Objects.equals(userName, that.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }
}
