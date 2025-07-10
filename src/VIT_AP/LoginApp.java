package VIT_AP;
import java.sql.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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

public class LoginApp extends Application {
	
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
    	
        GridPane Layout1 = new GridPane();
        
        Pane Layout2=new Pane();
        Layout2.setPrefSize(200, 50);
        
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: white;");
        gridPane.setPrefSize(400, 50);
        
        ImageView imageView = new ImageView();
        String imageUrl ="C:\\Users\\moumita\\Desktop\\VIT\\logo.png";
        Image image = new Image(imageUrl);
        imageView.setImage(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(70);
        StackPane stack = new StackPane();
        stack.getChildren().add(imageView);
        
        ImageView imageView2 = new ImageView();
        String imageUrl2 = "C:\\Users\\moumita\\Desktop\\VIT\\icon1.jpg";
        Image image2 = new Image(imageUrl2);
        imageView2.setImage(image2);
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        StackPane stack2 = new StackPane();
        stack2.getChildren().add(imageView2);
        
        ImageView imageView3 = new ImageView();
        String imageUrl3 = "C:\\Users\\moumita\\Desktop\\VIT\\icon2.jpg";
        Image image3 = new Image(imageUrl3);
        imageView3.setImage(image3);
        imageView3.setFitWidth(30);
        imageView3.setFitHeight(30);
        StackPane stack3 = new StackPane();
        stack3.getChildren().add(imageView3);
        
        ImageView imageView4 = new ImageView();
        String imageUrl4 = "C:\\Users\\moumita\\Desktop\\VIT\\Captcha1.jpg";
        Image image4 = new Image(imageUrl4);
        imageView4.setImage(image4);
        imageView4.setFitWidth(200);
        imageView4.setFitHeight(50);
        StackPane stack4 = new StackPane();
        stack4.getChildren().add(imageView4);
        
        GridPane topLayout = new GridPane();
        topLayout.setPadding(new Insets(10));
        topLayout.setHgap(130);
        topLayout.setStyle("-fx-background-color: dodgerblue;");
        topLayout.setPrefSize(500, 50);
        
        Label sideLabel = new Label("V-Top");
        sideLabel.setTextFill(Color.WHITE);
        sideLabel.setFont(new Font("Times New Roman", 30));
        topLayout.add(stack, 0, 0);
        topLayout.add(sideLabel, 1, 0);
        
        // Add controls to the login pane
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setPrefWidth(300);
        choiceBox.setItems(FXCollections.observableArrayList(
                "Admin",
        		"Faculty",
                "Student",
                "Staff"
                
        ));
        VBox root = new VBox(choiceBox);
        
        Label Label = new Label("V-Top Login");
        Label.setFont(new Font("Arial", 20));
        
        TextField idField = new TextField();
        idField.setPromptText("UserID");
        idField.setPrefWidth(300);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(300);
        passwordField.setPromptText("Password");
        
        TextField captchaField=new TextField();
        captchaField.setPromptText("Captcha(CAPS & NUMBERS ONLY");
        
        VBox root9=new VBox();
        root9.getChildren().addAll(stack4,captchaField);
        root9.setPadding(new Insets(30));
        
        Button loginButton = new Button("Sign in");
        loginButton.setStyle("-fx-background-color: dodgerblue;");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setOnAction(event -> {
            String selectedOption = choiceBox.getValue();
            performAction(selectedOption,idField.getText(), passwordField.getText(),captchaField.getText());
        });
        
        Label textLabel = new Label("Forgot Password");
        textLabel.setTextFill(Color.DODGERBLUE);
        textLabel.setStyle("-fx-font-size: 13; ");
        textLabel.setOnMouseClicked(event -> {
        	try {
            	Login_Details CP= new Login_Details(choiceBox.getValue(),primaryStage,idField.getText());
				CP.Change_Password();
            	}
				catch(Exception ex)
				{
					showAlert(Alert.AlertType.ERROR, "Form Error!",
		                    "Invalid Input");
				}
        });
        
        
        gridPane.add(Label, 0, 0);
        gridPane.add(root, 0, 2);
        gridPane.add(idField, 0, 3);
        gridPane.add(stack2, 1, 3);
        gridPane.add(passwordField, 0, 4);
        gridPane.add(stack3, 1, 4);
        gridPane.add(root9, 0, 5);
        gridPane.add(textLabel, 0, 7);
        gridPane.add(loginButton, 1, 7);
        
        Separator separator3 = new Separator();
        separator3.setStyle("-fx-pref-width: 400px; -fx-background-color: deepskyblue;");
        VBox root3=new VBox();
        root3.getChildren().addAll(separator3,gridPane);
        flowPane.getChildren().add(root3);
        
        Layout1.add(topLayout, 0, 0);
        Layout1.add(Layout2, 0, 1);
        Layout1.add(flowPane,0,3);
        return Layout1;
    }

    private void performAction(String user,String id, String password,String captcha) {
        // Simulate login authentication
    	try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
		String q1="select * from Login_Details where ID=? and Password=? and User=?";
		PreparedStatement stmt1=con.prepareStatement(q1);
		stmt1.setString(1,id);
		stmt1.setString(2,password);
		stmt1.setString(3,user);
		ResultSet set1=stmt1.executeQuery();
		
		//check if the provided details is correct or wrong
		if(!captcha.equals("UKKKG4")) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Invalid Captcha");
        }
		else if(set1.next())
		{
			
			
            switch(user)
			{
				case "Admin":
					AdminApp adminApp = new AdminApp();
		            adminApp.Admin(user,id,primaryStage);
					break;
				case "Faculty":
					FacultyApp facultyApp = new FacultyApp();
		            facultyApp.Faculty(user,id,primaryStage);
					break;
				case "Staff":
					//StaffApp stafApp = new StaffApp();
		            //staffApp.Staff(user,id,primaryStage);
					break;
				case "Student":
					StudentApp studentApp = new StudentApp();
		            studentApp.Student(user,id,primaryStage);
					break;
				default:
				{
					showAlert(Alert.AlertType.ERROR, "Form Error!",
	                        "Invalid Input");
	                break;
				}
			}
		}
		else if(id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "UserID cannot be empty");
        }
    	else if(password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Password cannot be empty");
        }
    	
        else {
        	showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter correct ID and Password");
        	
        }
    	}
    	catch (Exception e1)
    	{
    		showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "User not present!!");
    		
    	}
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
