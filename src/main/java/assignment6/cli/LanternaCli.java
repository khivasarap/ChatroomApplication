package assignment6.cli;
import assignment6.communication.MessageIdentifier;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * The type Lanterna cli.
 */
public class LanternaCli {
  private Terminal terminal;
  private static LanternaCli instance=null;
  final OutputStream outputStream = System.out;
  final InputStream inputStream = System.in;

  /**
   * Gets instance.
   *
   * @return the instance
   * @throws IOException the io exception
   */
  public static LanternaCli getInstance() throws IOException {
    if(instance==null)instance=new LanternaCli();
    return instance;
  }

  private LanternaCli() throws IOException {

    terminal=new UnixTerminal(inputStream, outputStream, StandardCharsets.UTF_8);
  }

  /**
   * Print message.
   *
   * @param messageIdentifier the message identifier
   * @param sender            the sender
   * @param content           the content
   * @throws IOException the io exception
   */
  public void printMessage(MessageIdentifier messageIdentifier, String sender,String content)
      throws IOException {
    switch (messageIdentifier){
      case CONNECT_MESSAGE: {
        printALine(messageIdentifier.getCommand(),sender,content,ANSI.YELLOW);
        break;
      }
      case CONNECT_RESPONSE:
      case QUERY_USER_RESPONSE: {
        printALine(messageIdentifier.getCommand(),sender,content,ANSI.GREEN);
        break;
      }
      case DISCONNECT_MESSAGE:{
        printALine(messageIdentifier.getCommand(),sender,content,ANSI.WHITE);
        break;
      }
      case QUERY_CONNECTED_USERS:{
        printALine(messageIdentifier.getCommand(),sender,content,CliColor.ORANGE_RED);
        break;
      }
      case BROADCAST_MESSAGE:{
        printALine(messageIdentifier.getCommand(),sender,content,CliColor.GREY);
        break;
      }
      case DIRECT_MESSAGE:{
        printALine(messageIdentifier.getCommand(),sender,content,CliColor.PINK);
        break;
      }
      case FAILED_MESSAGE:{
        printALine(messageIdentifier.getCommand(),sender,content,ANSI.RED);
        break;
      }
      case SEND_INSULT:{
        printInsult(messageIdentifier.getCommand(),sender,content);
        break;
      }


    }
    terminal.flush();
  }
  private void print(String string) throws IOException {
    for(char c:string.toCharArray()){
      terminal.putCharacter(c);
    }
  }

  /**
   * Print insult.
   *
   * @param type    the type
   * @param sender  the sender
   * @param content the content
   * @throws IOException the io exception
   */
  void printInsult(String type, String sender, String content) throws IOException {
    terminal.setForegroundColor(ANSI.RED);
    print(" (");
    print(type);
    print(") ");
    print(sender);
    print(": ");
    print(content);
    print("\n");
  }
  private void printALine(String type, String sender,String content, TextColor Color) throws IOException {
    terminal.setForegroundColor(Color);
    print(" (");
    print(type);
    print(") ");
    terminal.setForegroundColor(ANSI.CYAN);
    print(sender);
    print(": ");
    terminal.setForegroundColor(ANSI.WHITE);
    print(content);
    print("\n");
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws IOException the io exception
   */
  public static void main(String[] args) throws IOException {
    LanternaCli lanternaCli = LanternaCli.getInstance();
    lanternaCli.printMessage(MessageIdentifier.CONNECT_MESSAGE,"Tao","try to connect...");
    lanternaCli.printMessage(MessageIdentifier.CONNECT_RESPONSE,"Server","Connection Established");
    lanternaCli.printMessage(MessageIdentifier.BROADCAST_MESSAGE,"Tao","Anyone Here?");
    lanternaCli.printMessage(MessageIdentifier.QUERY_CONNECTED_USERS,"Tao","Querying existing users");
    lanternaCli.printMessage(MessageIdentifier.QUERY_USER_RESPONSE,"Server","A.TA.SHI,Oreki Houtarou are online");
    lanternaCli.printMessage(MessageIdentifier.DIRECT_MESSAGE,"Oreki","If I don't have to do it, I won't. If I have to do it, I'll make it quick.");
    lanternaCli.printMessage(MessageIdentifier.SEND_INSULT,"Tao","ki ni na ri ma su");
    lanternaCli.printMessage(MessageIdentifier.FAILED_MESSAGE,"Server","Connection not stable...");
    lanternaCli.printMessage(MessageIdentifier.DISCONNECT_MESSAGE,"Tao","Disconnected");

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LanternaCli)) {
      return false;
    }
    LanternaCli that = (LanternaCli) o;
    return Objects.equals(terminal, that.terminal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(terminal);
  }

  @Override
  public String toString() {
    return "LanternaCli{" +
        "terminal=" + terminal +
        '}';
  }

}
