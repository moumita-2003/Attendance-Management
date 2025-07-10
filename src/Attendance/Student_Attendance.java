package Attendance;
import java.sql.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import Attendance.FacultyStaff_Attendance.Person;
import Attendance.Stud_Attendance.Data;
import VIT_AP.FacultyApp;

//import application.TableExample.Person;

public class Student_Attendance extends Application {
	String year;
	String date;
	String ccode;
	String slot;
	String AID;
	Stage stage;
	String ClassID;
    
    final TextField statusList = new TextField();
    final TextField IDcol = new TextField();
    private final ObservableList<Person> data =
            FXCollections.observableArrayList();
	
	final Button addButton = new Button("OK");
	ArrayList<String> attend=new ArrayList<>();//stores the attendance of students in array
	ArrayList<String> IDList=new ArrayList<>();
 int a=0;
 public void Stud_A(String AID,String year,String date,String ClassID, Stage stage)
	{
		
		this.year=year;
		this.date=date;
		this.AID=AID;
		this.ClassID=ClassID;
		this.stage=stage;
		start(stage);
		//attend.add(date);
	}
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
    	this.stage=stage;
    	
    			
        TableView<Data> tableView = new TableView<>();
        TableView<Person> tableView2 = new TableView<>();
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(550);
 
        final Label label = new Label("Attendance Sheet");
        final HBox hb = new HBox();
        final HBox hb2 = new HBox();
        label.setFont(new Font("Arial", 20));
        Label welcomeLabel = new Label("Welcome to Attendance Page");
        Label yearLabel = new Label(("Year: "+year));
        
        Label dateLabel = new Label(("Date: "+date));
        tableView.setEditable(true);
        TableColumn<Data, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Data, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn statusCol = new TableColumn("Status");
        statusCol.setMinWidth(100);
        statusCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("status"));
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView2.getColumns().addAll(statusCol);
        tableView2.setEditable(true);
        List<Data> dataList = new ArrayList<>();
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
    		
			String q3="desc class_"+ClassID;
			Statement stmt3=con.createStatement();
			ResultSet set3=stmt3.executeQuery(q3);
			System.out.println(ClassID);
			
			String ID="";
			while (set3.next())
			{
			String f=set3.getString("Field");
			if(f.equals("Date"))
			{
				continue;
			}
	else {
	ID=f.substring(1);//as f=_ID,to remove first character i.e underscore
	
	//to get the name of the student from student registration through id
	
	String d="select * from login_details where ID = ?";
	
	System.out.println(ID);
	PreparedStatement st=con.prepareStatement(d);
	st.setString(1,ID);
	ResultSet set4=st.executeQuery();
	System.out.println("Available classes:");
	if(set4.next())
	{
		String name=set4.getString("CName");
		//NameList.setText(name);
		dataList.add(new Data(ID,name));
		IDList.add(ID);
		
	}
	
	}
			}
		
        tableView.getItems().addAll(dataList);
        
        Button addButton = new Button("Add");
         
        IDcol.setText(IDList.get(a));
           
          
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try {
            	String ch=statusList.getText();
            	attend.add(ch);//stores the date and attendance of students in array
            	
            	 data.add(new Person(statusList.getText()));
            	 
            	 String q2="update class_"+ClassID+" set _"+IDcol.getText()+"=? where date=?";
     			PreparedStatement stmt2=con.prepareStatement(q2);
     			stmt2.setString(1,ch);
     			stmt2.setString(2,date);
     			
     			stmt2.executeUpdate();
            	 a++;
            	tableView.refresh();
            	statusList.clear();
            	IDcol.clear();
            	IDcol.setText(IDList.get(a));
            }
            	 catch (Exception ex)
            	{
            		 infoBox("Attendance Taken Successful!", null, "Success");
            	}
            }
            
        });
          tableView2.setItems(data);
        tableView2.refresh();
        statusList.setPromptText("Status");
        final Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	FacultyApp facultyApp = new FacultyApp();
	            facultyApp.Faculty("Faculty",AID,stage);
            }
        });
        
        hb.getChildren().addAll(IDcol,statusList, addButton,gobackButton);
        hb.setSpacing(3);
        hb2.getChildren().addAll(tableView,tableView2);
        hb2.setSpacing(0);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label,yearLabel,dateLabel,hb2, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
       
    }
    catch (Exception ex)
	{
		showAlert(Alert.AlertType.ERROR, "Form Error!",
                "Error1");
	}
    }
	private static void showAlert(Alert.AlertType alertType, String title, String message) {
	Alert alert = new Alert(alertType);
	alert.setTitle(title);
	alert.setHeaderText(null);
	alert.setContentText(message);
	alert.show();
	}
	public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
	

    public static class Data {
        private String id;
        private String name;
        private static String status;

        public Data(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }
        public String getStatus() {
            return status;
        }
        public  void setStatus(String s) {
        	status = s;
        }
        public String getName() {
            return name;
        }
    }
    public static class Person {
    	 
        private final SimpleStringProperty status;
 
        private Person(String s) {
            this.status = new SimpleStringProperty(s); 
        }
 
        public String getStatus() {
            return status.get();
            
        }
 
        public void setStatus(String s) {
            status.set(s);
        }
 
 
    }
}
