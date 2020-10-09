package assignment6.message;

import assignment6.cli.DisplayMessage;
import assignment6.communication.ClientHandler;
import assignment6.communication.MessageIdentifier;
import assignment6.communication.Server;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The type Query response.
 */
public class QueryResponse implements Message{
  private final static MessageIdentifier identifier=MessageIdentifier.QUERY_USER_RESPONSE;
  private final static String SENDER="SERVER";
  private String userName;
  private final ConcurrentHashMap<String, ClientHandler> activeClients;

  /**
   * Instantiates a new Query response.
   *
   * @param userName      the user name
   * @param activeClients the active clients
   */
  public QueryResponse(String userName ,ConcurrentHashMap<String, ClientHandler>activeClients) {
    this.userName = userName;
    this.activeClients=activeClients;
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


  /**
   * Display content string.
   *
   * @param rawInput the raw input
   * @return the string
   */
  public static String displayContent(String rawInput) {
    String[] tokens = rawInput.split(DELIMIT);
    String content = "There is "+tokens[1]+" other users";
    if(!tokens[1].equals("0")) {
      content+=":";
      for (int i = 2; i < tokens.length; i += 2) {
        content += " " + tokens[i + 1];
      }
    }
    DisplayMessage display = new DisplayMessage(identifier.getCommand(), SENDER, content);
    return display.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb=new StringBuilder();
    sb.append(identifier.getValue());
    synchronized (activeClients){
      int totalUsers = activeClients.size() - 1;
      sb.append(DELIMIT);
      sb.append(totalUsers);
      if (totalUsers >= 1) {
        activeClients.forEach((k, v) -> {
          if (!k.equals(userName)) {
            sb.append(" "+k.length() + " " + k);
          }
        });
      }
      else
        sb.append(" 0");
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof QueryResponse)) {
      return false;
    }
    QueryResponse that = (QueryResponse) o;
    return Objects.equals(userName, that.userName) &&
        Objects.equals(activeClients, that.activeClients);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, activeClients);
  }
}
