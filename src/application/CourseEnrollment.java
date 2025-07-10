package application;
import java.sql.*;
import java.util.*;

import VIT_AP.AdminApp;
import VIT_AP.FacultyApp;
import VIT_AP.StudentApp;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class CourseEnrollment extends Application
{
	
	private Stage primaryStage;
	GridPane gridPane = new GridPane();
	static Scanner sc;
	static Connection con;
	static String Sname;
	static String SID;
	TextField yearField;
	String ID;
	String user;
	String sem="",Ccode="",Cname="",Fname="",slot="";
	int d=3;
	/*CourseEnrollment(String user,String ID)
	{
		this.ID=ID;
		this.user=user;
	}*/
	public CourseEnrollment(String user,String SID) throws Exception
	{
		/*Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
		sc=new Scanner(System.in);*/
		this.SID=SID;
		this.user=user;
		
	}
	/*public static void Create_Table() throws Exception
	{
		String q="create table Stud_Enrollment_"+year+"( ID char(20),CName varchar(20), Semester varchar(15), ClassID char(20) unique key,Course_Code char(10),Course_Name varchar(20), Faculty_Name varchar(20), Slot char(5))";
		Statement stmt=con.createStatement();
		stmt.executeUpdate(q);
		con.close();
		//Enrollment_Page EP=new Enrollment_Page();
		//EP.main_Enrollment(AID); 
	}
	*/
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/VIT_AP","root","root123");
		}
		catch(Exception e)
		{
		System.out.println("Error10");
		}
        // Create the login scene
        GridPane loginPane = createLoginPane();
        Scene loginScene = new Scene(loginPane, 600, 600);

        // Set the login scene on the stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Course Enrollment");
        primaryStage.show();
        
    }
    private GridPane createLoginPane() {
        // Create the login pane
    	
    	
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        Label label = new Label("Registration Details");
       
        Label yearlabel = new Label("Year");
        yearField = new TextField();
        yearField.setPromptText("Year");
        Label degreelabel = new Label("Degree");
ChoiceBox<String> choiceBox0 = new ChoiceBox<>();
        
        choiceBox0.setItems(FXCollections.observableArrayList(
                "Btech",
                "Integrated Mtech",
                "Commerce",
                "MSc",
                "Bsc"
                
                
        ));
        VBox degreeField = new VBox(choiceBox0);
        final Button viewButton = new Button("Enter");
        viewButton.setOnAction(e -> CourseEnroll(yearField.getText(),choiceBox0.getValue()));
        final Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
    					StudentApp studentApp = new StudentApp();
    					studentApp.Student(user,ID,primaryStage);
            }
        });
        
        
        gridPane.add(label, 0, 0);
        gridPane.add(yearlabel, 0, 1);
        gridPane.add(yearField,1, 1);
        gridPane.add(degreelabel, 0, 2);
        gridPane.add(degreeField,1, 2);
        
        
        
        
        gridPane.add(viewButton, 2, 2);
        gridPane.add(gobackButton, 1, 30);


            return gridPane;
        }
    public void CourseEnroll(String year,String degree)
	{

    	final Button enter1 = new Button("Enter");
    	final Button enter2 = new Button("Enter");
    	final Button enter3 = new Button("Enter");
    	final Button enter4 = new Button("Enter");
    	final Button enter5 = new Button("Add");
    	
    	
    	ChoiceBox<String> choiceBox1 = new ChoiceBox<>();
    	ChoiceBox<String> choiceBox2 = new ChoiceBox<>();
    	ChoiceBox<String> choiceBox3 = new ChoiceBox<>();
    	ChoiceBox<String> choiceBox4 = new ChoiceBox<>();
    	
    	 choiceBox1.setItems(FXCollections.observableArrayList(
                 "Semester 1",
                 "Semester 2",
                 "Semester 3",
                 "Semester 4",
                 "Semester 5",
                 "Semester 6",
                 "Semester 7",
                 "Semester 8",
                 "Semester 9",
                 "Semester 10"
                 
         ));
    	VBox root1 = new VBox(choiceBox1);
    	 Label label2 = new Label("Semester");
    	
        VBox root2 = new VBox(choiceBox2);
        Label label3 = new Label("Courses");
        
        
    	 VBox root3 = new VBox(choiceBox3);
    	 Label label4 = new Label("Faculty");
    	
    	 
    		VBox root4 = new VBox(choiceBox4);
    		Label label5 = new Label("Slot");
    		gridPane.add(label2, 0, d);
            gridPane.add(root1, 1, d);
            gridPane.add(enter1, 2, d++);
            gridPane.add(label3, 0, d);
            gridPane.add(root2, 1, d);
            gridPane.add(enter2, 2, d++);
            gridPane.add(label4, 0, d);
            gridPane.add(root3, 1, d);
            gridPane.add(enter3, 2, d++);
            gridPane.add(label5, 0, d);
            gridPane.add(root4, 1, d);
            gridPane.add(enter4, 2, d++);
            gridPane.add(enter5, 0, d++);
            
            sem="";
            Ccode="";
            Cname="";
            Fname="";
            slot="";
    	try
		{
    		ArrayList<String> course=new ArrayList<>();
    	    ArrayList<String> coursecode=new ArrayList<>();
    	    ArrayList<String> coursename=new ArrayList<>();
    	    ArrayList<String> faculty=new ArrayList<>();
    		ArrayList<String> slots=new ArrayList<>();
			
				
					String q="select * from Login_details where ID=? ";
					PreparedStatement stmt=con.prepareStatement(q);
					stmt.setString(1,SID);
					ResultSet set=stmt.executeQuery();
					if(set.next())
					{
						Sname=set.getString("Cname");
						System.out.println("Student name: "+Sname);
						System.out.println();
						
					}
					
					
					enter1.setOnAction(new EventHandler<ActionEvent>() {
			            @Override
			            public void handle(ActionEvent e) {
			            	String semester=choiceBox1.getValue();
			            	sem=""+semester.charAt(semester.length()-1);
					        try
							{
					        String q1="select * from courses where Degree=?";
							PreparedStatement stmt1=con.prepareStatement(q1);
							stmt1.setString(1,degree);
							ResultSet set1=stmt1.executeQuery();
							while(set1.next())
							{
								String Ccode1=set1.getString("Course_code");
								String Cname1=set1.getString("Course_Name");
								String s=Ccode1+" : "+Cname1;
								course.add(s);
								coursecode.add(Ccode1);
								coursename.add(Cname1);
							}
							choiceBox2.setItems(FXCollections.observableArrayList(course));
							}
							catch(Exception e1)
							{
								showAlert(Alert.AlertType.ERROR, "Form Error!",
						                "Enter valid year/degree");
							}
					        
			            }
			        });
					
					
					 enter2.setOnAction(new EventHandler<ActionEvent>() {
				            @Override
				            public void handle(ActionEvent e) {
				            	String st=choiceBox2.getValue();
								for(int i=0;i<st.length();i++)
								{
									char ch=st.charAt(i);
									if(ch==' ')
										break;
									else
										Ccode=Ccode+ch;
								}
								System.out.println(Ccode);
				            	Cname=coursename.get(coursecode.indexOf(Ccode));
				            	try
								{
				            		String q2="select * from classes_"+year+" where Course_code=? and semester=?";
									PreparedStatement stmt2=con.prepareStatement(q2);
									stmt2.setString(1,Ccode);
									stmt2.setString(2,sem);
									ResultSet set2=stmt2.executeQuery();
									System.out.println();
									
									
									//
									String Cname;
									if (set2.next())
									{
										System.out.println("Faculties Enrolled in Course:"+Ccode+" are given below....");
										Cname=set2.getString("Course_name");
										do
										{
										String Fname1=set2.getString("Faculty_Name");
										System.out.println(Fname1);
										faculty.add(Fname1);
										}
										while (set2.next());
										
									}
									
									choiceBox3.setItems(FXCollections.observableArrayList(faculty));
								}
								catch(Exception e1)
								{
									showAlert(Alert.AlertType.ERROR, "Form Error!",
							                "Error3!! Course Already Registered/Invalid Class Details");
								}
				            	
				            }
					 });
					enter3.setOnAction(new EventHandler<ActionEvent>() {
			            @Override
			            public void handle(ActionEvent e) {
			            	
			            	Fname=choiceBox3.getValue();
			            	try
							{
			            		String q3="select * from classes_"+year+" where Faculty_Name=?";
								PreparedStatement stmt3=con.prepareStatement(q3);
								stmt3.setString(1,Fname);
								ResultSet set3=stmt3.executeQuery();
								System.out.println();
								System.out.println("Slots available for Faculty:"+Fname+" are given below....");
								while (set3.next())
								{
									String slot1=set3.getString("Slot");
									System.out.println(slot1);
									slots.add(slot1);
								}
								choiceBox4.setItems(FXCollections.observableArrayList(slots));
					            
							}
							catch(Exception e1)
							{
								showAlert(Alert.AlertType.ERROR, "Form Error!",
						                "Error5!! Course Already Registered/Invalid Class Details");
							}
			            	
			            }
					});
					
					enter4.setOnAction(new EventHandler<ActionEvent>() {
			            @Override
			            public void handle(ActionEvent e) {
			            	slot=choiceBox4.getValue();
			            	try
							{
			            		String q4="select classID from classes_"+year+" where Course_code=? and Faculty_Name=? and slot=?";
								PreparedStatement stmt4=con.prepareStatement(q4);
								stmt4.setString(1,Ccode);
								stmt4.setString(2,Fname);
								stmt4.setString(3,slot);
								System.out.println("Clash1");
								ResultSet set4=stmt4.executeQuery();
								System.out.println("Clash2");
								//check whether there is same slot clash
								String q0="select * from Stud_Enrollment_"+year+" where ID=? and Course_Code=? and Faculty_Name=? and Slot=? and Semester=?";
								PreparedStatement stmt0=con.prepareStatement(q0);
								stmt0.setString(1,SID);
								stmt0.setString(2,Ccode);
								stmt0.setString(3,Fname);
								stmt0.setString(4,slot);
								stmt0.setString(5,sem);
								System.out.println("Clash3");
								ResultSet set0=stmt0.executeQuery();
								System.out.println("Clash4");
								if (set0.next())
								{
									String cc=set0.getString("Course_code");
									String ff=set0.getString("Faculty_Name");
									String ss=set0.getString("slot");
									showAlert(Alert.AlertType.ERROR, "Form Error!",
							                "Error5!! Course Already Registered/Invalid Class Details");
									System.out.println("Clash!!! with "+cc+" : "+ff+" : "+ss);
								}
								
								else
								{
								//set4 is null if the data provided is wrong ,so it doesn't enter the while loop and b=0
								//a=0:Exist,a=1:continue,b=0:Not registered,b=1:Registered			
									
									while(set4.next())
									{
										String ClassID2=set4.getString("ClassID");	
										System.out.println("ID"+ClassID2);
										String q5="alter table class_"+ClassID2+" add _"+SID+" char(1)";		
										Statement stmt5=con.createStatement();
										stmt5.executeUpdate(q5);
										String q6="insert into Stud_Enrollment_"+year+"(ID,CName,Semester,ClassID,Course_Code,Course_Name,Faculty_Name,Slot) values(?,?,?,?,?,?,?,?)";		
										PreparedStatement stmt6=con.prepareStatement(q6);
										stmt6.setString(1,SID);
										stmt6.setString(2,Sname);
										stmt6.setString(3,sem);
										stmt6.setString(4,ClassID2);
										stmt6.setString(5,Ccode); 
										stmt6.setString(6,Cname); 
										stmt6.setString(7,Fname);
										stmt6.setString(8,slot);
										stmt6.executeUpdate();
										//***
										System.out.println("Course_"+Ccode+" Registered Succesfully!!");
										infoBox(("Course_"+Ccode+" Registered Succesfully!!"), null, "Success");
										enter5.setOnAction(new EventHandler<ActionEvent>() {
								            @Override
								            public void handle(ActionEvent e2) {
								            CourseEnroll(year,degree);
								            }
										});
										//b=1;
									}
									System.out.println(Cname);
									
							            }
								}
							
							catch(Exception e1)
							{
								System.out.println("Error8!! Course Already Registered/Invalid Class Details");
							}
			            }
			        });
					
				
				System.out.println("Want to:\n"+"0.Exist\n"+"1.Continue for another course");
				//a=sc.nextInt();
				sc.nextLine();
				
			//}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("Error!! Course Already Registered/Invalid Class Details");
		}
		//as student also enroll the course by themselves
		//Enrollment_Page EP=new Enrollment_Page();
		//EP.main_Enrollment(AID); 
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