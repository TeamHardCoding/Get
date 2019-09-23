package ch.get;

import ch.get.view.ClientInfoSettingController;
import ch.get.view.ClientLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	
	private BorderPane rootLayout;
	private GridPane settingLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		//init
		initRoot();
	}

	public boolean showSettingWindow() {
		
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ClientInfoSetting.fxml"));
			settingLayout = (GridPane) loader.load();
			Scene scene = new Scene(settingLayout);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("클라이언트 설정");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			ClientInfoSettingController.inst.setMainApp(this);
			ClientInfoSettingController.inst.setSettingStage(stage);
			
			stage.showAndWait();
			return ClientInfoSettingController.inst.isOkClicked();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void initRoot() {
		
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ClientLayout.fxml"));
			rootLayout = (BorderPane)loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Client");
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(event -> {
				
				
			});
			primaryStage.show();
			
			ClientLayoutController.cliContInstance.setMainApp(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}
	public GridPane getSettingLayout() {
		return settingLayout;
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}