package com.online.receipt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CalculationApp extends Application {

    private TextField[] qtyFields = new TextField[12];
    private TextField[] priceFields = new TextField[12];
    private TextField[] valueFields = new TextField[12];
    private TextField totalField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculation Form");

        VBox layout = new VBox(10);
        for (int i = 0; i < 12; i++) {
            qtyFields[i] = new TextField();
            priceFields[i] = new TextField();
            valueFields[i] = new TextField();
            valueFields[i].setEditable(false);

            HBox row = new HBox(10, new Label("Qty:"), qtyFields[i], new Label("Price:"), priceFields[i], new Label("Value:"), valueFields[i]);
            layout.getChildren().add(row);

            final int index = i; // Final variable for lambda expression
            qtyFields[i].textProperty().addListener((observable, oldValue, newValue) -> calculateValue(index));
            priceFields[i].textProperty().addListener((observable, oldValue, newValue) -> calculateValue(index));
        }

        totalField = new TextField();
        totalField.setEditable(false);
        Button calculateButton = new Button("Calculate Total");
        calculateButton.setOnAction(e -> calculateTotal());

        layout.getChildren().addAll(new Label("Total:"), totalField, calculateButton);
        layout.setPadding(new javafx.geometry.Insets(20));
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateValue(int index) {
        try {
            int qty = Integer.parseInt(qtyFields[index].getText());
            int price = Integer.parseInt(priceFields[index].getText());
            int value = qty * price;
            valueFields[index].setText(String.valueOf(value));
        } catch (NumberFormatException e) {
            valueFields[index].setText("0");
        }
    }

    private void calculateTotal() {
        int total = 0;
        for (int i = 0; i < 12; i++) {
            try {
                total += Integer.parseInt(valueFields[i].getText());
            } catch (NumberFormatException e) {
                // Ignore invalid numbers
            }
        }
        totalField.setText(String.valueOf(total));
    }
}

