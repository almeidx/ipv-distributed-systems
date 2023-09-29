import java.net.*;
        import java.io.*;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;

        try {
            aSocket = new DatagramSocket(6789);
            byte[] buffer = new byte[1000];
            int lastMsg = 0;

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                String msg = new String(request.getData());

                String[] parts = msg.split(",");

                int msgN = Integer.parseInt(parts[0]);
                String response;

                if (msgN != lastMsg + 1) {
                    response = "waitingfor," + (lastMsg + 1);
                } else {
                    response = parts[1];
                    lastMsg = msgN;
                }

                DatagramPacket reply = new DatagramPacket(
                        response.getBytes(),
                        response.length(),
                        request.getAddress(),
                        request.getPort()
                );

                aSocket.send(reply);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
}
