import java.net.*;
import java.io.*;
import java.util.HashMap;

public class UDPServer {
    public static HashMap<Integer, String> messages = new HashMap();
    public static int port;
    public static InetAddress address;

    public static void main(String[] args) {
        DatagramSocket aSocket = null;

        try {
            aSocket = new DatagramSocket(6789);
            byte[] buffer = new byte[120];
            int lastMsg = 0;

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                port = request.getPort();
                address = request.getAddress();

                String msg = new String(request.getData());
                String[] parts = msg.split(",");

                int msgN = Integer.parseInt(parts[0]);

                lastMsg = UDPServer.processDeliveredMessages(lastMsg, msgN, parts[1]);

                if (!messages.isEmpty()) {
                    int x = messages.keySet().stream().min(Integer::compare).get();

                    String response = "waitingfor," + (x - 1);
                    sendReply(response, request, aSocket);
                } else {
                    sendReply(parts[1], request, aSocket);
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }

    /**
     * Processes delivered messages
     * @return the last message processed in order
     */
    public static int processDeliveredMessages(int nLastMessageInOrder, int nCurrentMessage, String currentMessage) {
        if (nCurrentMessage != nLastMessageInOrder + 1) {
            messages.put(nCurrentMessage, currentMessage);
        } else {
            nLastMessageInOrder = nCurrentMessage;

            while (messages.containsKey(nLastMessageInOrder + 1)) {
                nLastMessageInOrder++;
                String response = messages.get(nLastMessageInOrder);

                messages.remove(nLastMessageInOrder);
            }
        }

        return nLastMessageInOrder;
    }

    private static void sendReply(String response, DatagramPacket request, DatagramSocket aSocket) throws IOException {
        DatagramPacket reply = new DatagramPacket(
                response.getBytes(),
                response.length(),
                request.getAddress(),
                request.getPort()
        );

        aSocket.send(reply);
    }
}
