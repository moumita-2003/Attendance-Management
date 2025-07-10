package com.online.receipt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Form2 extends Application {

    private TextField[] qtyFields = new TextField[12];
    private TextField[] priceFields = new TextField[12];
    private TextField[] totalFields = new TextField[12];
    private TextField grandTotalField;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        for (int i = 0; i < 12; i++) {
            qtyFields[i] = new TextField();
            qtyFields[i].setPromptText("Qty " + (i + 1));
            qtyFields[i].textProperty().addListener((observable, oldValue, newValue) -> calculateTotal(i));
            priceFields[i] = new TextField();
            priceFields[i].setPromptText("Price " + (i + 1));
            priceFields[i].textProperty().addListener((observable, oldValue, newValue) -> calculateTotal(i));
            totalFields[i] = new TextField();
            totalFields[i].setEditable(false);

            grid.add(qtyFields[i], 0, i);
            grid.add(priceFields[i], 1, i);
            grid.add(totalFields[i], 2, i);
        }

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> saveData());
        grandTotalField = new TextField();
        grandTotalField.setEditable(false);
        grid.add(grandTotalField, 0, 12);
        grid.add(saveButton, 0, 13);

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculation Form");
        primaryStage.show();
    }

    private void calculateTotal(int index) {
        try {
            int qty = Integer.parseInt(qtyFields[index].getText());
            int price = Integer.parseInt(priceFields[index].getText());
            totalFields[index].setText(String.valueOf(qty * price));
        } catch (NumberFormatException e) {
            totalFields[index].setText("0");
        }
        updateGrandTotal();
    }

    private void updateGrandTotal() {
        int grandTotal = 0;
        for (TextField totalField : totalFields) {
            try {
                grandTotal += Integer.parseInt(totalField.getText());
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        grandTotalField.setText(String.valueOf(grandTotal));
    }

    private void saveData() {
        String url = "jdbc:mysql://localhost:3306/your_database"; // Replace with your DB URL
        String dbUser = "your_db_user"; // Replace with your DB username
        String dbPassword = "your_db_password"; // Replace with your DB password

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String query = "INSERT INTO UserData (BILL_NO, RECEIVER, DATE, `GRAND TOTAL`) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, "SomeBillNo"); // Replace with your bill number input
                stmt.setString(2, "SomeReceiver"); // Replace with your receiver input
                stmt.setString(3, "SomeDate"); // Replace with your date input
                stmt.setString(4, grandTotalField.getText());
                stmt.executeUpdate();
                showAlert("Data saved successfully!");
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
