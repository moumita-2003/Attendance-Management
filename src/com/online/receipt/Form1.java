package com.online.receipt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class Form1 extends Application {

    private TextField usernameField;
    private PasswordField passwordField;

    @Override
    public void start(Stage primaryStage) {
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> handleLogin());

        VBox vbox = new VBox(10, usernameField, passwordField, loginButton);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.show();
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            showAlert("Enter the username");
            return;
        }
        if (password.isEmpty()) {
            showAlert("Enter password");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/your_database"; // Replace with your DB URL
        String dbUser = "your_db_user"; // Replace with your DB username
        String dbPassword = "your_db_password"; // Replace with your DB password

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String query = "SELECT * FROM log_tab WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    showAlert("Login successful! Welcome to VIT-AP Cafe!!!");
                    primaryStage.hide(); // Hide the current window
                    new Form2().start(new Stage()); // Open Form2
                } else {
                    showAlert("Username or password is invalid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
