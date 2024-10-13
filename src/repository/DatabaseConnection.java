package repository;

import java.sql.*;

public class DatabaseConnection {
    Connection con = null;
    Statement st = null;
    ResultSet result = null;

    public DatabaseConnection() {
    }

    public void openConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlynguoichoi", "root", "");
            st = con.createStatement();
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found.", e);
        }
    }

    public void closeConnection() {
        try {
            if (result != null) {
                result.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
    public Connection getConnection() {
        return con; // Thêm phương thức này
    }
    
    public ResultSet executeQuery(String query) throws SQLException {
        if (con == null) {
            openConnection();
        }
        result = st.executeQuery(query);
        return result;
    }

    public int executeUpdate(String query) throws SQLException {
        if (con == null) {
            openConnection();
        }
        return st.executeUpdate(query);
    }
}
