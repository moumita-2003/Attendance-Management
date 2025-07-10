package VIT_AP;

import java.sql.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;

public class Login extends Application {
	private TextField captchaField;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        
        // Create the login scene
        GridPane loginPane = createLoginPane();
        Scene loginScene = new Scene(loginPane, 500, 700);
        
        // Set the login scene on the stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("VIT-AP University");
        primaryStage.show();
        
    }

    private GridPane createLoginPane() {
        // Create the login pane
    	GridPane Pane = new GridPane();
    	//Pane.setPadding(new Insets(20));
        Pane.setVgap(10);
        Pane.setHgap(10);
    	GridPane gridPane1 = new GridPane();
    	gridPane1.setAlignment(Pos.CENTER);
        gridPane1.setPadding(new Insets(20));
        gridPane1.setVgap(10);
        //gridPane.setHgap(0);
        gridPane1.setStyle("-fx-background-color: white;");
        gridPane1.setPrefSize(400, 50);
        GridPane gridPane2 = new GridPane();
        gridPane2.setAlignment(Pos.CENTER);
        gridPane2.setPadding(new Insets(20));
        gridPane2.setVgap(10);
        //gridPane.setHgap(0);
        gridPane2.setStyle("-fx-background-color: white;");
        gridPane2.setPrefSize(400, 50);
        GridPane gridPane3 = new GridPane();
        gridPane3.setAlignment(Pos.CENTER);
        gridPane3.setPadding(new Insets(20));
        gridPane3.setVgap(10);
        //gridPane.setHgap(0);
        gridPane3.setStyle("-fx-background-color: white;");
        gridPane3.setPrefSize(400, 50);
        GridPane topLayout = new GridPane();
        ImageView imageView = new ImageView();
        String imageUrl = "https://vtop2.vitap.ac.in/vtop/assets/img/new%20VIT_AP%20logo.png";

        // Load the image from the website
        Image image = new Image(imageUrl);
        imageView.setImage(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(70);
        StackPane stack = new StackPane();
        stack.getChildren().add(imageView);
        topLayout.setPadding(new Insets(10));
        //topLayout.setVgap(10);
        topLayout.setHgap(130);
        topLayout.setStyle("-fx-background-color: dodgerblue;");
        topLayout.setPrefSize(500, 50);
        Label titleLabel = new Label("VIT-AP");
        Separator separator = new Separator();
        separator.getStyleClass().add("white-line");
        Label subtitleLabel = new Label(" UNIVERSITY");
        Label sideLabel = new Label("V-Top");
        
        
        titleLabel.setTextFill(Color.WHITE);
        subtitleLabel.setTextFill(Color.WHITE);
        sideLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(new Font("Times New Roman", 40));
        sideLabel.setFont(new Font("Times New Roman", 30));
        subtitleLabel.setFont(new Font("Times New Roman", 20));
        //topLayout.getChildren().addAll(titleLabel, subtitleLabel);
        topLayout.add(stack, 0, 0);
        //topLayout.add(titleLabel, 0, 1);
        //topLayout.add(separator, 0, 2);
        topLayout.add(sideLabel, 1, 0);
        Pane.getChildren().addAll(topLayout,gridPane1,gridPane2,gridPane3);
        return Pane;
    }
}