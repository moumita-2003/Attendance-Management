package Attendance;
import java.sql.*;
import javafx.application.Application;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import VIT_AP.AdminApp;
import VIT_AP.FacultyApp;
import VIT_AP.StudentApp;

//import application.TableExample.Person;

public class ModifyAttendance2 extends Application {
	String year;
	String date;
	String attendee;
	TextField id ;
	TextField status ;
	String AID;
	Stage stage;
	String ClassID;
	ModifyAttendance2(String attendee,String year,String date,String AID)
	{
		this.attendee=attendee;
		this.year=year;
		this.date=date;
		this.AID=AID;
	}
    public static void main(String[] args) {
        launch(args);
    }
    public void studentmodify(String ClassID,Stage stage)
    {
    	this.ClassID=ClassID;
    	start(stage);
    }
    @Override
    public void start(Stage stage) {
    	this.stage=stage;
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
    		
    			
        TableView<Data> tableView = new TableView<>();
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(550);
 
        final Label label = new Label("Attendance Sheet");
        final HBox hb = new HBox();
        label.setFont(new Font("Arial", 20));
 
        tableView.setEditable(true);
        TableColumn<Data, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Data, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(statusColumn);

        List<Data> dataList = new ArrayList<>();
        /*attendee="faculty";
        year="2023";
        date="01/05/2023";*/
        String q1;
        if (attendee.equals("Student"))
        {
        	q1="select * from class_"+ClassID+" where Date=?";
        }
        else
        q1="select * from "+attendee+"_"+year+"_Attendance where Date=?";
		PreparedStatement stmt1=con.prepareStatement(q1);
		stmt1.setString(1,date);
		ResultSet set1=stmt1.executeQuery();

        // Retrieve the metadata for the result set
        ResultSetMetaData metaData = set1.getMetaData();

        // Get the column count
        int columnCount = metaData.getColumnCount();

        // Iterate over the columns and retrieve column names
        while (set1.next()) {
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            String n=set1.getString(columnName);
          
        dataList.add(new Data(columnName,n));
        }
        }

        tableView.getItems().addAll(dataList);
        this.id = new TextField();
        id.setPromptText("ID");
        //addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        this.status = new TextField();
        //addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        status.setPromptText("Status");
        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try {
            		String q2;
            		if (attendee.equals("Student"))
            			q2="update class_"+ClassID+" set _"+id.getText()+"=? where date=?";
            		
            		else
            			q2="update "+attendee+"_"+year+"_Attendance set _"+id.getText()+"=? where date=?";
				PreparedStatement stmt2=con.prepareStatement(q2);
				stmt2.setString(1,status.getText());
				stmt2.setString(2,date);
				
				stmt2.executeUpdate();
				infoBox("Updated Successfully", null, "Success");
                id.clear();
                status.clear();
            	}
            	catch (Exception ex)
            	{
            		showAlert(Alert.AlertType.ERROR, "Form Error!",
                            "Error1");
            	}
            }
        });
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
    		            //staffApp.Staff(attendee,AID,primaryStage);
    					break;
    				case "Student":
    					StudentApp studentApp = new StudentApp();
    		            studentApp.Student(attendee,AID,stage);
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
        hb.getChildren().addAll(id, status, modifyButton,gobackButton);
        hb.setSpacing(3);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableView, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
    catch (Exception ex)
	{
		showAlert(Alert.AlertType.ERROR, "Form Error!",
                "Error");
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
        private String status;

        public Data(String id, String status) {
            this.id = id;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }
    }
}
