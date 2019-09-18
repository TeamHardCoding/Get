package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.ServerHandler;
import ch.get.util.ShowAlertWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public class RootLayoutController implements Initializable {

	public static RootLayoutController rcl = null;
	private MainApp mainApp;
	
	@FXML private TextArea textArea;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rcl = this;
	}
	
	public void printText(String msg) {
		textArea.appendText(msg+"\n");
	}
	/*
	 * Handler
	 */
	@FXML
	private void startServer() {
		if(!ServerHandler.getInst().isAlive())
		{
			ServerHandler.getInst().start();
		} else {
			
			new ShowAlertWindow(AlertType.WARNING, "서버 구동 오류", "이미 서버가 실행중 입니다.");
		}
	}
	
	public void stopServer() {
		if(ServerHandler.getInst().isAlive()) {
			ServerHandler.getInst().stopServerSocket();
			printText("사용자 서버 종료");
		} else {
			Platform.exit();
		}
	}
	@FXML
	private void showProgramHandler() {
		new ShowAlertWindow(AlertType.INFORMATION, "1차 프로젝트", "Made By Shin");
	}
	public void exitProgramHandler() {
		Platform.exit();
	}
	/*
	 * setter
	 * getter
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
