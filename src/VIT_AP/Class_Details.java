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
public class Class_Details extends Application
{	
	private Stage primaryStage;
	String ID;
	String user;
	GridPane gridPane = new GridPane();
	Class_Details(String user,String ID)
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
        primaryStage.setTitle("Class_Details");
        primaryStage.show();
        
    }
    private GridPane createLoginPane() {
        // Create the login pane
    	
    	
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        Label label = new Label("Semester Year");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        final Button viewButton = new Button("View");
        viewButton.setOnAction(e -> View(yearField.getText()));
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
	public void View(String year) 
	{
		
		try{
		
		int n=0;//to check if the user is regisrtered for any class
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
		
		String q="";
		if (user.equals("Faculty"))
			q="select * from classes_"+year+" where FacultyID=? ";
		else 
			q="select * from Stud_Enrollment_"+year+" where ID=?";
		PreparedStatement stmt=con.prepareStatement(q);
		stmt.setString(1,ID);
		System.out.println(ID);
		ResultSet set1=stmt.executeQuery();// to get all the class details registered of a particular year
		int d=3,a=1;
		while (set1.next())
		{
			String ClassID=set1.getString("ClassID");
			String sem=set1.getString("Semester");
			String Fname=set1.getString("Faculty_Name");
			String Ccode=set1.getString("Course_Code");
			String Cname=set1.getString("Course_Name");
			String slot=set1.getString("Slot");
			String venue=set1.getString("Venue");
			Label label0 = new Label(("Class_"+a+" :"));
			label0.setFont(new Font("Arial", 20));
			a++;
			Label label1 = new Label(("Course Code: "+Ccode));
			label1.setFont(new Font("Arial", 15));
			Label label2 = new Label(("Course Name: "+Cname));
			label2.setFont(new Font("Arial", 15));
			Label label3 = new Label(("ClassID: "+ClassID));
			label3.setFont(new Font("Arial", 15));
			Label label4 = new Label(("Semester: "+sem));
			label4.setFont(new Font("Arial", 15));
			Label label5 = new Label(("Faculty Name: "+Fname));
			label5.setFont(new Font("Arial", 15));
			Label label6 = new Label(("Slot: "+slot));
			label6.setFont(new Font("Arial", 15));
			Label label7 = new Label(("Venue: "+venue));
			label7.setFont(new Font("Arial", 15));
			gridPane.add(label0, 0, d);
			d++;
			d++;
			gridPane.add(label1, 0, d);
			d++;
			gridPane.add(label2, 0, d);
			d++;
			gridPane.add(label3, 0, d);
			d++;
			gridPane.add(label4, 0, d);
			d++;
			gridPane.add(label5, 0, d);
			d++;
			gridPane.add(label6, 0, d);
			d++;
			gridPane.add(label7, 0, d);
			d++;
			d++;
			System.out.println("ClassID: "+ClassID+" ;   "+"Semester: "+sem);
			System.out.println("Faculty_Name : "+Fname+" ;  "+"Slot: "+slot+" ;  "+"Venue: "+venue);
			System.out.println("Course Code : "+Ccode+" ;  "+"Course Name: "+Cname);
			System.out.println();
			//class day and time
			String days[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
			
			for (int j=0;j<days.length;j++)
			{
				String q3="select * from TimeTable_"+year+" where "+days[j]+" = ? ";
				PreparedStatement stmt3=con.prepareStatement(q3);
				stmt3.setString(1,slot);
				ResultSet set3=stmt3.executeQuery();
						
				while(set3.next())
				{
					Label label8 = new Label(("Days: "+days[j]));
					System.out.println("Day:"+days[j]);	
					String t1=set3.getString("Start");
					String t2=set3.getString("End");
					Label label9 = new Label(("Time: "+t1+"-"+t2));
					System.out.println("Time: "+t1+"-"+t2);

					gridPane.add(label8, 0, d);
					d++;
					gridPane.add(label9, 0, d);
					d++;
				}
				
			}
			System.out.println();
			n=1;//true if the user has been resitered for class
			
			
			
		}
		if (n!=1)
		{//false if the user has been resitered for class
			System.out.println("Not Registered for any class");
			showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Not Registered for any class");
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
	
