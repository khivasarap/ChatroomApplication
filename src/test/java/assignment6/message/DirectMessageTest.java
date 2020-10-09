package assignment6.message;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectMessageTest {
  DirectMessage message = new DirectMessage("test","test","test");
  DirectMessage messageSame = new DirectMessage("test","test","test");
  @Test
  public void toBytes() {
    assertNotNull(message.toBytes());
  }

  @Test
  public void testToString() {
    assertEquals("25 4 test 4 test 4 test", message.toString());
  }

  @Test
  public void displayContent() {
    assertEquals(" (@user) test->test: test",DirectMessage.displayContent( message.toString()));
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
