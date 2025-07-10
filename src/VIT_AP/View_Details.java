package VIT_AP;

import java.sql.*;
import VIT_AP.AdminApp;
import VIT_AP.FacultyApp;
import VIT_AP.StudentApp;
import Attendance.ViewStudAttendance;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class View_Details extends Application
{
	private Stage primaryStage;
	String ID;
	String user;
	GridPane gridPane = new GridPane();
	View_Details(String user,String ID)
	{
		this.ID=ID;
		this.user=user;
	}
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create the login scene
        GridPane loginPane = createLoginPane();
        Scene loginScene = new Scene(loginPane, 400, 600);

        // Set the login scene on the stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Registration_Details");
        primaryStage.show();
        
    }
    private GridPane createLoginPane() {
        // Create the login pane
    	
    	
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        Label label = new Label("Year of Registration");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        final Button viewButton = new Button("View");
        viewButton.setOnAction(e -> my_details(yearField.getText()));
        final Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	switch(user)
    			{
    				case "Admin":
    					AdminApp adminApp = new AdminApp();
    		            adminApp.Admin(user,ID,primaryStage);
    					break;
    				case "Faculty":
    					FacultyApp facultyApp = new FacultyApp();
    		            facultyApp.Faculty(user,ID,primaryStage);
    					break;
    				case "Staff":
    					//StaffApp stafApp = new StaffApp();
    		            //staffApp.Staff(user,ID,primaryStage);
    					break;
    				case "Student":
    					StudentApp studentApp = new StudentApp();
    					studentApp.Student(user,ID,primaryStage);
    					break;
    				default:
    				{
    					showAlert(Alert.AlertType.ERROR, "Form Error!",
    	                        "Invalid Input");
    	                break;
    				}
    			}
            }
        });

        gridPane.add(label, 0, 0);
        gridPane.add(yearField, 0, 1);
        gridPane.add(viewButton, 0, 2);
        gridPane.add(gobackButton, 1, 2);


            return gridPane;
        
        
        }
	
	public void my_details(String year)
	{
		try {
			int d=3;	
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
		
		String q="select * from "+user+"_"+year+" where ID=? ";
		PreparedStatement stmt=con.prepareStatement(q);
		stmt.setString(1,ID);
		ResultSet set1=stmt.executeQuery();
		while (set1.next())
		{
			ResultSetMetaData set2=set1.getMetaData();//to get the metadata of the result set(column_names)
			int num=set2.getColumnCount();//to get the number of columns
			//int d=3;
			for (int i=1;i<=num;i++)
			{
				String col=set2.getColumnName(i);
				//String det=set1.getString(i);
				String det=set1.getString(col);
				Label label1 = new Label((i+"."+col+":"+det));
				label1.setFont(new Font("Arial", 15));
				gridPane.add(label1, 0, d);
				d++;
			}
		
		}
		
		con.close();
		}
		catch(Exception e)
		{
			System.out.println("Class Registration for the given year has not started");
			showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Class Registration for the given year has not started");
		}
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
