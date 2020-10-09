package assignment6.message;

import static org.junit.Assert.*;

import assignment6.communication.ClientHandler;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Before;
import org.junit.Test;

public class QueryResponseTest {
  ConcurrentHashMap map=new ConcurrentHashMap<String, ClientHandler>();
  QueryResponse message = new QueryResponse("test",map);
  QueryResponse messageSame = new QueryResponse("test",map);

  @Before
  public void setUp() throws Exception {
    map.put("test",new ClientHandler(null,1,null,null));
    map.put("test2",new ClientHandler(null,1,null,null));
  }

  @Test
  public void toBytes() {
    assertNotNull(message.toBytes());
  }

  @Test
  public void testToString() {
    System.out.println(message.toString());
    assertEquals("23 1 5 test2", message.toString());
  }

  @Test
  public void displayContent() {
    System.out.println(QueryResponse.displayContent( message.toString()));
    assertEquals(" (ClientHandler) SERVER: There is 1 other users: test2",QueryResponse.displayContent( message.toString()));
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
