package com.ch.get;

import com.ch.get.view.RootLayoutController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ServerStart extends Application {

	private Stage primaryStage;
	
	private BorderPane rootPane;
	private RootLayoutController rCont;
	
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
			primaryStage.show();
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
