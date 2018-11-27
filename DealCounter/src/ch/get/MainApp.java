package ch.get;

import ch.get.model.Preset;
import ch.get.model.StageMouseEvent;
import ch.get.view.RootLController;
import ch.get.view.SettingLController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	private Stage mainPrimary;
	
	//View Inst
	private AnchorPane rootLayout;
	private AnchorPane setLayout;
	
	//prosetInst
	private ObservableList<Preset> presetData = FXCollections.observableArrayList();
	
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
			rootLayout = (AnchorPane)loader.load();
			Scene rootScene = new Scene(rootLayout);
			
			mainPrimary.addEventHandler(MouseEvent.ANY, new StageMouseEvent());
			
			mainPrimary.setScene(rootScene);
			mainPrimary.setTitle("딜타임 카운터");
			mainPrimary.setAlwaysOnTop(true);
			mainPrimary.setResizable(false);
			mainPrimary.initStyle(StageStyle.UTILITY);
			mainPrimary.show();

			RootLController.getInst().setMainApp(this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean initSetting()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/SettingL.fxml"));
			setLayout = (AnchorPane)loader.load();
			
			Scene scene = new Scene(setLayout);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle("설정");
			stage.centerOnScreen();
			stage.initStyle(StageStyle.UTILITY);
			stage.initOwner(mainPrimary);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setScene(scene);
			stage.show();
			
			SettingLController.getInst().setMainApp(this);
			SettingLController.getInst().setThisStage(stage);
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}
	
	//getter
	public ObservableList<Preset> getPresetData() {
		return presetData;
	}
}