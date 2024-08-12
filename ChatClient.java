import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the chat server");

            // Thread for receiving messages from the server
            new Thread(() -> {
                String messageFromServer;
                try {
                    while ((messageFromServer = serverReader.readLine()) != null) {
                        System.out.println(messageFromServer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread for sending messages to the server
            String messageToServer;
            while ((messageToServer = inputReader.readLine()) != null) {
                serverWriter.println(messageToServer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}