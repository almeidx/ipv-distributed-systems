import java.net.*;
import java.io.*;
import java.util.HashMap;

public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    private HashMap<Integer, Place> places;

    public Connection (Socket aClientSocket, HashMap<Integer, Place> places) {
        this.places = places;

        try {
            // inicializa variáveis
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out =new DataOutputStream( clientSocket.getOutputStream());

            this.start(); //executa o método run numa thread separada

        } catch(IOException e) {
            System.out.println("Connection:"+e.getMessage());
        }
    }


    public void run(){
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            Request ret = (Request)ois.readObject();

            switch (ret.getType()) {
                case "new": {
                    Place place = ret.getPlace();
                    int objectID = System.identityHashCode(place);
                    places.put(objectID, place);
                    out.writeUTF(Integer.toString(objectID));
                    break;
                }
                case "invoke": {
                    int objectID = ret.getObjectID();
                    Place place = places.get(objectID);
                    if (place == null) {
                        out.writeUTF("invalid objectid");
                    } else {
                        String method = ret.getMethod();
                        switch (method) {
                            case "getPostalCode":
                                out.writeUTF(place.getPostalCode());
                                break;
                            case "getLocality":
                                out.writeUTF(place.getLocality());
                                break;
                            default:
                                out.writeUTF("invalid method");
                                break;
                        }
                    }
                    break;
                }
                default:
                    out.writeUTF("invalid type");
                    break;
            }
        } catch(EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally{
            try {
                clientSocket.close();
            }catch (IOException e){
                /*close failed*/
            }
        }
    }
}