package assignment6.cli;

import static org.junit.Assert.*;

import org.junit.Test;

public class DisplayMessageTest {
  DisplayMessage dis=new DisplayMessage("test","test","test");
  DisplayMessage dis2=new DisplayMessage("test","test","test");
  @Test
  public void testToString() {
    assertEquals(" (test) test: test",dis.toString());
  }

  @Test
  public void testEquals() {
    assertEquals(dis,dis2);
  }

  @Test
  public void testHashCode() {
    assertEquals(dis.hashCode(),dis2.hashCode());
  }
}
