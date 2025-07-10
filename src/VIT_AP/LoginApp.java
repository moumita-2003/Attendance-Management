package VIT_AP;
import java.util.Random;
import database.DBConnect;
import javafx.stage.Stage;

import java.sql.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;
public class LoginApp extends Application {
	private Canvas captchaCanvas;
	  private TextField inputField;
	  private String generatedCaptcha;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        captchaCanvas = new Canvas(150, 50);
        
        // Create the login scene
        GridPane loginPane = createLoginPane();
        Scene loginScene = new Scene(loginPane, 500, 700);
        
        // Set the login scene on the stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("VIT-AP University");
        primaryStage.show();
        
    }
   
    private GridPane createLoginPane() {
        
        
//SECTION-1: TOP Display BAR
        
        //1.1: get the image
        ImageView imageView = new ImageView();
        String imageUrl ="C:\\Users\\moumita\\Desktop\\ecp\\VTOP\\src\\images\\vtop_top_2.jpg";
        Image image = new Image(imageUrl);
        imageView.setImage(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(60);
        StackPane topBarImage = new StackPane();
        topBarImage.getChildren().add(imageView);
        
        //1.2:set the top bar
        GridPane topLayout = new GridPane();
        topLayout.add(topBarImage, 0, 0);
        
//SECTION-2: Middle top display bar
        Label Label = new Label("VTOP Login");
        Label.setFont(new Font("Arial", 25));
        Label.setPadding(new Insets(10));
        GridPane topLayout2 = new GridPane();
        topLayout2.setStyle("-fx-background-color: #F4F3FC;");
        topLayout2.setPrefSize(400, 50);
        topLayout2.add(Label, 1, 0);
        
//SECTION 3: User Details: 
        Label UserLabel = new Label("User");
        UserLabel.setFont(new Font("Arial", 20));
        
        //3.11 : Type of user
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setPrefWidth(350);
        choiceBox.setItems(FXCollections.observableArrayList(
                "Admin",
        		"Faculty",
                "Student",
                "Staff"
                
        ));
        VBox userType = new VBox(choiceBox);
        //3.12 load user type image
        ImageView imageView1 = new ImageView();
        String imageUrl1 = "C:\\Users\\moumita\\Desktop\\ecp\\VTOP\\src\\images\\user_type_logo.png";
        Image image1 = new Image(imageUrl1);
        imageView1.setImage(image1);
        imageView1.setFitWidth(40);
        imageView1.setFitHeight(25);
        StackPane userTypeStack = new StackPane();
        userTypeStack.getChildren().add(imageView1);
        
        //3.21: userid
        TextField idField = new TextField();
        idField.setPromptText("User ID");
        idField.setPrefWidth(350);
        //3.22 load user id image
        ImageView imageView2 = new ImageView();
        String imageUrl2 = "C:\\Users\\moumita\\Desktop\\ecp\\VTOP\\src\\images\\userId_logo.png";
        Image image2 = new Image(imageUrl2);
        imageView2.setImage(image2);
        imageView2.setFitWidth(20);
        imageView2.setFitHeight(20);
        StackPane idStack = new StackPane();
        idStack.getChildren().add(imageView2);
        
        //3.31: password
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(350);
        passwordField.setPromptText("Password");
        //3.32 load password image
        ImageView imageView3 = new ImageView();
        String imageUrl3 = "C:\\Users\\moumita\\Desktop\\ecp\\VTOP\\src\\images\\password_logo_2.jpg";
        Image image3 = new Image(imageUrl3);
        imageView3.setImage(image3);
        imageView3.setFitWidth(20);
        imageView3.setFitHeight(20);
        StackPane pwdStack = new StackPane();
        pwdStack.getChildren().add(imageView3);

//SECTION 4: Captcha 
        generateCaptcha();
        
        //4.11 load refresh image
        Image refreshImage = new Image("C:\\Users\\moumita\\Desktop\\ecp\\VTOP\\src\\images\\refresh_logo_3.jpg"); // Use "file:" for local images
        ImageView refreshImageView = new ImageView(refreshImage);
        refreshImageView.setFitWidth(40);
        refreshImageView.setFitHeight(40);

        //4.12 click on refresh image to refresh
        refreshImageView.setOnMouseClicked(event -> {
        	try {
        		generateCaptcha();
            	}
				catch(Exception ex)
				{
					showAlert(Alert.AlertType.ERROR, "Form Error!",
		                    "Invalid Input");
				}
        });
        
        //4.2 to merge captcha image and refresh image
        GridPane captcha = new GridPane();
        captcha.add(captchaCanvas, 0, 0);
        captcha.add(refreshImageView, 1, 0);
        
        //4.3 enter captcha
        inputField = new TextField();
        
        //4.4 join all captcha element in vbox
        VBox captchaBox = new VBox(10,captcha);
        captchaBox.setPadding(new Insets(20, 0, 0, 80));
        
//SECTION 5: Forget Password
        Label textLabel = new Label("Forgot Password");
        textLabel.setTextFill(Color.DODGERBLUE);
        textLabel.setStyle("-fx-font-size: 13; ");
        textLabel.setOnMouseClicked(event -> {
        	try {
            	Login_Details CP= new Login_Details(choiceBox.getValue(),primaryStage,idField.getText());
				CP.Change_Password();
            	}
				catch(Exception ex)
				{
					showAlert(Alert.AlertType.ERROR, "Form Error!",
		                    "Invalid Input");
				}
        });
        
//SECTION 6:Click the Submit button to authenticate    
        Button loginButton = new Button("Submit");
        loginButton.setStyle("-fx-background-color: #1B70BA;");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setPrefSize(120, 40);
        loginButton.setOnAction(event -> {
            String selectedOption = choiceBox.getValue();
            performAction(selectedOption,idField.getText(), passwordField.getText(),verifyCaptcha());
        });
        
        
        
//SECTION 7: create middle layout  
        GridPane midLayout = new GridPane();
        midLayout.setPadding(new Insets(20));
        midLayout.setVgap(10);
        midLayout.setStyle("-fx-background-color: white;");
        midLayout.setPrefSize(400, 50);
        
        //midLayout.add(UserLabel, 0, 1);
        midLayout.add(userType, 0, 2);
        midLayout.add(userTypeStack, 1, 2);
        midLayout.add(idField, 0, 3);
        midLayout.add(idStack, 1, 3);
        midLayout.add(passwordField, 0, 4);
        midLayout.add(pwdStack, 1, 4);
        midLayout.add(captchaBox, 0, 5);
        midLayout.add(inputField, 0, 6);
        midLayout.add(textLabel, 0, 8);
        midLayout.add(loginButton, 1, 7);
        
        Separator topLine = new Separator();
        topLine.setStyle("-fx-pref-width: 400px; -fx-background-color: #1B70BA;");
        
      //create middle box layout
        VBox midBox=new VBox();
        midBox.getChildren().addAll(topLine,topLayout2,midLayout);
        
//SECTION 8: Create the login pane        
        
        //for space(empty layout) between top bar and middle box layout
        Pane Layout2=new Pane();
        Layout2.setPrefSize(200, 50);
        
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.getChildren().add(midBox);
        
        GridPane MainLayout = new GridPane();
        MainLayout.add(topLayout, 0, 0);
        MainLayout.add(Layout2, 0, 1);
        MainLayout.add(flowPane,0,3);
        return MainLayout;
    }

//METHOD 1: Authentication process(Method) using user type, id, password and captcha
    private void performAction(String user,String id, String password,boolean captcha) {
        // Simulate login authentication
    	String q1="select * from Login_Details where ID=? and Password=? and User=?";
    	try (Connection conn = DBConnect.getConnection();
    	         PreparedStatement stmt = conn.prepareStatement(q1)){
    	
		PreparedStatement stmt1=conn.prepareStatement(q1);
		stmt1.setString(1,id);
		stmt1.setString(2,password);
		stmt1.setString(3,user);
		ResultSet set1=stmt1.executeQuery();
		
		//check if the provided details is correct or wrong
		if(!captcha) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Invalid Captcha");
        }
		else if(set1.next())
		{
			
			
            switch(user)
			{
				case "Admin":
					AdminApp adminApp = new AdminApp();
		            adminApp.Admin(user,id,primaryStage);
					break;
				case "Faculty":
					FacultyApp facultyApp = new FacultyApp();
		            facultyApp.Faculty(user,id,primaryStage);
					break;
				case "Staff":
					//StaffApp stafApp = new StaffApp();
		            //staffApp.Staff(user,id,primaryStage);
					break;
				case "Student":
					StudentApp studentApp = new StudentApp();
		            studentApp.Student(user,id,primaryStage);
					break;
				default:
				{
					showAlert(Alert.AlertType.ERROR, "Form Error!",
	                        "Enter valid user type");
	                break;
				}
			}
		}
		else if(id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "UserID cannot be empty");
        }
    	else if(password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Password cannot be empty");
        }
    	
        else {
        	showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter correct ID and Password");
        	
        }
    	}
    	catch (Exception e1)
    	{
    		showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "User not present!!");
    		
    	}
    }
    
//METHOD 1.1: Display Error Box for wrong details
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    
//METHOD 2: Generate captcha String
    private void generateCaptcha() {
    	int length=6;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        
        //generate random strings of alphabets and digits
        for (int i = 0; i < length; i++) {
          captcha.append(chars.charAt(random.nextInt(chars.length())));
        }
        generatedCaptcha = captcha.toString();
        
        drawCaptcha();
      }

////METHOD 3: Generate captcha Image 
    private void drawCaptcha() {
        GraphicsContext gc = captchaCanvas.getGraphicsContext2D();
        Random random = new Random();

        // Clear canvas
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, captchaCanvas.getWidth(), captchaCanvas.getHeight());

        // Set random font color
        gc.setFill(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
        gc.setFont(new Font("Arial", 24));

        // Draw captcha text with slight randomness
        for (int i = 0; i < generatedCaptcha.length(); i++) {
          double x = 20 + (i * 20) + random.nextInt(5);
          double y = 30 + random.nextInt(10);
          gc.fillText(String.valueOf(generatedCaptcha.charAt(i)), x, y);
        }

        // Add random noise lines
        gc.setStroke(Color.BLACK);
        for (int i = 0; i < 5; i++) {
          gc.strokeLine(random.nextInt(150), random.nextInt(50), random.nextInt(150), random.nextInt(50));
        }
      }

//METHOD 4: Validate captcha entered by user
      private boolean verifyCaptcha() {
        String userInput = inputField.getText().trim();
        if (userInput.equalsIgnoreCase(generatedCaptcha)) {
          return true;
        } else {
          return false;
        }
      }
}
