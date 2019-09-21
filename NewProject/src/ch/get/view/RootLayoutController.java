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
			new ShowAlertWindow(AlertType.WARNING, "���� ���� ����", "�̹� ������ ������ �Դϴ�.");
		}
	}
	
	public void stopServer() {
		if(sh.getUserThreadLists().size() >= 1) {
			ButtonType btType = 
					new ShowAlertWindow(AlertType.WARNING, "���� ����", "�̹� ������ ������ �Դϴ� �׷��� ���� �Ͻðڽ��ϱ�?")
					.getButtonResult();
			
			if(btType == ButtonType.OK) {
				sh.stopServerSocket();
				sh = null;
			}
		}
	}
	
	@FXML
	private void showClientProgramHandler() {
		mainApp.initClient();
	}
	@FXML
	private void showProgramHandler() {
		new ShowAlertWindow(AlertType.INFORMATION, "1�� ������Ʈ", "Made By Shin");
	}
	public void exitProgramHandler() {
		stopServer();
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
