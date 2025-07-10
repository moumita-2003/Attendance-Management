package Attendance;
import java.sql.*;

import Attendance.ViewAttendance2;

import VIT_AP.StudentApp;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class ViewStudAttendance extends Application {
 
	static Stage stage;
    GridPane gridPane = new GridPane();
	//static String year;
	String attendee;
	String AID;
	String year;
	
	public void View(String attendee,String ID,Stage stage) 
	{
		
		this.attendee=attendee;
		
		this.AID=ID;
		start(stage);
		
	}	
	public void Search(String year1)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
			 String ID="_"+AID;
			String q1="select table_name from information_schema.columns where column_name=?";
			PreparedStatement stmt=con.prepareStatement(q1);
			stmt.setString(1,ID);
			ResultSet set=stmt.executeQuery();
			int c=5;
			while (set.next())
			{
				String table=set.getString("table_name");
				TableView<Person> tableView = new TableView<Person>();
				
				tableView.setPrefHeight(100);
				ObservableList<Person> personList = FXCollections.observableArrayList();
				 TableColumn<Person, String> Col1 = new TableColumn<>("Details");
			        TableColumn<Person, Integer> Col2 = new TableColumn<>("Data");
			        Col1.setCellValueFactory(new PropertyValueFactory<>("details"));
			        Col2.setCellValueFactory(new PropertyValueFactory<>("data"));
			        tableView.getColumns().addAll(Col1, Col2);
      //table=class_(ClassID), so after 5th index classid starts so to get classid substring from 6 is taken
		String id=table.substring(6);
		String q3="select * from classes_"+year1+" where ClassID=?";
		PreparedStatement stmt3=con.prepareStatement(q3);
		stmt3.setString(1,id);
		ResultSet set0=stmt3.executeQuery();
		
		while (set0.next())
		{
			
			Object Ccode=set0.getObject("Course_Code");
			Object Cname=set0.getObject("Course_Name");
			String  Ccode2=Ccode.toString();
			String Cname2=Cname.toString();
			personList.add(new Person(Ccode2,Cname2));
		
				int p=0,a=0;
				String q2="select * from "+table+" where "+ID+" in (?)";
				PreparedStatement stmt2=con.prepareStatement(q2);
				stmt2.setString(1,"p");
				ResultSet set2=stmt2.executeQuery();
				
				while(set2.next())
				{
					Object date=set2.getObject("Date");
					p++;
				}
				stmt2.setString(1,"a");
				ResultSet set3=stmt2.executeQuery();
				while(set3.next())
				{
					Object date=set3.getObject("Date");
					a++;
				}
				
				String present="   "+p;
				personList.add(new Person("Total Presents:",present));
				
				String absent="   "+a;
				personList.add(new Person("Total Absents:",absent));
				double ap=Math.round((p*100.00)/(p+a));
				//double ap=(p*100.00)/(p+a);
				
				String per="   "+ap+" %";
				personList.add(new Person("Attendance Percentage:",per));
				tableView.setItems(personList);
				VBox root = new VBox(tableView);
				gridPane.add(root, 0,c++);
				
			}
			}
			Label label3 = new Label("View Attendance details of class:");
	        label3.setFont(new Font("Arial", 15));
	        Label label4 = new Label("Course Code:");
	        label4.setFont(new Font("Arial", 15));
	        final TextField ccode = new TextField();
	        ccode.setPromptText("Eg: CSE2005");
	        ccode.setMaxWidth(100);
	        gridPane.add(label3, 0, c++);
	        gridPane.add(label4, 0, c);
	        gridPane.add(ccode, 1, c++);
	        final Button viewButton2 = new Button("View");
	        viewButton2.setOnAction(e -> Details(ccode.getText(),year1));
	        gridPane.add(viewButton2, 1, c++);
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
        Scene loginScene = new Scene(loginPane, 800, 750);

        // Set the login scene on the stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("View Attendance");
        primaryStage.show();
        
    }

    private GridPane createLoginPane() {
        // Create the login pane
        
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Add controls to the login pane
        
		Label label1 = new Label("Attendance");
        label1.setFont(new Font("Arial", 20));
        Label label2 = new Label("Year:");
        label2.setFont(new Font("Arial", 15));
        final TextField yearfield = new TextField();
        yearfield.setPromptText("Year");
        yearfield.setMaxWidth(100);
        this.year=yearfield.getText();
        
        //year=yearfield.getText();
        
   final Button viewButton = new Button("Enter");
  viewButton.setOnAction(e -> Search(yearfield.getText()));
		
    
	
		
    final Button gobackButton = new Button("Go Back");
    gobackButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
        	
					StudentApp studentApp = new StudentApp();
		            studentApp.Student(attendee,AID,stage);
        	
				
			
        }
    });

        
        
    gridPane.add(label1, 0, 0);
    gridPane.add(label2, 0, 1);
    gridPane.add(yearfield, 0, 2);
    gridPane.add(viewButton, 0, 3);
    //gridPane.add(root1, 0,4);
    //gridPane.add(root2, 1,4);
    gridPane.add(gobackButton, 1,3);


        return gridPane;
    
    
    }
    
    
   public void Details(String code,String year)
    {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
			System.out.println(code);
			String q0="select * from Stud_Enrollment_"+year+" where ID=? and Course_Code=? ";
			PreparedStatement stmt0=con.prepareStatement(q0);
			stmt0.setString(1,AID);
			stmt0.setString(2,code);
			System.out.println(AID);
			ResultSet set0=stmt0.executeQuery();
			System.out.println("HI");
			if (set0.next())
			{
				System.out.println("HI");
				String classid=set0.getString("ClassID");
				ViewAttendance2 VA=new ViewAttendance2();
                VA.StudView("Student",AID,stage,classid);
				
			}
				
    	}
    	catch(Exception e)
        {
        	showAlert(Alert.AlertType.ERROR, "Form Error!",
                   "Invalid Input");
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

    public class Person {
        private SimpleStringProperty details;
        private SimpleStringProperty data;

        public Person(String details, String data) {
            this.details = new SimpleStringProperty(details);
            this.data = new SimpleStringProperty(data);
        }

        public SimpleStringProperty detailsProperty() {
            return details;
        }

        public SimpleStringProperty dataProperty() {
            return data;
        }
    }
    
}
