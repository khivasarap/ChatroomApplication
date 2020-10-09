package assignment6.communication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {
  Message message;
  @Before
  public void setUp() throws Exception {
    message = new Message(MessageIdentifier.CONNECT_RESPONSE,"pooja".getBytes(),"pooja".length());
  }

  @Test
  public void testNotEquals() {
   Message message1 = new Message(MessageIdentifier.DISCONNECT_RESPONSE,"tao".getBytes(),"tao".length());
    Assert.assertNotEquals(message1,message);
  }


  @Test
  public void testEqualsNull() {
    Message message1 = new Message(MessageIdentifier.DISCONNECT_RESPONSE,"tao".getBytes(),"tao".length());
    Assert.assertNotEquals(message1,null);
  }

  @Test
  public void testEqualsObject() {
    Message message1 = new Message(MessageIdentifier.DISCONNECT_RESPONSE,"tao".getBytes(),"tao".length());
    Message message2 = new Message(MessageIdentifier.DISCONNECT_RESPONSE,"tao".getBytes(),"tao".length());
    Assert.assertEquals(message1,message2);
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(message,message);
  }

  @Test
  public void testEqualsType() {
    Assert.assertNotEquals(message,"message");
  }
  @Test
  public void testHashCode() {
    Message message1 = new Message(MessageIdentifier.DISCONNECT_RESPONSE,"tao".getBytes(),"tao".length());
    Assert.assertNotEquals(message1.hashCode(),message.hashCode());
  }
}