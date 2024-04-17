package ru.peef.chilove.network;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.peef.chilove.ChiloveMain;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    static boolean running;
    public static List<Socket> listeners = new ArrayList<>();

    public static void init() {
        new Thread(() -> {
            int port = (ChiloveMain.getInstance().getServer().getPort()+1);
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                Bukkit.getLogger().info("Server is listening on port " + port);
                running = true;

                while (running) {
                    Socket socket = serverSocket.accept();
                    listeners.add(socket);
                    sendMessage(socket, "Hello message!");

                    Bukkit.getLogger().info("Added new listener");
                }
            } catch (IOException exception) {
                Bukkit.getLogger().info("Socket error: " + exception.getMessage());
                running = false;
            }
        }).start();
    }

    public static boolean sendMessage(Player player, String message) {
        try {
            for (Socket socket: listeners) {
                if (socket.getInetAddress().equals(player.getAddress().getAddress())) {
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);
                    writer.println(message);

                    return true;
                }
            }

        } catch (IOException ignored) { }
        return false;
    }

    public static void sendMessage(Socket socket, String message) {
        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println(message);

        } catch (IOException ignored) { }
    }

    public static void removeListener(Socket socket) { listeners.remove(socket); }
    public static void disableSocketServer() { running = false; }
    public static boolean isRunning() { return running; }
}
