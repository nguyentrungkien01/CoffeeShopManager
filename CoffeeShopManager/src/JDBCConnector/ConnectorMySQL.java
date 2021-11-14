package JDBCConnector;

import java.sql.*;

/**
 * <p>{@code ConnectorMySQL} uses to connect to database</p>
 */
public final class ConnectorMySQL {
    private final static String URL = "jdbc:mysql://localhost:3306/coffeeshop?autoReconnect=true&useSSL=false";
    private final static String USER = "root";
    private final static String PASSWORD = "0982482975";
    private Connection conn;

    public ConnectorMySQL() {
        this.conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
