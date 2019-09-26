package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.ServerHandler;
import ch.get.util.ShowAlertWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

public class RootLayoutController implements Initializable {

	public static RootLayoutController rcl = null;
	private MainApp mainApp;
	private ServerHandler sh = null;
	
	@FXML private TextArea textArea;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rcl = this;
	}
	
	public void printText(String msg) {
		textArea.appendText(msg+"\n\n");
	}
	
	/*
	 * Handler
	 */
	@FXML
	private void startServer() {
		if(sh == null) {
			sh = new ServerHandler();
			sh.start(); 
		} else {
			new ShowAlertWindow(AlertType.WARNING, "서버 구동 오류", "이미 서버가 실행중 입니다.");
		}
	}
	
	@FXML
	private void stopServer() {
		if(sh != null) {
			ButtonType btType = 
					new ShowAlertWindow(AlertType.WARNING, "서버 종료", "이미 서버가 실행중 입니다 그래도 종료 하시겠습니까?")
					.getButtonResult();
			
			if(btType == ButtonType.OK) {
				sh.stopServer();
				printText("서버 종료");
				sh = null;
			}
		}
	}
	
	public void exitProgramHandler() {
		stopServer();
	}
	
	@FXML
	private void showProgramHandler() {
		new ShowAlertWindow(AlertType.INFORMATION, "Server", "Made By Shin");
	}
	/*
	 * setter
	 * getter
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
