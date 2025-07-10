
package VIT_AP;
import java.sql.*;
import java.sql.DriverManager;

import Attendance.Attendance_Page;
import Attendance.FacultyStaff_Attendance;
import Attendance.ViewAttendance2;
import Attendance.Stud_Attend;
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

public class FacultyApp extends Application {
	String ID;
	String user;
	private Stage primaryStage;
	public void Faculty(String user,String ID,Stage primaryStage)
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
    	

        GridPane facultyPane = createFacultyPane();
        Scene facultyScene = new Scene(facultyPane, 400, 300);

        primaryStage.setScene(facultyScene);
        primaryStage.setTitle("Faculty Page");
        primaryStage.show();
    }

    private GridPane createFacultyPane() {
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
                "Take Student Attendance",
        		"Modify Student Attendance",
                "View my Attendance",
                "View my Registration Details",
                "View my Class Details",
                "Change my Login Password"
                
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
        gridPane.add(chooseLabel, 0, 3);
        gridPane.add(root, 0, 4);
        gridPane.add(okbutton, 0, 5);
        gridPane.add(logoutButton, 0, 6);
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
            case "Take Student Attendance":
            	Stud_Attend SA = new Stud_Attend();
            	SA.Stud(ID,primaryStage,selectedOption);
                break;
            case "Modify Student Attendance":
            	Stud_Attend MA = new Stud_Attend();
            	MA.Stud(ID,primaryStage,selectedOption);
                break;
            case "View my Attendance":
            	ViewAttendance2 VA = new ViewAttendance2();
            	VA.View(user,ID,primaryStage);
                break;
                
            case "View my Registration Details":
            	View_Details VMD=new View_Details("Faculty",ID);
				VMD.start(primaryStage);
                break;
            case "View my Class Details":
            	Class_Details VCD=new Class_Details("Faculty",ID);
				VCD.start(primaryStage);
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
            
                
            default:
            	showAlert(Alert.AlertType.ERROR, "Form Error!",
                        "Choose a option");
                break;
        }
    }

    private void handleLogout() {
        /*LoginApp loginApp = new LoginApp();
        Stage loginStage = new Stage();
        loginApp.start(loginStage);*/
        //Stage facultyStage = (Stage) getStage().getScene().getWindow();
        primaryStage.close();
        showAlert(Alert.AlertType.ERROR, "Form Error!",
                "Logged Out");
    }

    private static Stage getStage() {
        return (Stage) FacultyApp.getStage().getScene().getWindow();
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
