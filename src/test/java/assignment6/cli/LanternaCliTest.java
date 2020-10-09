package assignment6.cli;

import static org.junit.Assert.*;

import assignment6.communication.MessageIdentifier;
import java.io.IOException;
import org.junit.Test;

public class LanternaCliTest {

  @Test
  public void getInstance() {
    try {
      assertNotNull(LanternaCli.getInstance());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void printMessage()  {
    try {
      LanternaCli.getInstance().printMessage(MessageIdentifier.SEND_INSULT,"user","test");
      LanternaCli.getInstance().printMessage(MessageIdentifier.CONNECT_MESSAGE,"user","test");
      LanternaCli.getInstance().printMessage(MessageIdentifier.CONNECT_RESPONSE,"user","test");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void printInsult()  {
    try {
      LanternaCli.getInstance().printInsult("test","test","test");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testEquals() {
    try {
      assertEquals( LanternaCli.getInstance(), LanternaCli.getInstance());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testHashCode()  {
    try {
      assertEquals( LanternaCli.getInstance().hashCode(), LanternaCli.getInstance().hashCode());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testToString() {
    try {
      assertEquals( LanternaCli.getInstance().toString(), LanternaCli.getInstance().toString());
    } catch (IOException e) {
    }
  }
}
