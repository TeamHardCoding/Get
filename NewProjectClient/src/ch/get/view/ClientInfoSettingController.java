package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.util.CheckVaildAddress;
import ch.get.util.ShowAlertWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientInfoSettingController implements Initializable {

	@FXML
	private TextField serverIp;
	@FXML
	private TextField serverPort;
	private String serverPortString;

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
		boolean isVaild = CheckVaildAddress.getInstance().checkAddress(serverIp.getText(), serverPort.getText());

		if (isVaild) {
			if ((serverPort.getText().length() <= 0) || (serverPort.getText() == null)) {
				serverPortString = "8000";
				new ShowAlertWindow(AlertType.INFORMATION, "기본 포트 설정", "포트 미입력 으로 8000 으로 기본 설정 합니다.");
			} else {
				serverPortString = serverPort.getText();
			}

			isOkClicked = true;
			closeStage();
		} else {
			new ShowAlertWindow(AlertType.ERROR, "IP 입력 확인", CheckVaildAddress.getInstance().getMsg());
		}
	}

	// 셋팅창 OK클릭
	public boolean isOkClicked() {
		return isOkClicked;
	}

	// 셋팅 창 닫기
	public void closeStage() {
		settingStage.close();
	}

	public String getServerIp() {
		return serverIp.getText();
	}

	public String getServerPort() {
		return serverPortString;
	}

	/************************************************/
	public void setOkClicked(boolean isOkClicked) {
		this.isOkClicked = isOkClicked;
	}

	public void setSettingStage(Stage settingStage) {
		this.settingStage = settingStage;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
