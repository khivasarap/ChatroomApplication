package assignment6.communication;

import assignment6.message.ConnectResponse;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientHandlerTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test(timeout = 4000)
  public void processReadTest() throws IOException {
    ServerSocket server = new ServerSocket(5000);
    try {
      Client client = new Client("127.0.0.1", 5000, "pooja");
      Socket socket = server.accept();
      ClientHandler clientHandlerThread = new ClientHandler(socket, 1,
          socket.getOutputStream(),
          socket.getInputStream());
      String test = "21 5 pooja";
      int count = clientHandlerThread.getSocket().getInputStream().available();
      //Skip in case previous bytes
      if (clientHandlerThread.getSocket().getInputStream().available() > 0) {
        clientHandlerThread.getSocket().getInputStream().skip(count);
      }
      client.getOutputStream().write(test.getBytes());
      clientHandlerThread.processRead();
      Assert.assertNotNull(client.getInputStream());
      Assert.assertNotNull(clientHandlerThread.getSocket().getInputStream());
      server.close();
    } catch (BindException e) {
      server.close();
      System.exit(1);
    }
  }

  @Test(timeout = 500)
  public void processWriteTest() throws IOException {
    ServerSocket server=null;
    try {
       server = new ServerSocket(5004);
    }catch (Exception e){
      e.printStackTrace();
    }

    Client client = new Client("127.0.0.1", 5004, "pooja");
    Socket socket = server.accept();
    ClientHandler clientHandlerThread = new ClientHandler(socket, 1,
        socket.getOutputStream(),
        socket.getInputStream());
    ConnectResponse connectResponse = new ConnectResponse(true, "Testing");
    clientHandlerThread.processWrite(clientHandlerThread, connectResponse.toString());
    Assert.assertNotNull(clientHandlerThread.getSocket().getOutputStream());
    socket.close();
    server.close();
  }

  @Test(expected = ChatRoomFullException.class)
  public void testChatRoomFull() throws IOException, ChatRoomFullException {
    ServerSocket server = new ServerSocket(5002);
    Client client1 = new Client("127.0.0.1", 5002, "pooja");
    Client client2 = new Client("127.0.0.1", 5002, "tao");
    Client client3 = new Client("127.0.0.1", 5002, "foo");
    Client client4 = new Client("127.0.0.1", 5002, "koo");
    Client client5 = new Client("127.0.0.1", 5002, "do");
    Client client6 = new Client("127.0.0.1", 5002, "hoo");
    Client client7 = new Client("127.0.0.1", 5002, "sun");
    Client client8 = new Client("127.0.0.1", 5002, "cool");
    Client client9 = new Client("127.0.0.1", 5002, "tea");
    Client client10 = new Client("127.0.0.1", 5002, "fee");
    Client client11 = new Client("127.0.0.1", 5002, "bee");

    Socket socket = server.accept();

    ClientHandler clientHandlerThread = new ClientHandler(socket, 0,
        socket.getOutputStream(),
        socket.getInputStream());

    ClientHandler clientHandlerThread1 = new ClientHandler(socket, 1,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread2 = new ClientHandler(socket, 2,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread3 = new ClientHandler(socket, 3,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread4 = new ClientHandler(socket, 4,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread5 = new ClientHandler(socket, 5,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread6 = new ClientHandler(socket, 6,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread7 = new ClientHandler(socket, 7,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread8 = new ClientHandler(socket, 8,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread9 = new ClientHandler(socket, 9,
        socket.getOutputStream(),
        socket.getInputStream());
    ClientHandler clientHandlerThread10 = new ClientHandler(socket, 10,
        socket.getOutputStream(),
        socket.getInputStream());
    clientHandlerThread.processConnectMessage(new String[]{"19", "5", "pooja"});
    clientHandlerThread1.processConnectMessage(new String[]{"19", "3", "tao"});
    clientHandlerThread2.processConnectMessage(new String[]{"19", "3", "foo"});
    clientHandlerThread3.processConnectMessage(new String[]{"19", "3", "koo"});
    clientHandlerThread4.processConnectMessage(new String[]{"19", "2", "do"});
    clientHandlerThread5.processConnectMessage(new String[]{"19", "3", "hoo"});
    clientHandlerThread6.processConnectMessage(new String[]{"19", "3", "sun"});
    clientHandlerThread7.processConnectMessage(new String[]{"19", "4", "cool"});
    clientHandlerThread8.processConnectMessage(new String[]{"19", "3", "tea"});
    clientHandlerThread9.processConnectMessage(new String[]{"19", "3", "fee"});
    //Testing Equals and Hashcode
    Assert.assertEquals(clientHandlerThread, clientHandlerThread);
    Assert.assertNotEquals(clientHandlerThread, clientHandlerThread1);
    Assert.assertTrue(clientHandlerThread.isKeepRunning());
    Assert.assertNotEquals(clientHandlerThread, "clientHandlerThread1");
    Assert.assertEquals(clientHandlerThread.hashCode(), clientHandlerThread.hashCode());

    clientHandlerThread10.processConnectMessage(new String[]{"19", "3", "bee"});
    socket.close();
    server.close();
  }

}
