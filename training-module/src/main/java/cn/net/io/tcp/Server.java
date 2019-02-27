package cn.net.io.tcp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private Logger logger = LoggerFactory.getLogger(Server.class);
    private final static int DefaultPort = 24601;

    @Test
    public void start() {
        CreateServer();
    }

    private void CreateServer() {
        CreateServer(DefaultPort);
    }

    /**
     * Create a socket server at passed port.
     *
     * @param port Port onto which server is socketed.
     */
    private void CreateServer(int port) {
        try {
            logger.info(String.format("CREATING SERVER: localhost:%d", port));
            // Established server socket at port.
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                // Listen for connection.
                Socket socket = serverSocket.accept();
                // Once client has connected, use socket stream to send a prompt message to client.
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                // Prompt for client.
                printWriter.println("Enter a message for the server.");

                // Get input stream produced by client (to read sent message).
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String output = bufferedReader.readLine();

                // Output sent message from client.
                printWriter.println(output);

                // Close writer and socket.
                printWriter.close();
                socket.close();

                // Output message from client.
                logger.info(String.format("[FROM Client] %s", output));

                // Loop back, awaiting a new client connection.
            }
        } catch (SocketTimeoutException ex) {
            // Output expected SocketTimeoutExceptions.
            logger.error(ex.getMessage());
        } catch (IOException e) {
            // Output unexpected IOExceptions.
            logger.error(e.getMessage());
        }
    }
}
