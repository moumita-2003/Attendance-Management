package database;
import javafx.application.*;
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.*;
import javafx.stage.Stage;
public class Tick_Tac_Toe extends Application{
	Button arr1[][]=new Button[3][3];
	int arr2[][]=new int[3][3];
	int arr[]=new int[8];
	int d=0,x=0,a=0,b=0,turn=0;
	Stage stage;
	public static void main(String args[])
	{
		launch(args);
	}
	@Override  
    public void start(Stage primaryStage) { 
		this.stage=primaryStage;
		FlowPane pane=new FlowPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20));
		pane.setHgap(10);
		GridPane root= new GridPane();
		Scene scene=new Scene(pane,500,500);  
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tick_Tac_Toe");
		primaryStage.show();
		
		
		Font font = new Font("Arial",50);
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				Button btn=new Button("");
				btn.setPrefWidth(100);
				btn.setPrefHeight(100);
				root.add(btn, i, j);
				arr1[i][j]=btn;
				btn.setOnMouseClicked(event -> {
					if(event.getButton()== MouseButton.PRIMARY && turn==0)
					{
						btn.setText("X");
						btn.setFont(font);
						btn.setTextFill(Color.WHITE);
						btn.setStyle("-fx-background-color: red;");
						d++;
						turn=1;
						value(btn);
					}
					else if(event.getButton()==MouseButton.PRIMARY && turn==1)
					{
						btn.setText("O");
						btn.setFont(font);
						btn.setTextFill(Color.WHITE);
						btn.setText("O");
						btn.setStyle("-fx-background-color: blue;");
						d++;
						turn=0;
						value(btn);
					}
					else if(event.getClickCount()==3)
					{
						btn.setText("");
						btn.setStyle("");
					}
					checkwinner();
				});
				
				
			}
		}
		
		Button btn3=new Button("Restart");
		btn3.setStyle("-fx-background-color: Green;");
		btn3.setTextFill(Color.WHITE);
		btn3.setOnAction(event ->{
			Tick_Tac_Toe t=new Tick_Tac_Toe();
			t.start(stage);
		});
		
		pane.getChildren().addAll(root,btn3);
		
	}
	public void value(Button btn)
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(arr1[i][j].equals(btn))
				{ 
					a=i;
					b=j;
				}
			}
		}
		String st=arr1[a][b].getText();
		System.out.println(st+"L");
		if(st=="X")
			arr2[a][b]=1;
		else if(st=="O")
			arr2[a][b]=-1;
		else
			arr2[a][b]=0;
	}
	public void checkwinner()
	{
		
		int r1=arr2[0][0]+arr2[0][1]+arr2[0][2];
		int r2=arr2[1][0]+arr2[1][1]+arr2[1][2];
		int r3=arr2[2][0]+arr2[2][1]+arr2[2][2];
		int c1=arr2[0][0]+arr2[1][0]+arr2[2][0];
		int c2=arr2[0][1]+arr2[1][1]+arr2[2][1];
		int c3=arr2[0][2]+arr2[1][2]+arr2[2][2];
		int d1=arr2[0][0]+arr2[1][1]+arr2[2][2];
		int d2=arr2[0][2]+arr2[1][1]+arr2[2][0];
		//System.out.println(r1);
		if(r1==3||r2==3||r3==3||c1==3||c2==3||c3==3||d1==3||d2==3)
		{
			x++;
			showAlert(Alert.AlertType.INFORMATION,"Congratulation !!!!","X is the Winner!!");
			return ;
		}
		else if(r1==-3||r2==-3||r3==-3||c1==-3||c2==-3||c3==-3||d1==-3||d2==-3)
		{
			x++;
			showAlert(Alert.AlertType.INFORMATION,"Congratulation !!!!","O is the Winner!!");
			return ;
		}
		else if(d==9)
			showAlert(Alert.AlertType.INFORMATION,"Sorry !!!!","It's a Draw Match!!");
			
	}
    private static void showAlert(Alert.AlertType infoType, String title,String message) {
        Alert alert = new Alert(infoType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
	
}
