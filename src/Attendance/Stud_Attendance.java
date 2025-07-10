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
import javafx.scene.control.ChoiceBox;
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

import Attendance.FacultyStaff_Attendance.Person;


public class Stud_Attendance extends Application {
	String year;
	String date;
	String ccode;
	String slot;
	String AID;
	Stage stage;
	String ClassID="";
	final TextField IDList = new TextField();
	
    final TextField NameList = new TextField();
    
    final TextField statusList = new TextField();
	
	final Button addButton = new Button("OK");
	ArrayList<String> attend=new ArrayList<>();//stores the attendance of students in array
	
 int a=0;
	public void Stud_A(String AID,String year,String date,String code,String slot,Stage stage)
	{
		/*IDList.setMinWidth(100);
		NameList.setMinWidth(100);
		statusList.setMinWidth(100);*/
		this.ccode=code;
		this.slot=slot;
		this.year=year;
		this.date=date;
		this.AID=AID;
		this.stage=stage;
		start(stage);
	}
    public static void main(String[] args) {
        launch(args);
    }
    private final ObservableList<Data> data =
            FXCollections.observableArrayList();
    @Override
    public void start(Stage stage) {

		System.out.println("Available classes:");
        TableView<Data> tableView = new TableView<>();
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(550);
 
        final Label label = new Label("Attendance Sheet");
        final HBox hb = new HBox();
        label.setFont(new Font("Arial", 20));
        Label welcomeLabel = new Label("Welcome to Attendance Page");
        Label yearLabel = new Label(("Year: "+year));
        
        Label dateLabel = new Label(("Date: "+date));
        
        tableView.setEditable(true);   
       TableColumn idColumn = new TableColumn("ID");
       idColumn.setMinWidth(100);
       idColumn.setCellValueFactory(
               new PropertyValueFactory<Data, String>("id"));
       TableColumn nameColumn = new TableColumn("Name");
       nameColumn.setMinWidth(100);
       nameColumn.setCellValueFactory(
               new PropertyValueFactory<Data, String>("name"));
       TableColumn statusColumn = new TableColumn("Status");
       statusColumn.setMinWidth(100);
       statusColumn.setCellValueFactory(
               new PropertyValueFactory<Data, String>("status"));
       

        /*TableColumn<Data, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(
                new PropertyValueFactory<Person, String>("id"));
        TableColumn<Data, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Data, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));*/

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(statusColumn);
        tableView.setItems(data);
       
		
        //addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        statusList.setPromptText("Status");
        final Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	Attendance_Page ap = new Attendance_Page();
            	ap.Attendance(AID,stage);
            }
        });
        hb.getChildren().addAll(IDList,NameList,statusList, addButton,gobackButton);
        hb.setSpacing(3);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label,yearLabel,dateLabel, tableView, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
			
			
		        addButton.setOnAction(event -> {
		            performAction();
		        });
				
				//to get (?,?,?......)upto total no.of students+date(total columns)
				/*String s="?,",t="";
				for (int i=1;i<a;i++)
				{
					t=t+s;
				}
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
	        		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
	        		
				t=t+"?)";//t=(?,?,?......(a)?)
				String q4="insert into class_"+ClassID+" values("+t;
				PreparedStatement pstmt4=con.prepareStatement(q4);
				for (int i=0;i<a;i++)
				{	
					pstmt4.setString(i+1,attend.get(i));
				}
				pstmt4.executeUpdate();
				
				System.out.println("Attendance Taken Succesfully!!");
				}
	        	
		        catch (Exception ex)
		    	{
		    		showAlert(Alert.AlertType.ERROR, "Form Error!",
		                    "Error2");
		    	}*/
			
		
    	/*}
	catch(Exception e)
	{
		System.out.println("Invalid Attendance given! Try Again!");
	}*/
	

     
        
        
		}
   
    	public void performAction() {
        	try {
        		
        		Class.forName("com.mysql.cj.jdbc.Driver");
        		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
        		System.out.println(year);
        		
    		String q1="select ClassID from Classes_"+year+" where Course_code=? and FacultyID=? and Slot=?"; 
    		PreparedStatement pstmt1=con.prepareStatement(q1);
    		pstmt1.setString(1,ccode);
    		pstmt1.setString(2,AID);
    		pstmt1.setString(3,slot);
    		ResultSet set1=pstmt1.executeQuery();
    		if(set1.next())
			{
				ClassID=set1.getString("ClassID");
		
				String q3="desc class_"+ClassID;
				Statement stmt3=con.createStatement();
				ResultSet set3=stmt3.executeQuery(q3);
				System.out.println(ClassID);
				/*ArrayList<String> IDList=new ArrayList<>();//stores the stud-id of students in array
				ArrayList<String> NameList=new ArrayList<>();//stores the stud-name of students */
				
				
				//a stores the no.of student+date(total number of columns)
				//attend.add(date);
				while (set3.next())
				{
		System.out.println("Available classes:");
		String f=set3.getString("Field");
		System.out.println(f);
		IDList.setText(f);
		
		/*if(f.equals("Date"))
		{
			continue;
		}
		else {*/
		String ID=f.substring(1);//as f=_ID,to remove first character i.e underscore
		
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
			NameList.setText(name);
			
		}
		String ch=statusList.getText();
		attend.add(ch);//stores the date and attendance of students in array
		
		a++;
	
	data.add(new Data(
			IDList.getText(),
			NameList.getText(),
			statusList.getText()));
	IDList.clear();
    NameList.clear();
    statusList.clear();
    
		}
				}
			//}
        	}
        	
        catch (Exception ex)
    	{
    		showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Error2");
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
	

    public  class Data {
        private SimpleStringProperty id;
        private SimpleStringProperty name;
        private SimpleStringProperty status;

         Data(String id, String name, String status) {
            this.id = new SimpleStringProperty(id);
            this.name =new SimpleStringProperty(name);
            this.status = new SimpleStringProperty(status);
        }

        public String getID() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getStatus() {
            return status.get();
        }
        public void setID(String fName) {
            id.set(fName);
        }
        public void setName(String fName) {
            name.set(fName);
        }
        public void setStatus(String fName) {
            status.set(fName);
        }
    }
}
   