package brian.goets.chapter6.listing6_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        @SuppressWarnings("resource")
        ServerSocket socket = new ServerSocket(8081);
        while (true) {
            try (Socket connection = socket.accept()) {
                handleRequest(connection);
            }
        }
    }

    private static void handleRequest(Socket connection) {
        try {
            String content = convert(connection.getInputStream());
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convert(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.readLine();
        }
    }
}