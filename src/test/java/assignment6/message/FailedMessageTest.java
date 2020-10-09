package assignment6.message;

import static org.junit.Assert.*;

import org.junit.Test;

public class FailedMessageTest {
  FailedMessage message = new FailedMessage("test","test");
  FailedMessage messageSame = new FailedMessage("test","test");
  @Test
  public void toBytes() {
    assertNotNull(message.toBytes());
  }

  @Test
  public void testToString() {
    assertEquals("26 4 test 4 test", message.toString());
  }

  @Test
  public void displayContent() {
    assertEquals(" (ClientHandler) test: test",FailedMessage.displayContent( message.toString()));
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
