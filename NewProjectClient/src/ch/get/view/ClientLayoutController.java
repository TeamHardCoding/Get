package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.Client;
import ch.get.util.ShowAlertWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ClientLayoutController implements Initializable {

	public static ClientLayoutController cliContInstance = null;
	private Object lock = new Object();
//	private String serverIp;
//	private String serverPort;
	private MainApp mainApp;
	private Client client = null;

	@FXML
	private TextField textField;
	@FXML
	private Button conServerBtn;

	@FXML
	private ListView<String> listView;
	private ObservableList<String> obs = FXCollections.observableArrayList(); // 리스트 에 추가할 beans

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cliContInstance = this;
		listView.setItems(obs); // list setting
		conServerBtn.setDisable(true);

		textField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				handleSendMsg();
			}
		});
	}

//	@FXML
//	private void showClientSetting() {
//		boolean okCliecked = mainApp.showSettingWindow();
//		
//		if(okCliecked) {
//			serverIp = ClientInfoSettingController.inst.getServerIp();
//			serverPort = ClientInfoSettingController.inst.getServerPort();
//			conServerBtn.setDisable(false);
//		}
//	}

	@FXML
	private void connectToServer() {
		if (client == null) {
			client = new Client(lock);
			client.initClient();
			conServerBtn.setDisable(true);
		}
	}

	@FXML
	private void handleSendMsg() {
		if (client != null) {
			handleSendButton();
		} else {
			new ShowAlertWindow(AlertType.INFORMATION, "클라이언트 시작 오류", "서버가 닫혔거나 접속 하지 않았습니다.");
		}
	}

	private void handleSendButton() {
		int textLength = textField.getText().length();
		if (textLength > 0) {
//			inputDataListView(getMsgFromTextField()); //클라에다가 텍스트 입력
			client.sendMsgToServer(getMsgFromTextField()); // text 가져오기
			textField.setText("");
		}
	}

	public void quitFromServer() {

		if (client != null) {
			client.closeClient();
			client = null;
		}
	}

	public void inputDataListView(String temp) {

		if (client != null) {
			if (listView != null) {
				listView.getItems().add(temp);
				System.out.println(listView.getItems().size() + " Client 재고");
//				client.sendMsgToServer(temp);
			}
		}
	}

	/*
	 * setter/getter
	 */
//	public void setServerIp(String serverIp) {
//		this.serverIp = serverIp;
//	}
//	public void setServerPort(String serverPort) {
//		this.serverPort = serverPort;
//	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/***************************************************/
	public ListView<String> getListView() {
		return listView;
	}

	public String getMsgFromTextField() {
		String msg = this.textField.getText();
		return msg;
	}
//	public String getServerIp() {
//		return serverIp;
//	}
//	public int getServerPort() {
//		return Integer.parseInt(serverPort);
//	}
}
