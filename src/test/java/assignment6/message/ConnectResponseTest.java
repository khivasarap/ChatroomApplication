package assignment6.message;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectResponseTest {
  ConnectResponse message = new ConnectResponse(true,"test");
  ConnectResponse messageSame = new ConnectResponse(true,"test");
  @Test
  public void toBytes() {
    assertNotNull(message.toBytes());
  }

  @Test
  public void testToString() {
    assertEquals("20 true 4 test", message.toString());
  }

  @Test
  public void displayContent() {
    assertEquals(ConnectResponse.displayContent(message.toString())," (ClientHandler) SERVER: test");
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
