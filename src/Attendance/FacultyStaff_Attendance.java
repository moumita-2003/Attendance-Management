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
 
public class FacultyStaff_Attendance extends Application {
 
	static Stage stage;
    private TableView<Person> table = new TableView<Person>();
	static String date;
	static String year;
	static String attendee;
	static Connection con;
	static String AID;
	
	FacultyStaff_Attendance(String attendee,String year,String date,String AID)
	{
		this.year=year;
		this.attendee=attendee;
		this.date=date;
		this.AID=AID;
		try {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
		String q7="insert into "+attendee+"_"+year+"_Attendance  (Date) values (?)";
		PreparedStatement pstmt7=con.prepareStatement(q7);
		pstmt7.setString(1,date);
		pstmt7.executeUpdate();
		
		}
	catch(Exception e1)
	{
		showAlert(Alert.AlertType.ERROR, "Form Error!",
                "Error");
	}
		
	}	

    private final ObservableList<Person> data =
            FXCollections.observableArrayList();
    final HBox hb = new HBox();
    
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
    	this.stage=stage;
    	Scene scene = new Scene(new Group());
        stage.setTitle("Faculty Attendance");
        stage.setWidth(450);
        stage.setHeight(550);
 
        final Label label = new Label("Attendance sheet");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn firstNameCol = new TableColumn("ID");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));
 
        TableColumn lastNameCol = new TableColumn("Attendance Status");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));
 
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);
 
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("ID");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Status");
        
        
        
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Person(
                        addFirstName.getText(),
                        addLastName.getText()));
                addFirstName.clear();
                addLastName.clear();
            }
        });
        
        final Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	Attendance_Page ap = new Attendance_Page();
            	ap.Attendance(AID,stage);
            }
        });
 
        hb.getChildren().addAll(addFirstName, addLastName, addButton,gobackButton);
        hb.setSpacing(2);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
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
        public static void update(String id, String status) 
        {
        try {
        String q4="update "+attendee+"_"+year+"_Attendance set _"+id+"=? where Date=?";
		PreparedStatement pstmt4=con.prepareStatement(q4);
		pstmt4.setString(1,status);
		pstmt4.setString(2,date);
		pstmt4.executeUpdate();
    	}
    	catch(Exception e1)
    	{
    		showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Invalid ID");
    	}
    }
 
    public static class Person {
 
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
 
        private Person(String fName, String lName) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            update(fName,lName); 
        }
 
        public String getFirstName() {
            return firstName.get();
            
        }
 
        public void setFirstName(String fName) {
            firstName.set(fName);
        }
 
        public String getLastName() {
            return lastName.get();
        }
 
        public void setLastName(String fName) {
            lastName.set(fName);
        }
 
    }
    
}