package Attendance;
import java.time.LocalDate;
import java.util.*;
import Attendance.ModifyAttendance2;
import VIT_AP.AdminApp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public class Attendance_Page extends Application {
	String ID;
	private Stage primaryStage;
	public void Attendance(String ID,Stage primaryStage)
	{
		this.ID=ID;
		start(primaryStage);
	}
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        GridPane adminPane = createAdminPane();
        Scene adminScene = new Scene(adminPane, 400, 300);

        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Attendance Page");
        primaryStage.show();
    }

    private GridPane createAdminPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        
        Label welcomeLabel = new Label("Welcome to Attendance Page");
        Label yearLabel = new Label("Year:");
        TextField yearField = new TextField();
        //year=yearField.getText();
        
        Label dateLabel = new Label("Date:");
        
       // LocalDate date1=LocalDate.now();
       // String date=""+date1;
        //Label dateLabel = new Label(("Date: "+date));
       TextField dateField = new TextField();
       
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(FXCollections.observableArrayList(
                "Take Faculty Attendance",
        		"Take Staff Attendance",
                "Modify Faculty Attendance",
                "Modify Staff Attendance"
                
        ));

        VBox root = new VBox(choiceBox);
        Button okbutton = new Button("OK");
        okbutton.setOnAction(event -> {
            String selectedOption = choiceBox.getValue();
            performAction(selectedOption,yearField.getText(),dateField.getText());
        });
        Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	AdminApp adminApp = new AdminApp();
                adminApp.Admin("Admin",ID,primaryStage);
            }
        });
       /* AdminApp adminApp = new AdminApp();
        adminApp.Admin(ID,primaryStage);*/
        gridPane.add(welcomeLabel, 0, 0);
        gridPane.add(root, 0, 1);
        gridPane.add(yearLabel, 0, 2);
        gridPane.add(yearField, 1, 2);
        gridPane.add(dateLabel, 0, 3);
        gridPane.add(dateField, 1, 3);
        gridPane.add(okbutton, 1, 6);
        gridPane.add(gobackButton, 1, 7);
        
        
        return gridPane;
    }
    private void performAction(String selectedOption,String year,String date) {
    	try {
        switch (selectedOption) {
        
            case "Take Faculty Attendance":
            	FacultyStaff_Attendance FA = new FacultyStaff_Attendance("faculty",year,date,ID);
            	FA.start(primaryStage);
                break;
            case "Take Staff Attendance":
            	FacultyStaff_Attendance SA = new FacultyStaff_Attendance("staff",year,date,ID);
            	SA.start(primaryStage);
                break;
            case "Modify Faculty Attendance":
            	ModifyAttendance2 MA = new ModifyAttendance2("faculty",year,date,ID);
            	MA.start(primaryStage);
                break;
            case "Modify Staff Attendance":
            	ModifyAttendance2 MAS = new ModifyAttendance2("staff",year,date,ID);
            	MAS.start(primaryStage);
                break;
                
            default:
                System.out.println("No action defined for the selected option");
                break;
        }
    	}
    	catch (Exception e)
    	{
    		System.out.println("No action defined for the selected option");
    	}
    }
 

    private static Stage getStage() {
        return (Stage) Attendance_Page.getStage().getScene().getWindow();
    }
    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
