package VIT_AP;
import java.sql.*;
import VIT_AP.Login_Details;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Attendance.Attendance_Page;
import Attendance.ViewAttendance2;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AdminApp extends Application {
	String ID;
	String user;
	private Stage primaryStage;
	
	public void Admin(String user,String ID,Stage primaryStage)
	{
		this.user=user;
		this.ID=ID;
		start(primaryStage);
	}
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;

        GridPane adminPane = createAdminPane();
        Scene adminScene = new Scene(adminPane, 400, 300);

        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Admin Page");
        primaryStage.show();
    }

    private GridPane createAdminPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        
        try {
    		
    		Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
			String q1="select * from Login_Details where ID=? ";
			PreparedStatement stmt1=con.prepareStatement(q1);
			stmt1.setString(1,ID);
			ResultSet set1=stmt1.executeQuery();
			
			//check if the provided details is correct or wrong
			
			if(set1.next())
			{
				String name=set1.getString("CName");
				String label1="		Welcome, "+name;
				String label2="Registration No. : "+ID;
        Label welcomeLabel = new Label(label1);
        welcomeLabel.setFont(new Font("Arial", 20));
        Label regLabel = new Label(label2);
        regLabel.setFont(new Font("Arial", 15));
        Label chooseLabel = new Label("Choose one of the option");
			
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(FXCollections.observableArrayList(
                "Registration Page",
        		"Enrollment Page",
                "Take/Modify Attendance",
                "View my Attendance",
                "View Registration Details",
                "View Class Details",
                "Change my Login Password",
                "Change Admin"
                
        ));

        VBox root = new VBox(choiceBox);
        Button okbutton = new Button("OK");
        okbutton.setOnAction(event -> {
            String selectedOption = choiceBox.getValue();
            performAction(selectedOption);
        });
        Button logoutButton = new Button("Logout");

        logoutButton.setOnAction(e -> handleLogout());
        
        gridPane.add(welcomeLabel, 0, 0);
        gridPane.add(regLabel, 0, 1);
        gridPane.add(chooseLabel, 0, 2);
        gridPane.add(root, 0, 3);
        gridPane.add(okbutton, 0, 4);
        gridPane.add(logoutButton, 0, 5);
        
        
			}
			else {
				showAlert(Alert.AlertType.ERROR, "Form Error!",
	                       "Invalid userID or password!!");
			}
			
        
        	}
        	catch(Exception e)
            {
            	showAlert(Alert.AlertType.ERROR, "Form Error!",
                       "Invalid Input");
            } 
        	return gridPane;
    }
    private void performAction(String selectedOption) {
        switch (selectedOption) {
            case "Registration Page":
            	infoBox("Login Successful!", null, "Success");
                break;
            case "Enrollment Page":
            	infoBox("Login Successful!", null, "Success");
                break;
            case "Take/Modify Attendance":
            	Attendance_Page ap = new Attendance_Page();
            	ap.Attendance(ID,primaryStage);
                break;
            case "View my Attendance":
            	ViewAttendance2 VA = new ViewAttendance2();
            	VA.View(user,ID,primaryStage);
                break;
            case "View Registration Details":
            	infoBox("Login Successful!", null, "Success");
                break;
            case "View Class Details":
            	infoBox("Login Successful!", null, "Success");
                break;
                
            case "Change my Login Password":
            	try {
            	Login_Details CP= new Login_Details(user,primaryStage,ID);
				CP.Change_Password();
            	}
				catch(Exception ex)
				{
					showAlert(Alert.AlertType.ERROR, "Form Error!",
		                    "Invalid Input");
				}
                break;
            case "Change Admin":
            	try {
            	Login_Details CA= new Login_Details(user,primaryStage,ID);
				CA.Change_Admin();
                break;  
                }
				catch(Exception ex)
				{
					showAlert(Alert.AlertType.ERROR, "Form Error!",
		                    "Invalid Input");
				}
                
            default:
            	showAlert(Alert.AlertType.ERROR, "Form Error!",
                        "Choose a option");
                break;
        }
    }

    private void handleLogout() {
        primaryStage.close();
        showAlert(Alert.AlertType.ERROR, "Form Error!",
                "Logged Out");
    }
    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
