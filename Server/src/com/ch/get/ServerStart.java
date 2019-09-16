package com.ch.get;

import com.ch.get.view.RootLayoutController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerStart extends Application {

	private Stage primaryStage;
	
	private BorderPane rootPane;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		
		//initMain
		initMain();
	}
	
	public void initMain() {
			
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(ServerStart.class.getResource("view/RootLayout.fxml"));
			rootPane = loader.load();
			
			Scene scene = new Scene(rootPane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Server");
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					// TODO Auto-generated method stub
					if(event.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
						RootLayoutController.rlc.server_Exit();
					}
				}
			});
			
			RootLayoutController.rlc.setMainApp(this);
			primaryStage.show();
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void exit_Now() 
	{
		Platform.exit(); 
	}
	public static void main(String[] args) {
		launch(args);
	}
}
