package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
  private static final String URL = "jdbc:mysql://localhost:3306/VIT_AP"; // Replace with your DB name
  private static final String USER = "root"; // Replace with your MySQL username
  private static final String PASSWORD = "root123"; // Replace with your MySQL password

  public static Connection getConnection() {
    Connection conn = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("Database connected successfully!");
    } catch (ClassNotFoundException e) {
      System.out.println("MySQL JDBC Driver not found.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("Connection failed!");
      e.printStackTrace();
    }
    return conn;
  }
}
