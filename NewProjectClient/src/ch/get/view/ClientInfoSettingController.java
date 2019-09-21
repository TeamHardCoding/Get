package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.util.CheckVaildAddress;
import ch.get.util.ShowAlertWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ClientInfoSettingController implements Initializable{
	
	@FXML private TextField serverIp;
	@FXML private TextField serverPort;
	
	private MainApp mainApp;
	private Stage settingStage;
	private boolean isOkClicked = false;
	public static ClientInfoSettingController inst = null; 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inst = this;
	}
	
	@FXML
	private void handleOk() {
		boolean isVaild = CheckVaildAddress.getInstance().checkAddress(serverIp.getText());
		
		if(isVaild) {
			isOkClicked = true;
			closeStage();
		} else { 
			new ShowAlertWindow(AlertType.ERROR, 
					"IP �Է� Ȯ��", 
					CheckVaildAddress.getInstance().getMsg());
		}
	}
	//����â OKŬ��
	public boolean isOkClicked() {
		return isOkClicked;
	}
	//���� â �ݱ�
	public void closeStage() {
		settingStage.close();
	}
	
	public String getServerIp() {
		return serverIp.getText();
	}
	public String getServerPort() {
		return serverPort.getText();
	}
	public void setSettingStage(Stage settingStage) {
		this.settingStage = settingStage;
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
