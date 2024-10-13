package service;

import java.io.*;
import java.net.*;
import java.sql.*;
import repository.DatabaseConnection;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            // Đọc thông tin đăng nhập từ client
            username = input.readLine();
            String password = input.readLine();

            // Kiểm tra đăng nhập
            if (checkLogin(username, password)) {
                output.println("Login successful");
                updateUserStatus(username, "Online");

                // Lắng nghe thông điệp từ client
                String message;
                while ((message = input.readLine()) != null) {
                    if (message.equals("logout") || message.equals("disconnect")) {
                        updateUserStatus(username, "Offline");
                        System.out.println("User " + username + " logged out.");
                        break;
                    }
                }
            } else {
                output.println("Login failed");
            }
        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
        } finally {
            if (username != null) {
                updateUserStatus(username, "Offline");
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkLogin(String username, String password) {
        // Kết nối đến cơ sở dữ liệu và kiểm tra thông tin đăng nhập
        DatabaseConnection dbc = new DatabaseConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            dbc.openConnection();
            PreparedStatement ps = dbc.getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next(); // Nếu có kết quả trả về thì đăng nhập thành công
            rs.close();
            return exists;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.closeConnection();
        }
        return false;
    }

    private void updateUserStatus(String username, String status) {
        // Cập nhật trạng thái người dùng
        DatabaseConnection dbc = new DatabaseConnection();
        String query = "UPDATE users SET status = ? WHERE username = ?";

        try {
            dbc.openConnection();
            PreparedStatement ps = dbc.getConnection().prepareStatement(query);
            ps.setString(1, status);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.closeConnection();
        }
    }
}
