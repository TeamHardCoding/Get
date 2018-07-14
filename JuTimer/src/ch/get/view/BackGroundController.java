package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import javafx.fxml.Initializable;

public class BackGroundController implements Initializable{
	
	public static BackGroundController inst;
	private MainApp mainApp;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inst = this;
	}
	
	//getter
	public static BackGroundController getInst() {
		return inst;
	}
	
	//setter
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
