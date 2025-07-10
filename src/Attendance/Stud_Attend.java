package Attendance;
import java.sql.*;
import Attendance.ModifyAttendance2;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
public class Stud_Attend extends Application{
	Stage primaryStage;
	GridPane gridPane = new GridPane();
	String FID;
	String option;
public void Stud(String ID,Stage primaryStage,String option)
{
	this.FID=ID;
	this.primaryStage = primaryStage;
	this.option=option;
	start(primaryStage);
}
public static void main(String[] args) {
    launch(args);
}

@Override
public void start(Stage primaryStage)
{
	//FID="23SCOPE1001";
	this.primaryStage = primaryStage;
    // Create the login scene
    GridPane loginPane = createLoginPane();
    Scene loginScene = new Scene(loginPane, 400, 600);

    // Set the login scene on the stage
    primaryStage.setScene(loginScene);
    primaryStage.setTitle("Login");
    primaryStage.show();
    
}
private GridPane createLoginPane() {
    // Create the login pane
    
    gridPane.setPadding(new Insets(20));
    gridPane.setVgap(10);
    gridPane.setHgap(10);
    Label welcomeLabel = new Label("Welcome to Attendance Page");
    Label yearLabel = new Label("Year:");
    TextField yearField = new TextField();
    Label dateLabel = new Label("Date:");
    TextField dateField = new TextField();
    Button enterButton=new Button("Enter");
    
    gridPane.add(welcomeLabel, 0, 0);
    gridPane.add(yearLabel, 0, 1);
    gridPane.add(yearField, 1, 1);
    gridPane.add(dateLabel, 0, 2);
    gridPane.add(dateField, 1, 2);
    gridPane.add(enterButton, 1, 3);
    enterButton.setOnAction(event -> {
        String year=yearField.getText();
        String date=dateField.getText();
        performAction(year,date);
    });
    return gridPane;
}
private void performAction(String year,String date) {
	 List<String> codeList = new ArrayList<>();
     List<String> slotList = new ArrayList<>();
     Label codeLabel = new Label("Course code:");
		ChoiceBox<String> choiceBox1 = new ChoiceBox<>();
		choiceBox1.setItems(FXCollections.observableArrayList(codeList));
		Label slotLabel = new Label("Slot:");
		ChoiceBox<String> choiceBox2 = new ChoiceBox<>();
		choiceBox2.setItems(FXCollections.observableArrayList(slotList));
		final Button okButton = new Button("OK");
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
		String q="select * from classes_"+year+" where FacultyID=?";
		PreparedStatement pstmt=con.prepareStatement(q);
		pstmt.setString(1,FID);
		ResultSet set=pstmt.executeQuery();
		while(set.next())//get the course code and slots which faculty is being assign
		{
			String Ccode1=set.getString("Course_Code");
			codeList.add(Ccode1);
			String slot1=set.getString("Slot");
			slotList.add(slot1);
		}
		choiceBox1.setItems(FXCollections.observableArrayList(codeList));
		choiceBox2.setItems(FXCollections.observableArrayList(slotList));
             
		okButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent e) {
            	String code=choiceBox1.getValue();
		        String slot=choiceBox2.getValue();
			try {
		        
		        String q5="select * from classes_"+year+" where FacultyID=? and Course_Code=? and slot=?";
				PreparedStatement pstmt5=con.prepareStatement(q5);
				pstmt5.setString(1,FID);
				pstmt5.setString(2,code);

				pstmt5.setString(3,slot);
				ResultSet set5=pstmt5.executeQuery();
				String ClassID="";
				if (set5.next())//get the CLASSID
				{
					ClassID=set5.getString("ClassID");
					System.out.println(ClassID);
					if (option.equals("Take Student Attendance"))
					{
				String q4="insert into class_"+ClassID+" (Date) values(?)";
				PreparedStatement pstmt4=con.prepareStatement(q4);
				pstmt4.setString(1,date);
				pstmt4.executeUpdate();
				Student_Attendance SA= new Student_Attendance();
		        SA.Stud_A(FID,year,date,ClassID,primaryStage);
					}
					else if (option.equals("Modify Student Attendance"))
					{
						ModifyAttendance2 MA = new ModifyAttendance2("Student",year,date,FID);
		            	MA.studentmodify(ClassID,primaryStage);
					}
				} 
				else 
					showAlert(Alert.AlertType.ERROR, "Form Error!",
				                "Cannot find class");
			}
				catch (Exception ex)
				{
					showAlert(Alert.AlertType.ERROR, "Form Error!",
			                "class not found");
				}
            
	        
            }
	        
	    });
		
	}
	catch (Exception ex)
	{
		showAlert(Alert.AlertType.ERROR, "Form Error!",
                "Error1");
	}
	VBox root1 = new VBox(choiceBox1);
	VBox root2= new VBox(choiceBox2);
	gridPane.add(codeLabel, 0, 3);
    gridPane.add(root1, 1, 3);
    gridPane.add(slotLabel, 0, 4);
    gridPane.add(root2, 1, 4);
    gridPane.add(okButton, 1, 5);
	
}


private static void showAlert(Alert.AlertType alertType, String title, String message) {
	Alert alert = new Alert(alertType);
	alert.setTitle(title);
	alert.setHeaderText(null);
	alert.setContentText(message);
	alert.show();
	}
}
