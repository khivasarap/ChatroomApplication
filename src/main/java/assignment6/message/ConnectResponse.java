package assignment6.message;

import assignment6.cli.DisplayMessage;
import assignment6.communication.MessageIdentifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The type Connect response.
 */
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
  public byte[] toBytes() {
    List<Byte> res = new ArrayList<>();
    char space = ' ';
    res.add(Integer.valueOf(identifier.getValue()).byteValue());
    res.add((byte) space);
    res.add((byte) space);
    Byte[] resArr = res.toArray(new Byte[0]);
    return ArrayUtils.addAll(ArrayUtils.toPrimitive(resArr));
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ConnectResponse)) {
      return false;
    }
    ConnectResponse that = (ConnectResponse) o;
    return isSuccess == that.isSuccess &&
        Objects.equals(msg, that.msg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isSuccess, msg);
  }
}
