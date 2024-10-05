import java.io.*;
import java.net.*;
import java.util.*;

public class BlockingServer {
    private static final int PORT = 8080;
    private static Set<Socket> clientSockets = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Server is running...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);
                System.out.println("Client konek: " + clientSocket.getInetAddress());
                
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Menerima dari client: " + message);
                System.out.print(message);
            }
        } catch (IOException e) {
            System.out.println("Client diskonek: " + clientSocket.getInetAddress());
        } finally {
            try {
                clientSocket.close();
                clientSockets.remove(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
