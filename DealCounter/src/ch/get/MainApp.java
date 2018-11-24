package ch.get;

import ch.get.model.StageDragEvent;
import ch.get.view.RootLController;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	private Stage mainPrimary;
	
	//View Inst
	private AnchorPane rootLayout;
	
	//controller
	//public static RootLController rootCont;
	
	@Override
	public void start(Stage primaryStage) {
		mainPrimary = primaryStage;
		
		initMain();
		
		//컨트롤러 인스턴스
		//rootCont = RootLController.inst.getInst();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initMain()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootL.fxml"));
			rootLayout = loader.load();
			
			Scene rootScene = new Scene(rootLayout);
			
			mainPrimary.addEventHandler(MouseEvent.ANY, new StageDragEvent());
			
			mainPrimary.setScene(rootScene);
			mainPrimary.setTitle("딜타임 카운터");
			mainPrimary.setResizable(false);
			mainPrimary.initStyle(StageStyle.UNDECORATED);
			mainPrimary.show();
			
			
			RootLController.inst.setMainApp(this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
