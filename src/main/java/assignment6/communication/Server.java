package assignment6.communication;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Server.
 */
public class Server {

  /**
   * The Counter.
   */
  static Integer counter = 0;
  /**
   * The Active clients.
   */
  static ConcurrentHashMap<String, ClientHandler> activeClients = new ConcurrentHashMap<String, ClientHandler>();

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    try {
      ServerSocket server = new ServerSocket(5000);
      Socket socket;
      System.out.println("ClientHandler Started ....");
      System.out.println("I am listening on port: 5000");
      while (true) {
        counter++;
        socket = server.accept();  //server accept the client connection request
          ClientHandler clientHandlerThread = new ClientHandler(socket, counter,
              socket.getOutputStream(),
              socket.getInputStream()); //send  the request to a separate thread
          clientHandlerThread.start();
      }
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      Server.activeClients.forEach((k, v) -> {
        v.stop();
      });
    }
  }
}
