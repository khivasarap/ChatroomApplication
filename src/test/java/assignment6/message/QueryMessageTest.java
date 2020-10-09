package assignment6.message;

import static org.junit.Assert.*;

import assignment6.communication.ClientHandler;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;

public class QueryMessageTest {
  QueryMessage message = new QueryMessage("test");
  QueryMessage messageSame = new QueryMessage("test");
  @Test
  public void toBytes() {
    assertNotNull(message.toBytes());
  }

  @Test
  public void testToString() {
    System.out.println(message.toString());
    assertEquals("22 4 test", message.toString());
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
