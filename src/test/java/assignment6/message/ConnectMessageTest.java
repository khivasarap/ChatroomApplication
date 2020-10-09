package assignment6.message;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectMessageTest {
  ConnectMessage connectMessage=new ConnectMessage("test");
  ConnectMessage connectMessageSame=new ConnectMessage("test");
  @Test
  public void toBytes() {
    assertNotNull(connectMessage.toBytes());
  }

  @Test
  public void testToString() {
    assertEquals("19 4 test",connectMessage.toString());
  }

  @Test
  public void testEquals() {
    assertEquals(connectMessage,connectMessageSame);
  }

  @Test
  public void testHashCode() {
    assertEquals(connectMessage.hashCode(),connectMessageSame.hashCode());
  }
}
