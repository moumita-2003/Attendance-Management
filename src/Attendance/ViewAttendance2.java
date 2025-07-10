package Attendance;
import java.sql.*;

import VIT_AP.AdminApp;
import VIT_AP.FacultyApp;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class ViewAttendance2 extends Application {
 
	static Stage stage;
    private TableView<Person> tableView1 = new TableView<Person>();
    private TableView<Person> tableView2 = new TableView<Person>();
    GridPane gridPane = new GridPane();
	//static String year;
	String attendee;
	static Connection con;
	String AID;
	String CID;
	public void StudView(String attendee,String ID,Stage stage,String CID)
	{
		this.CID=CID;
		View(attendee,ID,stage);
		
	}	
	public void View(String attendee,String ID,Stage stage)
	{
		this.attendee=attendee;
		
		this.AID=ID;
		start(stage);
		
	}	
	public void Search(ObservableList<Person> List1,ObservableList<Person> List2,String year)
	{
		try
		{
			
		
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
			
			 String ID="_"+AID;
			
				String table;
				if (attendee.equals("Student"))
					table="class_"+CID;
				else
					table=attendee+"_"+year+"_attendance";
				int p=0,a=0;
				String q2="select * from "+table+" where "+ID+" in (?)";
				PreparedStatement stmt2=con.prepareStatement(q2);
				stmt2.setString(1,"p");
				ResultSet set2=stmt2.executeQuery();
				
				while(set2.next())
				{
					Object date=set2.getObject("Date");
					String date1=""+date;
					List1.add(new Person (date1));
					p++;
				}
				stmt2.setString(1,"a");
				ResultSet set3=stmt2.executeQuery();
				while(set3.next())
				{
					Object date=set3.getObject("Date");					
					String date2=""+date;
					List2.add(new Person (date2));
					a++;
				}
				
				String present="Total Presents: "+p;
				Label label3 = new Label(present);
				String absent="Total Absents: "+a;
				Label label4 = new Label(absent);
				double ap=Math.round((p*100.00)/(p+a));
				//double ap=(p*100.00)/(p+a);
				
				String per="Attendance Percentage: "+ap+" %";
				Label label5 = new Label(per);
				gridPane.add(label3, 0,6);
				gridPane.add(label4, 1,6);
				gridPane.add(label5, 0,7);
			
			System.out.println(List1);
			tableView1.getItems().addAll(List1);
			tableView2.getItems().addAll(List2);
		}
        catch(Exception e)
        {
        	showAlert(Alert.AlertType.ERROR, "Form Error!",
                   "Error");
        }  
		
		}
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        // Create the login scene
        GridPane loginPane = createLoginPane();
        Scene loginScene = new Scene(loginPane, 500, 800);

        // Set the login scene on the stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
        
    }

    private GridPane createLoginPane() {
        // Create the login pane
        
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Add controls to the login pane
        tableView1.setEditable(true);
   	 
        TableColumn<Person, String> Col1 = new TableColumn<>("Present Days");
        TableColumn<Person, Integer> Col2 = new TableColumn<>("Absent Days");
        Col1.setCellValueFactory(new PropertyValueFactory<>("dates"));
        Col2.setCellValueFactory(new PropertyValueFactory<>("dates"));
        
        

        // Add the columns to the table
        tableView1.getColumns().addAll(Col1);
        tableView2.getColumns().addAll(Col2);
        ObservableList<Person> List1 = FXCollections.observableArrayList();
        ObservableList<Person> List2 = FXCollections.observableArrayList();
        
        VBox root1 = new VBox(tableView1);
        VBox root2 = new VBox(tableView2);
		Label label1 = new Label("Attendance");
        label1.setFont(new Font("Arial", 20));
        Label label2 = new Label("Year:");
        label2.setFont(new Font("Arial", 15));
        final TextField yearfield = new TextField();
        yearfield.setPromptText("Year");
        yearfield.setMaxWidth(100);
        //year=yearfield.getText();
        
   final Button viewButton = new Button("View");
  viewButton.setOnAction(e -> Search(List1,List2,yearfield.getText()));
		
    
	
		
    final Button gobackButton = new Button("Go Back");
    gobackButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
        	switch(attendee)
			{
				case "Admin":
					AdminApp adminApp = new AdminApp();
		            adminApp.Admin(attendee,AID,stage);
					break;
				case "Faculty":
					FacultyApp facultyApp = new FacultyApp();
		            facultyApp.Faculty(attendee,AID,stage);
					break;
				case "Staff":
					//StaffApp stafApp = new StaffApp();
		            //staffApp.Staff(attendee,AID,stage);
					break;
				case "Student":
					ViewStudAttendance studentApp = new ViewStudAttendance();
					studentApp.View(attendee,AID,stage);
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

        
        
    gridPane.add(label1, 0, 0);
    gridPane.add(label2, 0, 1);
    gridPane.add(yearfield, 0, 2);
    gridPane.add(viewButton, 1, 2);
    gridPane.add(root1, 0,4);
    gridPane.add(root2, 1,4);
    gridPane.add(gobackButton, 1, 10);


        return gridPane;
    
    
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

    public class Person {
        private SimpleStringProperty dates;

        public Person(String dates) {
            this.dates = new SimpleStringProperty(dates);
        }


        public SimpleStringProperty datesProperty() {
            return dates;
        }
    }
    
}