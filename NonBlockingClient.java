import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class NonBlockingClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8081;

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            socketChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(256);
            System.out.print("Konek ke server. ");
            System.out.print("Ketik pesan: ");

            while (true) {
                String userInputMessage = System.console().readLine();
                if (userInputMessage == null) break;

                buffer.clear();
                buffer.put(userInputMessage.getBytes());
                buffer.flip();
                socketChannel.write(buffer);
                
                buffer.clear();
                socketChannel.read(buffer);
                System.out.println("Balasan server: " + new String(buffer.array()).trim());
            }
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
