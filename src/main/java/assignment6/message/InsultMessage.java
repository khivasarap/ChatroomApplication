package assignment6.message;

import assignment6.communication.MessageIdentifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The type Insult message.
 */
public class InsultMessage implements Message {

  private final static MessageIdentifier identifier = MessageIdentifier.SEND_INSULT;
  private String userName;
  private String recipentName;

  /**
   * Instantiates a new Insult message.
   *
   * @param userName     the user name
   * @param recipentName the recipent name
   */
  public InsultMessage(String userName, String recipentName) {
    this.userName = userName;
    this.recipentName = recipentName;
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
    sb.append(recipentName.length());
    sb.append(DELIMIT);
    sb.append(recipentName);
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InsultMessage)) {
      return false;
    }
    InsultMessage that = (InsultMessage) o;
    return Objects.equals(userName, that.userName) &&
        Objects.equals(recipentName, that.recipentName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, recipentName);
  }
}
