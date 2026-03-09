import java.io.*;
import java.net.*;
import java.util.*;

public class WhiteboardServer {
    private static final int PORT = 5000;
    private static final List<ObjectOutputStream> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("🎨 Whiteboard Server running on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                synchronized (clients) {
                    clients.add(out);
                }
                new Thread(() -> handleClient(socket, out)).start();
                System.out.println("✅ Client connected: " + socket.getInetAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket, ObjectOutputStream out) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            while (true) {
                Message msg = (Message) in.readObject();
                broadcast(msg);
            }
        } catch (Exception e) {
            System.out.println("❌ Client disconnected: " + socket.getInetAddress());
        } finally {
            synchronized (clients) {
                clients.remove(out);
            }
        }
    }

    private static void broadcast(Message msg) {
        synchronized (clients) {
            for (ObjectOutputStream out : clients) {
                try {
                    out.writeObject(msg);
                    out.flush();
                } catch (IOException ignored) {}
            }
        }
    }
}
