import java.net.*;
import java.io.*;

public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection (Socket aClientSocket) {
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

            //String data = in.readUTF(); //lê os dados do cliente
            //out.writeUTF(data); //envia a mensagem (resposta) ao cliente

            ObjectInputStream ois = new ObjectInputStream(in);
            Person ret = (Person)ois.readObject();

            out.writeUTF(ret.getName());

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