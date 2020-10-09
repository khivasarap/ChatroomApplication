package assignment6.message;

import static org.junit.Assert.*;

import org.junit.Test;

public class DisconnectResponseTest {
  DisconnectResponse message = new DisconnectResponse("test");
  DisconnectResponse messageSame = new DisconnectResponse("test");
  @Test
  public void toBytes() {
    assertNotNull(message.toBytes());
  }

  @Test
  public void testToString() {
//    System.out.println(message.toString());
    assertEquals("28 4 test Logged off", message.toString());
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
