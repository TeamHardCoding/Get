package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ViewListController implements Initializable{
	
	@FXML ListView<String> listView;
	private MainApp mainApp;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//listView.getSelectionModel().selectedItemProperty().addListener(
				//	(observable, oldValue, newValue) -> handleHideToolbar());
	}
	
	//setter
	public void setMainApp(MainApp temp)
	{
		mainApp = temp;
	}
	
	//getter
	public ListView<String> getListView() {
		return listView;
	}
	
}
