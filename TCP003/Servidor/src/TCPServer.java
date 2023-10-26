import java.net.*;
import java.io.*;
import java.util.HashMap;

public class TCPServer {
    public static void main (String args[]) {
        try{
            int serverPort = 7896;
            HashMap<Integer, Place> places = new HashMap<Integer, Place>();
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept(); // bloqueia à espera de uma conexão
                Connection c = new Connection(clientSocket, places); // processa o pedido
            }
        } catch(IOException e) {
            System.out.println("Listen :"+e.getMessage());
        }
    }
}
