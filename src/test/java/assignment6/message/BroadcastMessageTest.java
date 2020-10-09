package assignment6.message;

import static org.junit.Assert.*;

import org.junit.Test;

public class BroadcastMessageTest {
  BroadcastMessage broadcastMessage=new BroadcastMessage("test","test");
  BroadcastMessage broadcastMessageSame=new BroadcastMessage("test","test");
  @Test
  public void testToString() {
    assertEquals("24 4 test 4 test",broadcastMessage.toString());

  }

  @Test
  public void displayContent() {
    assertEquals(broadcastMessage.displayContent("24 4 test 4 test")," (@all) test: test");
  }

  @Test
  public void testEquals() {
    assertEquals(broadcastMessage,broadcastMessageSame);
  }

  @Test
  public void testHashCode() {
    assertEquals(broadcastMessage.hashCode(),broadcastMessageSame.hashCode());
  }

  @Test
  public void toBytes() {
    assertNotNull(broadcastMessage.toBytes());
  }
}
