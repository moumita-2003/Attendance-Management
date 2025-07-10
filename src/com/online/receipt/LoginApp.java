package com.online.receipt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class LoginApp extends Application {

    private TextField usernameField;
    private PasswordField passwordField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        usernameField = new TextField();
        passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> login());

        VBox layout = new VBox(10, new Label("Username:"), usernameField, new Label("Password:"), passwordField, loginButton);
        layout.setPadding(new javafx.geometry.Insets(20));
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            showAlert("Enter the username");
        } else if (password.isEmpty()) {
            showAlert("Enter password");
        } else {
            try {
                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=charan;integratedSecurity=true;");
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM log_tab WHERE username = ? AND password = ?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    showAlert("Login successful! Welcome to VIT-AP Cafe!!!");
                    // Navigate to next screen (menu)
                    // MenuApp menuApp = new MenuApp();
                    // menuApp.start(new Stage());
                    //primaryStage.close(); // Close login window
                } else {
                    showAlert("Username or password is invalid");
                }

                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database error: " + e.getMessage());
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
