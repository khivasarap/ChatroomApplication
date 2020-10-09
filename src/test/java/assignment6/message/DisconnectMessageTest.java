package assignment6.message;

import static org.junit.Assert.*;

import assignment6.cli.DisplayMessage;
import org.junit.Test;

public class DisconnectMessageTest {
  DisconnectMessage message = new DisconnectMessage("test");
  DisconnectMessage messageSame = new DisconnectMessage("test");
  @Test
  public void toBytes() {
    assertNotNull(message.toBytes());
  }

  @Test
  public void testToString() {
//    System.out.println(message.toString());
    assertEquals("21 4 test", message.toString());
  }

  @Test
  public void displayContent() {
//    System.out.println(DisconnectResponse.displayContent( message.toString()));
    assertEquals(" (ClientHandler) SERVER: Logged off",DisconnectResponse.displayContent( message.toString()));
  }

  @Test
  public void testEquals() {
    assertEquals(message, messageSame);
  }


  @Test
  public void testHashCode() {
    assertEquals(message.hashCode(), messageSame.hashCode());
  }

}
