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
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class ClientLayoutController implements Initializable {
	
	public static ClientLayoutController cliContInstance = null;
	private String serverIp;
	private String serverPort;
	private MainApp mainApp;
	private Client client = null;
	
	@FXML ListView<String> listView;
	private ObservableList<String> obs = FXCollections.observableArrayList(); //리스트 에 추가할 beans
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cliContInstance = this;
		listView.setItems(obs); //list setting
	}
	
	@FXML
	private void showClientSetting() {
		boolean okCliecked = mainApp.showSettingWindow();
		
		if(okCliecked) {
			setServerIp(serverIp);
			setServerPort(serverPort);
			
			System.out.println(serverIp);
			System.out.println(serverPort);
		}
	}
	
	@FXML
	private void connectToServer() {
		
		if(client == null) {
			client = new Client();
		}
		
		client.initClient();
	}
	
	public void inputDataListView(String temp) {
		
		if(listView != null) {
			listView.getItems().add(temp);
		}
	}
	
	/*
	 * setter/getter
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public String getServerIp() {
		return serverIp;
	}
	public String getServerPort() {
		return serverPort;
	}
}
