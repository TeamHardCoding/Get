package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ClientLayoutController implements Initializable {
	
	public static ClientLayoutController cliContInstance = null;
	private MainApp mainApp;
	
	@FXML ListView<String> listView;
	private ObservableList<String> obs = FXCollections.observableArrayList(); //리스트 에 추가할 beans
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cliContInstance = this;
		listView.setItems(obs); //list setting
	}
	
	@FXML
	private void connectToServer() {
		new Client();
	}
	
	public void inputDataListView(String temp) {
		
		if(listView != null) {
			listView.getItems().add(temp);
		}
	}
	
	/*
	 * setter/getter
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
