package ch.get.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.Util;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class ToolsController implements Initializable{
	
	@FXML private ToolBar toolBar;
	@FXML private Button savBtn;
	
	private ListView<String> listView;
	private ObservableList<String> log;
	
	private MainApp mainApp;
	private Util util;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		util = new Util();
		savBtn.setDisable(true);
	}
	
	@FXML
	private void handleSave()
	{
		log = listView.getSelectionModel().getSelectedItems();
		util.saveKPM(log);
	}
	
	@FXML
	private void handleVerInfo()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Version Info");
		alert.setHeaderText("Ver 1.0 Released");
		alert.setContentText("Made by ch.Get");
		alert.showAndWait();
	}

	//setter
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setMainApp_Util()
	{
		util.setMainApp(mainApp);
	}
	
	public void setListView(ListView<String> listView) {
		this.listView = listView;
	}

	//getter
	public ToolBar getToolBar() {
		return toolBar;
	}

	public Button getSavBtn() {
		return savBtn;
	}
}