package service;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // Địa chỉ server
    private static final int SERVER_PORT = 12345; // Cổng server
    private static Socket socket;
    private static PrintWriter output;
    private static BufferedReader input;

    public static String login(String username, String password) {
        try {
            // Tạo kết nối với server nếu chưa có
            if (socket == null || socket.isClosed()) {
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }

            // Gửi thông tin đăng nhập đến server
            output.println(username);
            output.println(password);

            // Nhận phản hồi từ server
            return input.readLine(); // Trả về phản hồi từ server

        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            return "Error connecting to server"; // Trả về thông báo lỗi
        }
    }

    public static void disconnect() {
        try {
            // Gửi thông báo ngắt kết nối đến server
            if (output != null) {
                output.println("disconnect");
                output.flush();
            }

            // Đóng các luồng và socket
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null && !socket.isClosed()) socket.close();

            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.out.println("Error disconnecting: " + e.getMessage());
        }
    }
}
