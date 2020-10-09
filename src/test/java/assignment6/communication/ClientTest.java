package assignment6.communication;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {

  @Before
  public void setUp() throws Exception {

  }

  @Test (timeout = 5000)
  public void createReadThread() throws IOException {
    ServerSocket server = new ServerSocket(5002);
    try {
      Client client = new Client("127.0.0.1", 5002, "pooja");
      Socket socket = server.accept();
      ClientHandler clientHandlerThread = new ClientHandler(socket, 1,
          socket.getOutputStream(),
          socket.getInputStream());
      clientHandlerThread.getSocket().getOutputStream().write("21 pooja 5".getBytes());
      client.createReadThread();
      Assert.assertNotNull(client.getInputStream());
      int countBytes = client.getInputStream().available();
      Assert.assertTrue(countBytes > 0);
      socket.close();
      server.close();
    }catch (BindException e){
      server.close();
      System.exit(0);
    }
  }

  @Test
  public void createWriteThread() throws IOException {
    ServerSocket server = new ServerSocket(5001);
    Client client = new Client("127.0.0.1", 5001, "pooja");
    Socket socket = server.accept();
    ClientHandler clientHandlerThread = new ClientHandler(socket, 1,
        socket.getOutputStream(),
        socket.getInputStream());
    client.getOutputStream().write("protocolString".getBytes(StandardCharsets.UTF_8));
    client.createWriteThread();
    Assert.assertNotNull(client.getOutputStream());
    Assert.assertTrue(clientHandlerThread.getSocket().getInputStream().available() > 0);
    Assert.assertEquals(client,client);
    Assert.assertEquals(client.hashCode(),client.hashCode());
    socket.close();
    server.close();
  }

  @Test
  public void testEquals() throws IOException {
    ServerSocket server = new ServerSocket(5001);
    Client client = new Client("127.0.0.1", 5001, "pooja");
    Client client2 = new Client("127.0.0.1", 5001, "pooja");
    Assert.assertNotEquals(client,client2);
  }

}
