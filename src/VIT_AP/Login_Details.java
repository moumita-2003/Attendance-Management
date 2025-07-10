package VIT_AP;
import java.sql.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class Login_Details extends Application
{
	Connection con;
	String user;
	String AID;
	String email;
	Stage primaryStage;
	GridPane gridPane = new GridPane();
	 Button loginButton = new Button("Login"); 
	 TextField emailField;
	 TextField idField;
	public Login_Details(String user,Stage primaryStage,String AID) throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
		
		this.user=user;
		this.AID=AID;
		this.primaryStage = primaryStage;
	}
	/*public static void Login()
	{
		
		try
		{
			//User=faculty/admin/Student/Staff , it is executed when first time using the project
			String q2="create table Login_Details (User char(10),ID char(20),CName varchar(30),EmailID varchar(30),Password varchar(20) Default 'VITAP12345')";
			Statement stmt2=con.createStatement();
			stmt2.executeUpdate(q2);
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
			sc=new Scanner(System.in);
			user="Admin";
				System.out.println("Enter your name:");
				String name=sc.nextLine();
				System.out.println("Enter your ID/Registration no.:");
				String ID=sc.nextLine();
				System.out.println("Enter your Email ID:");
				String Email=sc.nextLine();
				
				String q3="insert into Login_Details (User,ID,CName,EmailID) values(?,?,?,?)";
				PreparedStatement pstmt=con.prepareStatement(q3);
				pstmt.setString(1,user);
				pstmt.setString(2,ID);
				pstmt.setString(3,name);
				pstmt.setString(4,Email);
				pstmt.executeUpdate();
				
			
			System.out.println("Login_Details Added successfully");
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
	}*/
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
        primaryStage.setTitle("Login");
        primaryStage.show();
        
    }
	 private GridPane createLoginPane() {
		 
	        gridPane.setPadding(new Insets(20));
	        gridPane.setVgap(10);
	        Label idLabel = new Label("UserID:");
	        idField = new TextField();
	        
	        Label emailLabel = new Label("Email ID:");
	        emailField = new TextField();
	        emailField.setPromptText("EmailID");
	        gridPane.add(idLabel, 0, 1);
	        gridPane.add(idField, 1, 1);
	        gridPane.add(emailLabel, 0, 2);
	        gridPane.add(emailField, 1, 2);
	        gridPane.add(loginButton, 1, 5);
	        
	        return gridPane;
	 }
	public void Change_Password()
	 {
		Label Label = new Label("Change the password");
		gridPane.add(Label, 0, 0);
		 start(primaryStage);
		 Label passwordLabel = new Label("New Password:");
	        PasswordField passwordField = new PasswordField();
	       
	        passwordField.setPromptText("New Password");
	        
	        Label passwordLabel2 = new Label("Confirm Password:");
	        PasswordField passwordField2= new PasswordField();
	        passwordField2.setPromptText("Confirm Password");
	       
	        System.out.println(passwordField.getText()+"HI");
	        gridPane.add(passwordLabel, 0, 3);
	        gridPane.add(passwordField, 1, 3);
	        gridPane.add(passwordLabel2, 0, 4);
	        gridPane.add(passwordField2, 1, 4);
	        loginButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            
	            public void handle(ActionEvent e) {
	            	String newpass=passwordField.getText();
	            	 String confirmpass=passwordField2.getText();
	            	 String ID=idField.getText();
	            	 String email=emailField.getText();
				
						
				if (newpass.equals(confirmpass) ) 
				{
					try{
					String q="update Login_Details set Password=? where User=? and EmailID=? and ID=?";
					PreparedStatement pstmt=con.prepareStatement(q);
					pstmt.setString(1,newpass);
					pstmt.setString(2,user);
					pstmt.setString(3,email);
					pstmt.setString(4,ID);
					pstmt.executeUpdate();
				infoBox("Password Changed Successfully!", null, "Success");
				primaryStage.close();
					}
					catch(Exception ex)
					{
						showAlert(Alert.AlertType.ERROR, "Form Error!",
			                    "Invalid Input2");
					}
				
				}
				
				else
					showAlert(Alert.AlertType.ERROR, "Form Error!",
		                    "Confirm Password didn't match");
				
				}     
			    });
	       
	        
	 }
	public void Change_Admin() 
	{  
		Label Label = new Label("Enter the details of New Admin");
		gridPane.add(Label, 0, 0);
		start(primaryStage);
		idField.setPromptText("New Admin UserID");
        emailField.setPromptText("New Admin EmailID");
       
			
		 loginButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            
	            public void handle(ActionEvent e) {
	            	String ID=idField.getText();
			       	 String email=emailField.getText();
				try{
					

						//new admin must be registered in login_Details
					String q1="select * from Login_Details where User=? and ID=? and EmailID=?";
					PreparedStatement pstmt1=con.prepareStatement(q1);
					pstmt1.setString(1,"Faculty");
					pstmt1.setString(2,ID);
					pstmt1.setString(3,email);
					ResultSet set1=pstmt1.executeQuery();
					System.out.println("HI");
		if(set1.next())
		{
			//new admin details
			String q2="update Login_Details set user=? where ID=? and EmailID=? ";
			PreparedStatement pstmt2=con.prepareStatement(q2);
			pstmt2.setString(1,"Admin");
			pstmt2.setString(2,ID);
			pstmt2.setString(3,email);
			System.out.println(ID+email+"HI");
			pstmt2.executeUpdate();
			System.out.println("HI");
			String q6="update Login_Details set user=? where ID=?";
			PreparedStatement pstmt6=con.prepareStatement(q6);
			pstmt6.setString(1,"Faculty");
			pstmt6.setString(2,AID);
			pstmt6.executeUpdate();
			System.out.println("HI");
			infoBox("Admin Changed Successfully!", null, "Success");
			primaryStage.close();
		}
		else
		showAlert(Alert.AlertType.ERROR, "Form Error!",
                "Error2");
        
		}
		catch(Exception ex1)
		{
			showAlert(Alert.AlertType.ERROR, "Form Error!",
	                "Error");
		}
	            }
	            });
		
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
	

