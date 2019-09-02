package ch.get;

import java.io.IOException;

import ch.get.view.RootController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application {

	Stage primaryStage;
	
	//컨테이너 객체
	BorderPane root_Layout;
	AnchorPane info_Layout;
	
	//컨트롤러 객체
	RootController root_Cont;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		//init
		initRoot();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void init_Layout(){
		FXMLLoader info = new FXMLLoader(Start.class.getResource("view/info.fxml"));
		
		try {
			info_Layout = (AnchorPane)info.load();
			
			if(info_Layout != null)
			{
				Scene scene = new Scene(info_Layout);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Shin");
				stage.initStyle(StageStyle.UTILITY);
				stage.initOwner(primaryStage);
				stage.show();
				
				info_Layout.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						if(event != null) {
							stage.close();
						}
					}
				});
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//root_Cont
	}
	
	public void initRoot() {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Start.class.getResource("view/Root.fxml"));
		
		try {
			root_Layout = (BorderPane)loader.load();
			root_Cont = loader.getController();
			Scene scene = new Scene(root_Layout);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("PROJECT BY Shin");
			primaryStage.setResizable(false);
			
			primaryStage.show();
			
			root_Cont.setMainApp(this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
