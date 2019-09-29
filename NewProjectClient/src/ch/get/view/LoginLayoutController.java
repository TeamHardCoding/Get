package ch.get.view;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.ServerInfo;
import ch.get.model.ServerStat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginLayoutController implements Initializable {

	public static LoginLayoutController loginCont = null;
	private static HashMap<Integer, ServerInfo> serverLists;
	private MainApp mainApp;
	private boolean isOkClicked = false;
	private Stage loginStage;
	private String serverIp;
	private String serverPort;
	private int serverNo;

	@FXML
	private TextField userId;
	@FXML
	private TextField userPass;
//	@FXML private ImageView serverStat;
	@FXML
	private Label serverStat;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		loginCont = this;
		serverLists = new HashMap<Integer, ServerInfo>();
		serverNo = 0;
		Thread thread = new Thread(new ServerStat(serverStat));
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	private void handleOk() { // 로그인 버튼 후 메인화면
//		this.isOkClicked = true;
//		loginStage.close();
	}

	@FXML
	private void handleCancel() {
		this.isOkClicked = false;
		loginStage.close();
	}

	@FXML
	private void showClientSetting() {
		boolean okCliecked = mainApp.showSettingWindow();

		if (okCliecked) {
			serverIp = ClientInfoSettingController.inst.getServerIp();
			serverPort = ClientInfoSettingController.inst.getServerPort();
//			System.out.println(serverIp+" "+serverPort); 넘어옴
			serverLists.put(serverNo++, new ServerInfo("유저서버", serverIp, serverPort));

//			System.out.println(serverLists.size());
//			System.out.println(serverLists.get(0).getServerIp());
//			System.out.println(serverLists.get(1).getServerIp());
		}
	}

	/*
	 * setter/getter
	 */
	public void setLoginStage(Stage loginStage) {
		this.loginStage = loginStage;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**********************************************/
	public static HashMap<Integer, ServerInfo> getServerLists() {
		return serverLists;
	}

	public boolean isOkClicked() {
		return isOkClicked;
	}

	public String getServerIp() {
		return serverIp;
	}

	public int getServerPort() {
		return Integer.parseInt(serverPort);
	}
}
