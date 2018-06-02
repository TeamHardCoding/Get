package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RootLayoutController implements Initializable{
	
	private MainApp mainApp;
	private Stage primageStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	//setOnHandler
	@FXML
	private void addInstance() //add Instance
	{
		mainApp.addResultValue();
	}
	
	@FXML
	private void closeApp() //Exit Program
	{
		Platform.exit();
	}
	
	@FXML
	private void infoCalAlba() //Info My Program
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ch.Get");
		alert.setHeaderText("�˹ٺ� ����");
		alert.setContentText("Feedback : dkdlwmzhs@naver.com\n����� �ּż� �����մϴ�.");
		alert.showAndWait();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setPrimageStage(Stage primageStage) {
		this.primageStage = primageStage;
	}
}
