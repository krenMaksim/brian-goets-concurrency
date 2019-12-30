package brian.goets.chapter6.listing6_4;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        @SuppressWarnings("resource")
        ServerSocket socket = new ServerSocket(8081);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }

    private static void handleRequest(Socket connection) {
        try {
            String content = convert(connection.getInputStream());
            System.out.println(String.format("[%s] %s", Thread.currentThread().getName(), content));
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    private static String convert(InputStream inputStream) throws IOException {
        StringBuffer input = new StringBuffer();
        while (inputStream.available() > 0) {
            input.append((char) inputStream.read());
        }
        return input.toString();
    }
}