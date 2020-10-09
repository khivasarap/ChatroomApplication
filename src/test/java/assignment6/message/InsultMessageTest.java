package assignment6.message;

import static org.junit.Assert.*;

import org.junit.Test;

public class InsultMessageTest {
  InsultMessage message = new InsultMessage("test","test");
  InsultMessage messageSame = new InsultMessage("test","test");
  @Test
  public void toBytes() {
    assertNotNull(message.toBytes());
  }

  @Test
  public void testToString() {
    assertEquals("27 4 test 4 test", message.toString());
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
